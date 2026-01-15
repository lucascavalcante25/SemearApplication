package com.semearApp.semearApp.beans;

import com.semearApp.semearApp.models.Membro;
import com.semearApp.semearApp.repository.MembroRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("session")
public class MembroBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private MembroRepository membroRepository;

    private List<Membro> membros;
    private Membro membro = new Membro();

    @PostConstruct
    public void init() {
        membros = new ArrayList<>();
        membro = new Membro();
        carregarMembros();
    }

    public void carregarMembros() {
        membros = (List<Membro>) membroRepository.findAll();
    }

    public String salvar() {
        if (membro == null) {
            return "erro.xhtml?faces-redirect=true"; // Redireciona para uma página de erro
        }
        membroRepository.save(membro);
        carregarMembros();
        membro = new Membro();
        return "lista-membros.xhtml?faces-redirect=true";
    }

    public String editar(Long id) {
        if (id == null) {
            return "erro.xhtml?faces-redirect=true"; // Redireciona para uma página de erro
        }
        membro = membroRepository.findById(id).orElse(new Membro());
        return "update-membro.xhtml?faces-redirect=true";
    }

    public String remover(Long id) {
        if (id == null) {
            return "erro.xhtml?faces-redirect=true"; // Redireciona para uma página de erro
        }
        membroRepository.findById(id).ifPresent(m -> membroRepository.delete(m));
        carregarMembros();
        return "lista-membros.xhtml?faces-redirect=true";
    }

    public List<Membro> getMembros() {
        return membros;
    }

    public void setMembros(List<Membro> membros) {
        this.membros = membros;
    }

    public Membro getMembro() {
        return membro;
    }

    public void setMembro(Membro membro) {
        this.membro = membro;
    }
}
