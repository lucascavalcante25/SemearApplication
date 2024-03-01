package com.semearApp.semearApp.controllers;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.semearApp.semearApp.models.Membro;
import com.semearApp.semearApp.repository.MembroRepository;

@Controller
public class IndexController {

	@Autowired
	private MembroRepository membroRepository;

	// Método para filtrar membros aniversariantes do mês atual
	private List<Membro> getAniversariantesDoMes() {
		Iterable<Membro> membrosIterable = membroRepository.findAll(); // Obtém todos os membros do repositório
		List<Membro> membros = new ArrayList<>();
		membrosIterable.forEach(membros::add); // Convertendo de Iterable para List
		List<Membro> aniversariantesDoMes = new ArrayList<>();
		LocalDate hoje = LocalDate.now();
		Month mesAtual = hoje.getMonth();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for (Membro membro : membros) {
			LocalDate dataNascimento = LocalDate.parse(membro.getDataNascimento(), formatter);
			if (dataNascimento.getMonth() == mesAtual) {
				aniversariantesDoMes.add(membro);
			}
		}
		return aniversariantesDoMes;
	}

	// GET
	@RequestMapping("/")
	public ModelAndView abrirIndex() {
		ModelAndView mv = new ModelAndView("index");
		// Obtém os membros aniversariantes do mês atual
		List<Membro> aniversariantes = getAniversariantesDoMes();
		mv.addObject("aniversariantes", aniversariantes);
		return mv;
	}
}
