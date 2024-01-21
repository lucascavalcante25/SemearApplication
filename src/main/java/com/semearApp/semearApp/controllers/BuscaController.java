package com.semearApp.semearApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.semearApp.semearApp.repository.LouvorRepository;

@Controller
public class BuscaController {
	
	@Autowired
	private LouvorRepository louvorRepository;
	
//	@Autowired
//	private VagaRepository vr;
//	
//	@Autowired
//	private DependenteRepository dr;
//	
//	@Autowired
//	private CandidatoRepository cr;
	
	//GET
	@RequestMapping("/")
	public ModelAndView abrirIndex() {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
	//POST
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView buscarIndex(@RequestParam("buscar") String buscar, @RequestParam("nome") String nome) {
		
		ModelAndView mv = new ModelAndView("index");
		String mensagem = "Resultados da busca por " + buscar;
		
		if(nome.equals("nomelouvor")) {
			mv.addObject("louvores", louvorRepository.findByNomes(buscar));
			
//		}else if(nome.equals("nomedependente")) {
//			mv.addObject("dependentes", dr.findByNomesDependentes(buscar));
//			
//		}else if(nome.equals("nomecandidato")) {
//			mv.addObject("candidatos", cr.findByNomesCandidatos(buscar));
//			
//		}else if(nome.equals("titulovaga")) {
//			mv.addObject("vagas", vr.findByNomesVaga(buscar));
			
		}else {
			mv.addObject("louvores", louvorRepository.findByNomes(buscar));
//			mv.addObject("dependentes", dr.findByNomesDependentes(buscar));
//			mv.addObject("candidatos", cr.findByNomesCandidatos(buscar));
//			mv.addObject("vagas", vr.findByNomesVaga(buscar));
		}
		
		mv.addObject("mensagem", mensagem);
		
		return mv;
	}

}
