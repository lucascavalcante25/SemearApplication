package com.semearApp.semearApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.semearApp.semearApp.models.TransacaoSaida;
import com.semearApp.semearApp.repository.TransacaoSaidaRepository;

@Controller
public class TransacaoSaidaController {

    @Autowired
    private TransacaoSaidaRepository transacaoSaidaRepository;

    @GetMapping("/cadastro-transacao-saida")
    public String mostrarFormularioCadastroSaide(Model model) {
        model.addAttribute("transacaoSaida", new TransacaoSaida());
        return "financeiro/cadastro-transacao-saida";
    }

    @PostMapping("/cadastrar-transacao-saida")
    public String cadastrarTransacaoSaida(@ModelAttribute("transacaoSaida") TransacaoSaida transacaoSaida) {
    	transacaoSaidaRepository.save(transacaoSaida);
        return "redirect:/cadastro-transacao-saida";
    }
    
}
