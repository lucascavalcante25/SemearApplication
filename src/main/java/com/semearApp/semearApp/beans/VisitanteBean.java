package com.semearApp.semearApp.beans;

import com.semearApp.semearApp.models.Visitante;
import com.semearApp.semearApp.repository.VisitanteRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("session")
public class VisitanteBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private VisitanteRepository visitanteRepository;

    private List<Visitante> visitantes;
    private Visitante visitante = new Visitante();

    @PostConstruct
    public void init() {
        visitantes = new ArrayList<>();
        visitante = new Visitante();
        carregarVisitantes();
    }

    public void carregarVisitantes() {
        visitantes = (List<Visitante>) visitanteRepository.findAll();
    }

    public String salvar() {
        if (visitante == null) {
            return "erro.xhtml?faces-redirect=true"; // Redireciona para uma página de erro
        }
        visitanteRepository.save(visitante);
        carregarVisitantes();
        visitante = new Visitante();
        return "lista-visitantes.xhtml?faces-redirect=true";
    }

    public String editar(Long id) {
        if (id == null) {
            return "erro.xhtml?faces-redirect=true"; // Redireciona para uma página de erro
        }
        visitante = visitanteRepository.findById(id).orElse(new Visitante());
        return "update-visitante.xhtml?faces-redirect=true";
    }

    public String remover(Long id) {
        if (id == null) {
            return "erro.xhtml?faces-redirect=true"; // Redireciona para uma página de erro
        }
        visitanteRepository.findById(id).ifPresent(v -> visitanteRepository.delete(v));
        carregarVisitantes();
        return "lista-visitantes.xhtml?faces-redirect=true";
    }

    public List<Visitante> getVisitantes() {
        return visitantes;
    }

    public void setVisitantes(List<Visitante> visitantes) {
        this.visitantes = visitantes;
    }

    public Visitante getVisitante() {
        return visitante;
    }

    public void setVisitante(Visitante visitante) {
        this.visitante = visitante;
    }
}
