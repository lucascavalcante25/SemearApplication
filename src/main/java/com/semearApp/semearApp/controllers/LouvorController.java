package com.semearApp.semearApp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.semearApp.semearApp.enums.TipoLouvorEnum;
import com.semearApp.semearApp.models.GruposDeMusicas;
import com.semearApp.semearApp.models.Louvor;
import com.semearApp.semearApp.repository.GruposDeMusicasRepository;
import com.semearApp.semearApp.repository.LouvorRepository;

@Controller
public class LouvorController {

	@Autowired
	private LouvorRepository louvorRepository;

	@Autowired
	private GruposDeMusicasRepository gruposDeMusicasRepository;

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

	// GET que lista musicas por grupo
	@RequestMapping("/grupos-musicas")
	public ModelAndView gruposMusicas(Model model) {
		ModelAndView mv = new ModelAndView("louvor/grupos-musicas");

		// Recupera todos os louvores ativos do repositório
		List<Louvor> louvores = louvorRepository.findByAtivoTrue();

		// Filtra os louvores por tipo e ativo
		List<Louvor> jubiloLouvores = new ArrayList<Louvor>();
		List<Louvor> adoracaoLouvores = new ArrayList<Louvor>();
		List<Louvor> ceiaLouvores = new ArrayList<Louvor>();

		if (louvores != null) {
			for (Louvor list : louvores) {
				if (!list.isNoGrupo()) {
					if (list.getTipoLouvorEnum().toString().equals("[JUBILO]")) {
						jubiloLouvores.add(list);
					} else if (list.getTipoLouvorEnum().toString().equals("[ADORACAO]")) {
						adoracaoLouvores.add(list);
					} else {
						ceiaLouvores.add(list);
					}
				}
			}
		}

		// Adiciona as listas de louvores ao modelo
		mv.addObject("jubiloLouvores", jubiloLouvores);
		mv.addObject("adoracaoLouvores", adoracaoLouvores);
		mv.addObject("ceiaLouvores", ceiaLouvores);

		return mv;
	}

	@PostMapping("/salvar-grupos-musicas")
	public ModelAndView salvarGruposMusicas(@ModelAttribute GruposDeMusicas gruposDeMusicas) {
		// Recupera os grupos existentes
		GruposDeMusicas grupoExistente = gruposDeMusicasRepository.findById(gruposDeMusicas.getId()).orElse(null);

		if (grupoExistente != null) {
			// Atualiza a lista de louvores associada ao grupo
			grupoExistente.getLouvores().clear();
			grupoExistente.getLouvores().addAll(gruposDeMusicas.getLouvores());
			// Atualiza outras propriedades conforme necessário

			// Salva as alterações
			gruposDeMusicasRepository.save(grupoExistente);
		} else {
			// Se o grupo não existir, cria um novo
			gruposDeMusicasRepository.save(gruposDeMusicas);
		}

		// Redireciona para a página de grupos de músicas ou outra página desejada
		return new ModelAndView("redirect:/grupos-musicas");
	}
	
	@PostMapping("/salvar-louvor-ao-grupo")
	public ResponseEntity<String> adicionarLouvorAoGrupo(@RequestParam Long louvorId, @RequestParam Long grupoId) {
	    System.out.println("Recebendo solicitação para adicionar louvor ao grupo. Louvor ID: " + louvorId + ", Grupo ID: " + grupoId);

	    Optional<Louvor> louvorOptional = louvorRepository.findById(louvorId);
	    Optional<GruposDeMusicas> grupoOptional = gruposDeMusicasRepository.findById(grupoId);

//	    Louvor existe e não está em nenhum grupo
	    if (louvorOptional.isPresent() && !grupoOptional.isPresent()) {
	    	List<Louvor> listaLouvorAtualizada = new ArrayList<Louvor>();
	        Louvor louvor = louvorOptional.get();
	        listaLouvorAtualizada.add(louvor);
	        
	        GruposDeMusicas grupoDeMusicas = new GruposDeMusicas();

	        louvor.setNoGrupo(true); // Atualiza o atributo noGrupo
	        louvorRepository.save(louvor);
	        

	        grupoDeMusicas.setLouvores(listaLouvorAtualizada);// Adiciona o louvor ao grupo
	        gruposDeMusicasRepository.save(grupoDeMusicas);

	        return ResponseEntity.ok("Louvor adicionado ao grupo com sucesso!");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Louvor ou grupo não encontrado");
	    }
	}



}
