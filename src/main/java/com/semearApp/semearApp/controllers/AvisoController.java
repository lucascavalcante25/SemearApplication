package com.semearApp.semearApp.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.semearApp.semearApp.models.Aviso;
import com.semearApp.semearApp.repository.AvisoRepository;

@Controller
public class AvisoController {

	@Autowired
	private AvisoRepository avisoRepository;

	LocalDate dataAtual = LocalDate.now();

	@GetMapping("/lista-aviso")
	public String mostrarListaAvisos(Model model) {
		// Adicionar todos os aviso cadastrados ao modelo
		Iterable<Aviso> aviso = avisoRepository.findAll();
		model.addAttribute("aviso", aviso);
		return "aviso/lista-aviso";
	}

	@PostMapping("/cadastrar-aviso")
	public String cadastrarAviso(@ModelAttribute Aviso aviso) {
		// Salvar o novo aviso no banco de dados
		avisoRepository.save(aviso);
		// Redirecionar de volta para a lista de aviso
		return "redirect:/lista-aviso";
	}

	// GET que chama o FORM de edição do aviso
	@RequestMapping("/editar-aviso")
	public ModelAndView editarAviso(@RequestParam long id) {
		Aviso aviso = avisoRepository.findById(id);
		ModelAndView mv = new ModelAndView("aviso/update-aviso");
		mv.addObject("aviso", aviso);
		return mv;
	}

	@PostMapping("/salvar-edicao-aviso")
	public String salvarEdicaoMembro(@ModelAttribute Aviso aviso) {
		// Salvar as alterações do membro no banco de dados
		avisoRepository.save(aviso);
		// Redirecionar de volta para a lista de aviso
		return "redirect:/lista-aviso";
	}

	@RequestMapping("/remover-aviso")
	public String removerAviso(@RequestParam long id) {
		Aviso aviso = avisoRepository.findById(id);
		avisoRepository.delete(aviso);
		// Redirecionar de volta para a lista de aviso
		return "redirect:/lista-aviso";
	}

}
