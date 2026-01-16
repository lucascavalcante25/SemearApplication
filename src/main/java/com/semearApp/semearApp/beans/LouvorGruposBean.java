package com.semearApp.semearApp.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import com.semearApp.semearApp.enums.TipoLouvorEnum;
import com.semearApp.semearApp.models.GruposDeMusicas;
import com.semearApp.semearApp.models.Louvor;
import com.semearApp.semearApp.repository.GruposDeMusicasRepository;
import com.semearApp.semearApp.service.LouvorService;

@Component
@Scope("session")
public class LouvorGruposBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private LouvorService louvorService;

    @Autowired
    private GruposDeMusicasRepository gruposDeMusicasRepository;

    private List<Louvor> jubiloLouvores = new ArrayList<>();
    private List<Louvor> adoracaoLouvores = new ArrayList<>();
    private List<Louvor> ceiaLouvores = new ArrayList<>();

    private List<Louvor> grupo1 = new ArrayList<>();
    private List<Louvor> grupo2 = new ArrayList<>();
    private List<Louvor> grupo3 = new ArrayList<>();
    private List<Louvor> grupo4 = new ArrayList<>();
    private List<Louvor> grupo5 = new ArrayList<>();
    private List<Louvor> grupo6 = new ArrayList<>();
    private List<Louvor> grupo7 = new ArrayList<>();
    private List<Louvor> grupo8 = new ArrayList<>();

    @PostConstruct
    public void init() {
        carregarLouvores();
        carregarGrupos();
    }

    public void carregarLouvores() {
        List<Louvor> louvores = louvorService.findAllActive();
        jubiloLouvores.clear();
        adoracaoLouvores.clear();
        ceiaLouvores.clear();

        if (louvores == null) {
            return;
        }

        for (Louvor louvor : louvores) {
            if (louvor.isNoGrupo()) {
                continue;
            }
            List<TipoLouvorEnum> tipos = louvor.getTipoLouvorEnum();
            if (tipos == null || tipos.isEmpty()) {
                continue;
            }
            if (tipos.contains(TipoLouvorEnum.JUBILO)) {
                jubiloLouvores.add(louvor);
            } else if (tipos.contains(TipoLouvorEnum.ADORACAO)) {
                adoracaoLouvores.add(louvor);
            } else if (tipos.contains(TipoLouvorEnum.CEIA)) {
                ceiaLouvores.add(louvor);
            }
        }
    }

    public void carregarGrupos() {
        grupo1.clear();
        grupo2.clear();
        grupo3.clear();
        grupo4.clear();
        grupo5.clear();
        grupo6.clear();
        grupo7.clear();
        grupo8.clear();

        Iterable<GruposDeMusicas> grupos = gruposDeMusicasRepository.findAll();
        for (GruposDeMusicas grupo : grupos) {
            if (grupo.getLouvores() == null) {
                continue;
            }
            switch (grupo.getNome()) {
                case "Grupo 1":
                    grupo1.addAll(grupo.getLouvores());
                    break;
                case "Grupo 2":
                    grupo2.addAll(grupo.getLouvores());
                    break;
                case "Grupo 3":
                    grupo3.addAll(grupo.getLouvores());
                    break;
                case "Grupo 4":
                    grupo4.addAll(grupo.getLouvores());
                    break;
                case "Grupo 5":
                    grupo5.addAll(grupo.getLouvores());
                    break;
                case "Grupo 6":
                    grupo6.addAll(grupo.getLouvores());
                    break;
                case "Grupo 7":
                    grupo7.addAll(grupo.getLouvores());
                    break;
                case "Grupo 8":
                    grupo8.addAll(grupo.getLouvores());
                    break;
                default:
                    break;
            }
        }
    }

    public List<Louvor> getJubiloLouvores() {
        return jubiloLouvores;
    }

    public List<Louvor> getAdoracaoLouvores() {
        return adoracaoLouvores;
    }

    public List<Louvor> getCeiaLouvores() {
        return ceiaLouvores;
    }

    public List<Louvor> getGrupo1() {
        return grupo1;
    }

    public List<Louvor> getGrupo2() {
        return grupo2;
    }

    public List<Louvor> getGrupo3() {
        return grupo3;
    }

    public List<Louvor> getGrupo4() {
        return grupo4;
    }

    public List<Louvor> getGrupo5() {
        return grupo5;
    }

    public List<Louvor> getGrupo6() {
        return grupo6;
    }

    public List<Louvor> getGrupo7() {
        return grupo7;
    }

    public List<Louvor> getGrupo8() {
        return grupo8;
    }

    public String getTipoPrincipal(Louvor louvor) {
        if (louvor == null || louvor.getTipoLouvorEnum() == null) {
            return "";
        }
        if (louvor.getTipoLouvorEnum().contains(TipoLouvorEnum.JUBILO)) {
            return "JUBILO";
        }
        if (louvor.getTipoLouvorEnum().contains(TipoLouvorEnum.ADORACAO)) {
            return "ADORACAO";
        }
        if (louvor.getTipoLouvorEnum().contains(TipoLouvorEnum.CEIA)) {
            return "CEIA";
        }
        return "";
    }
}
