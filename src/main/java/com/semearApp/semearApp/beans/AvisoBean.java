package com.semearApp.semearApp.beans;

import com.semearApp.semearApp.models.Aviso;
import com.semearApp.semearApp.repository.AvisoRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("session")
public class AvisoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private AvisoRepository avisoRepository;

    private List<Aviso> avisos;
    private Aviso aviso = new Aviso();

    @PostConstruct
    public void init() {
        avisos = new ArrayList<>();
        aviso = new Aviso();
        carregarAvisos();
    }

    public void carregarAvisos() {
        avisos = (List<Aviso>) avisoRepository.findAll();
    }

    public String salvar() {
        if (aviso == null) {
            return "erro.xhtml?faces-redirect=true"; // Redireciona para uma página de erro
        }
        avisoRepository.save(aviso);
        carregarAvisos();
        aviso = new Aviso();
        return "lista-aviso.xhtml?faces-redirect=true";
    }

    public String editar(Long id) {
        if (id == null) {
            return "erro.xhtml?faces-redirect=true"; // Redireciona para uma página de erro
        }
        aviso = avisoRepository.findById(id).orElse(new Aviso());
        return "update-aviso.xhtml?faces-redirect=true";
    }

    public String remover(Long id) {
        if (id == null) {
            return "erro.xhtml?faces-redirect=true"; // Redireciona para uma página de erro
        }
        avisoRepository.findById(id).ifPresent(a -> avisoRepository.delete(a));
        carregarAvisos();
        return "lista-aviso.xhtml?faces-redirect=true";
    }

    public List<Aviso> getAvisos() {
        return avisos;
    }

    public void setAvisos(List<Aviso> avisos) {
        this.avisos = avisos;
    }

    public Aviso getAviso() {
        return aviso;
    }

    public void setAviso(Aviso aviso) {
        this.aviso = aviso;
    }
}
