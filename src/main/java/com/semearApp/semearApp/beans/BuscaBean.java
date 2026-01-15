package com.semearApp.semearApp.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.semearApp.semearApp.models.Louvor;
import com.semearApp.semearApp.repository.LouvorRepository;

@Component
@Scope("session")
public class BuscaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private LouvorRepository louvorRepository;

    private String termoBusca;
    private String tipoBusca = "nomelouvor";
    private List<Louvor> resultadosLouvor;
    private String mensagem;

    public String buscar() {
        resultadosLouvor = new ArrayList<>();
        mensagem = "";

        if (termoBusca != null && !termoBusca.isEmpty()) {
            // Lógica similar ao BuscaController
            // Busca apenas por Louvor por enquanto, pois os outros repositórios estavam
            // comentados no Controller
            if ("nomelouvor".equals(tipoBusca) || true) { // Fallback para louvor como no controller
                resultadosLouvor = louvorRepository.findByNomes(termoBusca);
            }
            mensagem = "Resultados da busca por " + termoBusca;
        }

        // Retorna para a mesma página ou página de resultados
        return null; // reload current page
    }

    // Getters and Setters
    public String getTermoBusca() {
        return termoBusca;
    }

    public void setTermoBusca(String termoBusca) {
        this.termoBusca = termoBusca;
    }

    public String getTipoBusca() {
        return tipoBusca;
    }

    public void setTipoBusca(String tipoBusca) {
        this.tipoBusca = tipoBusca;
    }

    public List<Louvor> getResultadosLouvor() {
        return resultadosLouvor;
    }

    public void setResultadosLouvor(List<Louvor> resultadosLouvor) {
        this.resultadosLouvor = resultadosLouvor;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
