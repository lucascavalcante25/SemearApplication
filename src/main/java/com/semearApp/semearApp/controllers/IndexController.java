package com.semearApp.semearApp.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.semearApp.semearApp.models.Aviso;
import com.semearApp.semearApp.models.Membro;
import com.semearApp.semearApp.models.TransacaoEntrada;
import com.semearApp.semearApp.models.TransacaoSaida;
import com.semearApp.semearApp.repository.AvisoRepository;
import com.semearApp.semearApp.repository.MembroRepository;
import com.semearApp.semearApp.repository.TransacaoEntradaRepository;
import com.semearApp.semearApp.repository.TransacaoSaidaRepository;

@Controller
public class IndexController {

	@Autowired
	private TransacaoEntradaRepository transacaoEntradaRepository;
	@Autowired
	private TransacaoSaidaRepository transacaoSaidaRepository;

	@Autowired
	private MembroRepository membroRepository;

	@Autowired
	private AvisoRepository avisoRepository;

	// Método para filtrar membros aniversariantes do mês atual
	private List<Membro> getAniversariantesDoMes() {
		Iterable<Membro> membrosIterable = membroRepository.findAll(); // Obtém todos os membros do repositório
		List<Membro> membros = new ArrayList<>();
		membrosIterable.forEach(membros::add); // Convertendo de Iterable para List
		List<Membro> aniversariantesDoMes = new ArrayList<>();
		LocalDate hoje = LocalDate.now();
		Month mesAtual = hoje.getMonth();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for (Membro membro : membros) {
			LocalDate dataNascimento = LocalDate.parse(membro.getDataNascimento(), formatter);
			if (dataNascimento.getMonth() == mesAtual) {
				aniversariantesDoMes.add(membro);
			}
		}
		return aniversariantesDoMes;
	}

	// Método para filtrar Avisos do mês atual
	private List<Aviso> getAvisos() {
		LocalDate hoje = LocalDate.now();
		Iterable<Aviso> avisoIterable = avisoRepository.findAll();
		List<Aviso> avisos = new ArrayList<>();
		avisoIterable.forEach(avisos::add);
		List<Aviso> avisosDoMes = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for (Aviso aviso : avisos) {
			LocalDate data = LocalDate.parse(aviso.getData(), formatter);
			if (data.isAfter(hoje))
				avisosDoMes.add(aviso);
			if (aviso.isAvisoPadrao())
				avisosDoMes.add(aviso);

		}
		return avisosDoMes;
	}

	// GET
	@RequestMapping("/")
	public ModelAndView abrirIndex() {
		ModelAndView mv = new ModelAndView("index");
		// Obtém os membros aniversariantes do mês atual
		List<Membro> aniversariantes = getAniversariantesDoMes();
		List<Aviso> avisosDoMes = getAvisos();
		
		// Busca todas as transações de entrada e saída e converte para lista
		Iterable<TransacaoEntrada> iterableTransacoesEntrada = transacaoEntradaRepository.findAll();
		List<TransacaoEntrada> transacoesEntrada = new ArrayList<>();
		iterableTransacoesEntrada.forEach(transacoesEntrada::add);

		Iterable<TransacaoSaida> iterableTransacoesSaida = transacaoSaidaRepository.findAll();
		List<TransacaoSaida> transacoesSaida = new ArrayList<>();
		iterableTransacoesSaida.forEach(transacoesSaida::add);

		// Consolida os dados mês a mês
		Map<String, Double> consolidadoEntrada = consolidarPorMesEntrada(transacoesEntrada);
		Map<String, Double> consolidadoSaida = consolidarPorMesSaida(transacoesSaida);
		// Calcula o saldo acumulado para cada mês
		Map<String, Double> saldoAcumulado = calcularSaldoAcumulado(consolidadoEntrada, consolidadoSaida);

		// Adiciona os dados consolidados ao modelo
		mv.addObject("consolidadoEntrada", consolidadoEntrada);
		mv.addObject("consolidadoSaida", consolidadoSaida);
		mv.addObject("saldoAcumulado", saldoAcumulado);

		mv.addObject("aniversariantes", aniversariantes);
		mv.addObject("avisosDoMes", avisosDoMes);
		return mv;
	}

	private Map<String, Double> consolidarPorMesEntrada(List<TransacaoEntrada> transacoes) {
		Map<String, Double> consolidado = new HashMap<>();

		for (TransacaoEntrada transacao : transacoes) {
			LocalDate data = LocalDate.parse(transacao.getData());
			String mesAno = data.format(DateTimeFormatter.ofPattern("yyyy-MM")); // Formato "yyyy-MM" para agrupar por
																					// ano e mês
			BigDecimal valor = transacao.getValor();

			// Adiciona o valor da transação ao valor consolidado do mês correspondente
			consolidado.put(mesAno, consolidado.getOrDefault(mesAno, 0.0) + valor.doubleValue());
		}

		return consolidado;
	}

	private Map<String, Double> consolidarPorMesSaida(List<TransacaoSaida> transacoes) {
		Map<String, Double> consolidado = new HashMap<>();

		for (TransacaoSaida transacao : transacoes) {
			LocalDate data = LocalDate.parse(transacao.getData());
			String mesAno = data.format(DateTimeFormatter.ofPattern("yyyy-MM")); // Formato "yyyy-MM" para agrupar por
																					// ano e mês
			BigDecimal valor = transacao.getValor();

			// Adiciona o valor da transação ao valor consolidado do mês correspondente
			consolidado.put(mesAno, consolidado.getOrDefault(mesAno, 0.0) + valor.doubleValue());
		}

		return consolidado;
	}

	private Map<String, Double> calcularSaldoAcumulado(Map<String, Double> consolidadoEntrada,
			Map<String, Double> consolidadoSaida) {
		Map<String, Double> saldoAcumulado = new HashMap<>();
		double saldo = 0.0;

		for (String mesAno : consolidadoEntrada.keySet()) {
			double entrada = consolidadoEntrada.getOrDefault(mesAno, 0.0);
			double saida = consolidadoSaida.getOrDefault(mesAno, 0.0);
			saldo += entrada - saida;
			saldoAcumulado.put(mesAno, saldo);
		}

		return saldoAcumulado;
	}

}
