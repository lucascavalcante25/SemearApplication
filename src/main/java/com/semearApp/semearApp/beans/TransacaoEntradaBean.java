package com.semearApp.semearApp.beans;

import com.semearApp.semearApp.models.TransacaoEntrada;
import com.semearApp.semearApp.repository.TransacaoEntradaRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("session")
public class TransacaoEntradaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private TransacaoEntradaRepository transacaoEntradaRepository;

    private List<TransacaoEntrada> transacoes;
    private TransacaoEntrada transacao = new TransacaoEntrada();

    @PostConstruct
    public void init() {
        transacoes = new ArrayList<>();
        transacao = new TransacaoEntrada();
        carregarTransacoes();
    }

    public void carregarTransacoes() {
        transacoes = (List<TransacaoEntrada>) transacaoEntradaRepository.findAll();
    }

    public String salvar() {
        if (transacao == null) {
            return "erro.xhtml?faces-redirect=true"; // Redireciona para uma página de erro
        }
        transacaoEntradaRepository.save(transacao);
        carregarTransacoes();
        transacao = new TransacaoEntrada();
        return "dashboard.xhtml?faces-redirect=true";
    }

    public List<TransacaoEntrada> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<TransacaoEntrada> transacoes) {
        this.transacoes = transacoes;
    }

    public TransacaoEntrada getTransacao() {
        return transacao;
    }

    public void setTransacao(TransacaoEntrada transacao) {
        this.transacao = transacao;
    }
}
