package com.semearApp.semearApp.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semearApp.semearApp.models.Aviso;
import com.semearApp.semearApp.models.Membro;
import com.semearApp.semearApp.models.TransacaoEntrada;
import com.semearApp.semearApp.models.TransacaoSaida;
import com.semearApp.semearApp.repository.AvisoRepository;
import com.semearApp.semearApp.repository.MembroRepository;
import com.semearApp.semearApp.repository.TransacaoEntradaRepository;
import com.semearApp.semearApp.repository.TransacaoSaidaRepository;

@Component
@Scope("session")
public class DashboardBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private TransacaoEntradaRepository transacaoEntradaRepository;
    @Autowired
    private TransacaoSaidaRepository transacaoSaidaRepository;
    @Autowired
    private MembroRepository membroRepository;
    @Autowired
    private AvisoRepository avisoRepository;

    private Map<String, Double> consolidadoEntrada;
    private Map<String, Double> consolidadoSaida;
    private Map<String, Double> saldoAcumulado;

    private List<Membro> aniversariantes;
    private List<Aviso> avisosDoMes;

    @PostConstruct
    public void init() {
        // Carregar dados iniciais
        consolidadoEntrada = new HashMap<>();
        consolidadoSaida = new HashMap<>();
        saldoAcumulado = new HashMap<>();
        aniversariantes = new ArrayList<>();
        avisosDoMes = new ArrayList<>();

        carregarDadosFinanceiros();
        carregarAniversariantes();
        carregarAvisos();
    }

    public void carregarDadosFinanceiros() {
        Iterable<TransacaoEntrada> iterableEntrada = transacaoEntradaRepository.findAll();
        List<TransacaoEntrada> entradas = new ArrayList<>();
        iterableEntrada.forEach(entradas::add);

        Iterable<TransacaoSaida> iterableSaida = transacaoSaidaRepository.findAll();
        List<TransacaoSaida> saidas = new ArrayList<>();
        iterableSaida.forEach(saidas::add);

        consolidadoEntrada = consolidarPorMesEntrada(entradas);
        consolidadoSaida = consolidarPorMesSaida(saidas);
        saldoAcumulado = calcularSaldoAcumulado(consolidadoEntrada, consolidadoSaida);
    }

    public void carregarAniversariantes() {
        aniversariantes = getAniversariantesDoMes();
    }

    public void carregarAvisos() {
        avisosDoMes = getAvisos();
    }

    // Lógica portada do IndexController
    private List<Membro> getAniversariantesDoMes() {
        Iterable<Membro> membrosIterable = membroRepository.findAll();
        List<Membro> membros = new ArrayList<>();
        membrosIterable.forEach(membros::add);

        List<Membro> aniversariantesDoMes = new ArrayList<>();
        LocalDate hoje = LocalDate.now();
        Month mesAtual = hoje.getMonth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Membro membro : membros) {
            try {
                LocalDate dataNascimento = LocalDate.parse(membro.getDataNascimento(), formatter);
                if (dataNascimento.getMonth() == mesAtual) {
                    aniversariantesDoMes.add(membro);
                }
            } catch (Exception e) {
                // Ignorar datas inválidas
            }
        }
        return aniversariantesDoMes;
    }

    private List<Aviso> getAvisos() {
        LocalDate hoje = LocalDate.now();
        Iterable<Aviso> avisoIterable = avisoRepository.findAll();
        List<Aviso> avisos = new ArrayList<>();
        avisoIterable.forEach(avisos::add);

        List<Aviso> avisosDoMes = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Aviso aviso : avisos) {
            try {
                LocalDate data = LocalDate.parse(aviso.getData(), formatter);
                if (data.isAfter(hoje) || aviso.isAvisoPadrao()) {
                    avisosDoMes.add(aviso);
                }
            } catch (Exception e) {
                // Ignorar datas inválidas
            }
        }
        return avisosDoMes;
    }

    private Map<String, Double> consolidarPorMesEntrada(List<TransacaoEntrada> transacoes) {
        Map<String, Double> consolidado = new HashMap<>();
        for (TransacaoEntrada transacao : transacoes) {
            try {
                LocalDate data = LocalDate.parse(transacao.getData()); // Assumindo formato ISO yyyy-MM-dd se não houver
                                                                       // formatter no parse original
                String mesAno = data.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                BigDecimal valor = transacao.getValor();
                consolidado.put(mesAno, consolidado.getOrDefault(mesAno, 0.0) + valor.doubleValue());
            } catch (Exception e) {
                // Log erro parsing data
            }
        }
        return consolidado;
    }

    private Map<String, Double> consolidarPorMesSaida(List<TransacaoSaida> transacoes) {
        Map<String, Double> consolidado = new HashMap<>();
        for (TransacaoSaida transacao : transacoes) {
            try {
                LocalDate data = LocalDate.parse(transacao.getData());
                String mesAno = data.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                BigDecimal valor = transacao.getValor();
                consolidado.put(mesAno, consolidado.getOrDefault(mesAno, 0.0) + valor.doubleValue());
            } catch (Exception e) {
                // Log erro
            }
        }
        return consolidado;
    }

    private Map<String, Double> calcularSaldoAcumulado(Map<String, Double> consolidadoEntrada,
            Map<String, Double> consolidadoSaida) {
        Map<String, Double> saldoAcumulado = new HashMap<>();
        double saldo = 0.0;

        // Note: Map keys are not sorted, so accumulation might be weird if not sorted
        // by date.
        // IndexController implementation was also simple iteration. Keeping as is for
        // parity.
        for (String mesAno : consolidadoEntrada.keySet()) {
            double entrada = consolidadoEntrada.getOrDefault(mesAno, 0.0);
            double saida = consolidadoSaida.getOrDefault(mesAno, 0.0);
            saldo += entrada - saida;
            saldoAcumulado.put(mesAno, saldo);
        }
        return saldoAcumulado;
    }

    public String getConsolidadoEntradaJson() {
        return toJson(consolidadoEntrada);
    }

    public String getConsolidadoSaidaJson() {
        return toJson(consolidadoSaida);
    }

    public String getSaldoAcumuladoJson() {
        return toJson(saldoAcumulado);
    }

    private String toJson(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    // Getters
    public List<Membro> getAniversariantes() {
        return aniversariantes;
    }

    public List<Aviso> getAvisosDoMes() {
        return avisosDoMes;
    }
}
