package com.semearApp.semearApp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.semearApp.semearApp.enums.TipoLouvorEnum;
import com.semearApp.semearApp.models.Louvor;
import com.semearApp.semearApp.repository.LouvorRepository;

@Controller
public class LouvorController {

	@Autowired
	private LouvorRepository louvorRepository;

	@RequestMapping("/cadastrarLouvor")
	public String form() {
		return "louvor/lista-louvor";
	}

	@PostMapping("/cadastrarLouvor")
	public String cadastrarLouvor(@Valid Louvor louvor, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
		} else {
			louvorRepository.save(louvor);
			attributes.addFlashAttribute("mensagem", "Louvor cadastrado com sucesso!");
		}

		return "redirect:/louvores";
	}

	// GET que lista Louvors
	@RequestMapping("/louvores")
	public ModelAndView listaLouvores() {
		ModelAndView mv = new ModelAndView("louvor/lista-louvor");
		Iterable<Louvor> louvor = louvorRepository.findAll();
		mv.addObject("louvor", louvor);
		return mv;
	}

//	// GET que lista dependentes e detalhes dos Louvor
//	@RequestMapping("/detalhes-Louvor/{id}")
//	public ModelAndView detalhesLouvor(@PathVariable("id") long id) {
//		Louvor louvor = louvorRepository.findById(id);
//		ModelAndView mv = new ModelAndView("louvor/detalhes-louvor");
//		mv.addObject("louvor", louvor);
//
//		return mv;
//
//	}

	// GET que chama o FORM de edição do Louvor
	@RequestMapping("/editar-louvor")
	public ModelAndView editarLouvor(@RequestParam long id) {
		Louvor louvor = louvorRepository.findById(id);
		ModelAndView mv = new ModelAndView("louvor/update-louvor");
		mv.addObject("louvor", louvor);
		mv.addObject("tiposLouvorEnum", TipoLouvorEnum.values()); // Adiciona todos os tipos para preencher os
																	// checkboxes
		return mv;
	}

	// Método que atualiza o Louvor
	@PostMapping("/atualizar-louvor")
	public String atualizarLouvor(@ModelAttribute Louvor louvor, BindingResult result, RedirectAttributes attributes) {

		Louvor louvorExistente = louvorRepository.findById(louvor.getId());

		// Verifica se o Louvor existe antes de atualizar
		if (louvorExistente != null) {
			// Atualize as propriedades do Louvor existente com os dados do formulário
			louvorExistente.setNome(louvor.getNome());
			louvorExistente.setArtista(louvor.getArtista());
			louvorExistente.setTonalidade(louvor.getTonalidade());
			louvorExistente.setAndamento(louvor.getAndamento());
			louvorExistente.setLinkVersao(louvor.getLinkVersao());
			louvorExistente.setAtivo(louvor.isAtivo());

			// Remove todas as opções antigas
	        louvorExistente.getTipoLouvorEnum().clear();

	        // Adiciona as novas opções
	        if (louvor.getTipoLouvorEnum() != null) {
	            louvorExistente.getTipoLouvorEnum().addAll(louvor.getTipoLouvorEnum());
	        }

			// Salva o Louvor atualizado no repositório
			louvorRepository.save(louvorExistente);
			attributes.addFlashAttribute("successs", "Louvor alterado com sucesso!");

			// Redireciona de volta para a página de lista de louvores
			return "redirect:/louvores";
		} else {
			attributes.addFlashAttribute("erro", "Erro ao tentar atualizar!");
		}
		return "redirect:/louvores";
	}

	@RequestMapping("/deletarLouvor")
	public String deletarLouvor(@RequestParam long id) {
		Louvor louvor = louvorRepository.findById(id);
		louvorRepository.delete(louvor);

		return "redirect:/louvores";
	}

}
