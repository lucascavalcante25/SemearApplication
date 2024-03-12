package com.semearApp.semearApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.semearApp.semearApp.models.TransacaoEntrada;
import com.semearApp.semearApp.repository.TransacaoEntradaRepository;

@Controller
public class TransacaoEntradaController {

    @Autowired
    private TransacaoEntradaRepository transacaoEntradaRepository;

    
    @GetMapping("/cadastro-transacao-entrada")
    public String mostrarFormularioCadastroEntrada(Model model) {
        model.addAttribute("transacaoEntrada", new TransacaoEntrada());
        Iterable<TransacaoEntrada> transacoesEntrada = transacaoEntradaRepository.findAll();
        // Adiciona as transações de entrada ao modelo
        model.addAttribute("transacoesEntrada", transacoesEntrada);
        return "financeiro/cadastro-transacao-entrada";
    }

    @PostMapping("/cadastrar-transacao-entrada")
    public String cadastrarTransacaoEntrada(@ModelAttribute("transacaoEntrada") TransacaoEntrada transacaoEntrada) {
    	transacaoEntradaRepository.save(transacaoEntrada);
        return "redirect:/cadastro-transacao-entrada";
    }
    
}
