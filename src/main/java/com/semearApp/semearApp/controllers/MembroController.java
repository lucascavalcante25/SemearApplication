package com.semearApp.semearApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.semearApp.semearApp.models.Membro;
import com.semearApp.semearApp.repository.MembroRepository;

@Controller
public class MembroController {

	@Autowired
	private MembroRepository membroRepository;

	@GetMapping("/lista-membros")
	public String mostrarListaMembros(Model model) {
		// Adicionar todos os membros cadastrados ao modelo
		Iterable<Membro> membro = membroRepository.findAll();
		model.addAttribute("membro", membro);
		return "membro/lista-membros";
	}

	@PostMapping("/cadastrar-membro")
	public String cadastrarMembro(@ModelAttribute Membro membro) {
		// Salvar o novo membro no banco de dados
		membroRepository.save(membro);
		// Redirecionar de volta para a lista de membros
		return "redirect:/lista-membros";
	}

	// GET que chama o FORM de edição do membro
	@RequestMapping("/editar-membro")
	public ModelAndView editarMembro(@RequestParam long id) {
		Membro membro = membroRepository.findById(id);
		ModelAndView mv = new ModelAndView("membro/update-membro");
		mv.addObject("membro", membro);
		return mv;
	}

	@PostMapping("/salvar-edicao-membro")
	public String salvarEdicaoMembro(@ModelAttribute Membro membro) {
		// Salvar as alterações do membro no banco de dados
		membroRepository.save(membro);
		// Redirecionar de volta para a lista de membros
		return "redirect:/lista-membros";
	}

	@RequestMapping("/remover-membro")
	public String removerMembro(@RequestParam long id) {
		Membro membro = membroRepository.findById(id);
		membroRepository.delete(membro);
		// Redirecionar de volta para a lista de membros
		return "redirect:/lista-membros";
	}

}
