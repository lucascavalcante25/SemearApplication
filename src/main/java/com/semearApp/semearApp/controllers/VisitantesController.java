package com.semearApp.semearApp.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.semearApp.semearApp.models.Visitante;
import com.semearApp.semearApp.repository.VisitanteRepository;
import com.semearApp.semearApp.util.Utils;

@Controller
public class VisitantesController {

	@Autowired
	private VisitanteRepository visitanteRepository;

	LocalDate dataAtual = LocalDate.now();

	// Endpoint para exibir o formulário de cadastro de visitantes
	@GetMapping("/cadastrarVisitante")
	public String form() {
		return "visitante/lista-visitantes";
	}

	// Endpoint para processar o formulário de cadastro de visitantes
	@PostMapping("/cadastrarVisitante")
	public String cadastrarVisitante(@Valid Visitante visitante, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
		} else {

			String dataFormatada = Utils.formatarData(dataAtual);
			visitante.setData(dataFormatada);
			visitante.setNome(visitante.getNome());
			visitante.setCongregacao(visitante.getCongregacao());
			visitanteRepository.save(visitante);
			attributes.addFlashAttribute("mensagem", "Visitante cadastrado com sucesso!");
		}
		return "redirect:/visitantes";
	}

	@GetMapping("/visitantes")
	public ModelAndView listaVisitantes() {
		ModelAndView mv = new ModelAndView("visitante/lista-visitantes");

		// Obtenha todos os visitantes do repositório
		List<Visitante> visitantes = visitanteRepository.buscarListaDeVisitantesOrdenada();

		LocalDate hoje = LocalDate.now();
		String hojeFormat = Utils.formatarData(hoje);
		for (Visitante listaVisitantes : visitantes) {
			if (listaVisitantes.getData().equals(hojeFormat)) {
				listaVisitantes.setData("HOJE");
			}
		}

		mv.addObject("visitantes", visitantes);
		return mv;
	}

}
