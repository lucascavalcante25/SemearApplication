package com.semearApp.semearApp.beans;

import com.semearApp.semearApp.models.TransacaoSaida;
import com.semearApp.semearApp.repository.TransacaoSaidaRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("session")
public class TransacaoSaidaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private TransacaoSaidaRepository transacaoSaidaRepository;

    private List<TransacaoSaida> transacoes;
    private TransacaoSaida transacao = new TransacaoSaida();

    @PostConstruct
    public void init() {
        transacoes = new ArrayList<>();
        transacao = new TransacaoSaida();
        carregarTransacoes();
    }

    public void carregarTransacoes() {
        transacoes = (List<TransacaoSaida>) transacaoSaidaRepository.findAll();
    }

    public String salvar() {
        if (transacao == null) {
            return "erro.xhtml?faces-redirect=true"; // Redireciona para uma página de erro
        }
        transacaoSaidaRepository.save(transacao);
        carregarTransacoes();
        transacao = new TransacaoSaida();
        return "dashboard.xhtml?faces-redirect=true";
    }

    public List<TransacaoSaida> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<TransacaoSaida> transacoes) {
        this.transacoes = transacoes;
    }

    public TransacaoSaida getTransacao() {
        return transacao;
    }

    public void setTransacao(TransacaoSaida transacao) {
        this.transacao = transacao;
    }
}
