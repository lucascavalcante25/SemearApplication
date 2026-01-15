package com.semearApp.semearApp.beans;

import com.semearApp.semearApp.models.Louvor;
import com.semearApp.semearApp.repository.LouvorRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("session")
public class LouvorBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private LouvorRepository louvorRepository;

    private List<Louvor> louvores;
    private Louvor louvor = new Louvor();

    @PostConstruct
    public void init() {
        louvores = new ArrayList<>();
        louvor = new Louvor();
        carregarLouvores();
    }

    public void carregarLouvores() {
        louvores = (List<Louvor>) louvorRepository.findAll();
    }

    public String salvar() {
        if (louvor == null) {
            return "erro.xhtml?faces-redirect=true"; // Redireciona para uma página de erro
        }
        louvorRepository.save(louvor);
        carregarLouvores();
        louvor = new Louvor();
        return "lista-louvor.xhtml?faces-redirect=true";
    }

    public String editar(Long id) {
        if (id == null) {
            return "erro.xhtml?faces-redirect=true"; // Redireciona para uma página de erro
        }
        louvor = louvorRepository.findById(id).orElse(new Louvor());
        return "update-louvor.xhtml?faces-redirect=true";
    }

    public String remover(Long id) {
        if (id == null) {
            return "erro.xhtml?faces-redirect=true"; // Redireciona para uma página de erro
        }
        louvorRepository.findById(id).ifPresent(l -> louvorRepository.delete(l));
        carregarLouvores();
        return "lista-louvor.xhtml?faces-redirect=true";
    }

    public List<Louvor> getLouvores() {
        return louvores;
    }

    public void setLouvores(List<Louvor> louvores) {
        this.louvores = louvores;
    }

    public Louvor getLouvor() {
        return louvor;
    }

    public void setLouvor(Louvor louvor) {
        this.louvor = louvor;
    }
}
