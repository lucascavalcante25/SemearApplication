package com.semearApp.semearApp.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.semearApp.semearApp.models.GruposDeMusicas;
import com.semearApp.semearApp.models.Louvor;
import com.semearApp.semearApp.repository.GruposDeMusicasRepository;
import com.semearApp.semearApp.repository.LouvorRepository;

@RestController
public class LouvorAjaxController {

    @Autowired
    private LouvorRepository louvorRepository;

    @Autowired
    private GruposDeMusicasRepository gruposDeMusicasRepository;

    @GetMapping("/visualizarArquivo")
    public ResponseEntity<byte[]> visualizarArquivo(@RequestParam long id) {
        Louvor louvor = louvorRepository.findById(id);
        if (louvor == null || louvor.getArquivo() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.builder("attachment").filename(louvor.getNome() + " cifra.pdf").build());
        headers.setContentLength(louvor.getArquivo().length);
        return ResponseEntity.ok().headers(headers).body(louvor.getArquivo());
    }

    @PostMapping("/salvar-louvor-ao-grupo")
    public ResponseEntity<String> adicionarLouvorAoGrupo(@RequestParam Long louvorId,
            @RequestParam(required = false) Long grupoId, @RequestParam(required = false) String nomeDoGrupo) {
        Optional<Louvor> louvorOptional = louvorRepository.findById(louvorId);
        if (louvorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Louvor não encontrado.");
        }

        String nomeGrupo = nomeDoGrupo != null ? nomeDoGrupo
                : (grupoId != null ? "Grupo " + grupoId : null);
        if (nomeGrupo == null || nomeGrupo.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Grupo inválido.");
        }

        Louvor louvor = louvorOptional.get();
        GruposDeMusicas grupoAtual = gruposDeMusicasRepository.findByLouvoresId(louvorId);
        if (grupoAtual != null && !nomeGrupo.equals(grupoAtual.getNome())) {
            grupoAtual.getLouvores().remove(louvor);
            gruposDeMusicasRepository.save(grupoAtual);
        }

        GruposDeMusicas grupo = gruposDeMusicasRepository.findByNome(nomeGrupo).orElse(null);
        if (grupo == null) {
            grupo = new GruposDeMusicas();
            grupo.setNome(nomeGrupo);
            grupo.setLouvores(new ArrayList<>());
        }
        if (!grupo.getLouvores().contains(louvor)) {
            grupo.getLouvores().add(louvor);
        }
        gruposDeMusicasRepository.save(grupo);

        louvor.setNoGrupo(true);
        louvorRepository.save(louvor);

        return ResponseEntity.ok("Louvor adicionado ao grupo com sucesso!");
    }

    @PostMapping("/mover-para-lista-de-origem")
    public ResponseEntity<String> moverParaOrigem(@RequestParam Long louvorId) {
        Optional<Louvor> louvorOptional = louvorRepository.findById(louvorId);
        if (louvorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Louvor não encontrado.");
        }

        Louvor louvor = louvorOptional.get();
        GruposDeMusicas grupoAtual = gruposDeMusicasRepository.findByLouvoresId(louvorId);
        if (grupoAtual != null) {
            grupoAtual.getLouvores().remove(louvor);
            if (grupoAtual.getLouvores().isEmpty()) {
                gruposDeMusicasRepository.delete(grupoAtual);
            } else {
                gruposDeMusicasRepository.save(grupoAtual);
            }
        }

        louvor.setNoGrupo(false);
        louvorRepository.save(louvor);

        return ResponseEntity.ok("Louvor movido de volta para a lista principal.");
    }
}
