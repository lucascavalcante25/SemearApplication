package com.semearApp.semearApp.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.semearApp.semearApp.enums.TipoLouvorEnum;
import com.semearApp.semearApp.models.GruposDeMusicas;
import com.semearApp.semearApp.models.Louvor;
import com.semearApp.semearApp.repository.GruposDeMusicasRepository;
import com.semearApp.semearApp.repository.LouvorRepository;
import com.semearApp.semearApp.service.LouvorService;

@Controller
public class LouvorController {

	@Autowired
	private LouvorRepository louvorRepository;

	@Autowired
	private GruposDeMusicasRepository gruposDeMusicasRepository;

	@Autowired
	private LouvorService louvorService;

	@RequestMapping("/cadastrarLouvor")
	public String form(Model model, MultipartFile file) {
		model.addAttribute("louvor", new Louvor());
		return "louvor/cadastrar-louvor";
	}

	@PostMapping("/cadastrarLouvor")
	public String cadastrarLouvor(@Valid Louvor louvor, @RequestParam("file") MultipartFile file, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/cadastrarLouvor";
		}

		try {
			// Define os bytes do arquivo na entidade Louvor
			louvor.setArquivo(file.getBytes());

			// Salva o louvor no banco de dados
			louvorRepository.save(louvor);

			attributes.addFlashAttribute("mensagem", "Louvor cadastrado com sucesso!");
		} catch (IOException e) {
			attributes.addFlashAttribute("mensagem", "Erro ao salvar o arquivo.");
			e.printStackTrace();
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

	@GetMapping("/visualizarArquivo")
	public ResponseEntity<byte[]> visualizarArquivo(@RequestParam long id) {
		Louvor louvor = louvorRepository.findById(id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDisposition(
				ContentDisposition.builder("attachment").filename(louvor.getNome() + " cifra.pdf").build());
		headers.setContentLength(louvor.getArquivo().length);
		return ResponseEntity.ok().headers(headers).body(louvor.getArquivo());
	}

	@PostMapping("/atualizar-louvor")
	public String atualizarLouvor(@ModelAttribute Louvor louvor, @RequestParam("file") MultipartFile file,
			BindingResult result, RedirectAttributes attributes) {

		Louvor louvorExistente = louvorRepository.findById(louvor.getId());

		// Verifica se o Louvor existe antes de atualizar
		if (louvorExistente != null) {
			try {
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

				// Se um novo arquivo foi enviado, atualiza o arquivo no Louvor existente
				if (!file.isEmpty()) {
					louvorExistente.setArquivo(file.getBytes());
				}

				// Salva o Louvor atualizado no repositório
				louvorRepository.save(louvorExistente);
				attributes.addFlashAttribute("successs", "Louvor alterado com sucesso!");
			} catch (IOException e) {
				attributes.addFlashAttribute("erro", "Erro ao processar o arquivo.");
				e.printStackTrace();
			}

			// Redireciona de volta para a página de lista de louvores
			return "redirect:/louvores";
		} else {
			attributes.addFlashAttribute("erro", "Erro ao tentar atualizar!");
			return "redirect:/louvores";
		}
	}

	@RequestMapping("/deletarLouvor")
	public String deletarLouvor(@RequestParam long id) {
		Louvor louvor = louvorRepository.findById(id);
		louvorRepository.delete(louvor);

		return "redirect:/louvores";
	}

	@RequestMapping(value = "/baixar-arquivo/{louvorId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> baixarArquivo(@PathVariable Long louvorId) {
		Louvor louvor = louvorRepository.findById(louvorId).orElse(null);

		if (louvor != null && louvor.getArquivo() != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", "cifra.pdf"); // nome do arquivo de exemplo

			return new ResponseEntity<>(louvor.getArquivo(), headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// GET que lista musicas por grupo
	@RequestMapping("/grupos-musicas")
	public ModelAndView gruposMusicas(Model model) {
		ModelAndView mv = new ModelAndView("louvor/grupos-musicas");

		// Recupera todos os louvores ativos do repositório
		List<Louvor> louvores = louvorService.findAllActive();

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

		// Filtra os louvores por tipo e ativo
		List<GruposDeMusicas> grupoMusicas1 = new ArrayList<GruposDeMusicas>();
		List<GruposDeMusicas> grupoMusicas2 = new ArrayList<GruposDeMusicas>();
		List<GruposDeMusicas> grupoMusicas3 = new ArrayList<GruposDeMusicas>();
		List<GruposDeMusicas> grupoMusicas4 = new ArrayList<GruposDeMusicas>();
		List<GruposDeMusicas> grupoMusicas5 = new ArrayList<GruposDeMusicas>();
		List<GruposDeMusicas> grupoMusicas6 = new ArrayList<GruposDeMusicas>();
		List<GruposDeMusicas> grupoMusicas7 = new ArrayList<GruposDeMusicas>();
		List<GruposDeMusicas> grupoMusicas8 = new ArrayList<GruposDeMusicas>();

		// Adiciona a lista de grupos ao modelo
		Iterable<GruposDeMusicas> grupos = obterGrupos();

		for (GruposDeMusicas listaGrupo : grupos) {
			if (listaGrupo.getNome().equals("Grupo 1"))
				grupoMusicas1.add(listaGrupo);
			else if (listaGrupo.getNome().equals("Grupo 2"))
				grupoMusicas2.add(listaGrupo);
			else if (listaGrupo.getNome().equals("Grupo 3"))
				grupoMusicas3.add(listaGrupo);
			else if (listaGrupo.getNome().equals("Grupo 4"))
				grupoMusicas4.add(listaGrupo);
			else if (listaGrupo.getNome().equals("Grupo 5"))
				grupoMusicas5.add(listaGrupo);
			else if (listaGrupo.getNome().equals("Grupo 6"))
				grupoMusicas6.add(listaGrupo);
			else if (listaGrupo.getNome().equals("Grupo 7"))
				grupoMusicas7.add(listaGrupo);
			else if (listaGrupo.getNome().equals("Grupo 8"))
				grupoMusicas8.add(listaGrupo);

		}

		mv.addObject("grupoMusicas1", grupoMusicas1);
		List<Louvor> lg1 = new ArrayList<Louvor>();
		if (grupoMusicas1 != null) {
			for (GruposDeMusicas l : grupoMusicas1) {
				for (Louvor lteste : l.getLouvores()) {
					lg1.add(lteste);
				}
			}
		}
		mv.addObject("lg1", lg1);

		mv.addObject("grupoMusicas2", grupoMusicas2);
		List<Louvor> lg2 = new ArrayList<Louvor>();
		if (grupoMusicas2 != null) {
			for (GruposDeMusicas l : grupoMusicas2) {
				for (Louvor lteste : l.getLouvores()) {
					lg2.add(lteste);
				}
			}
		}
		mv.addObject("lg2", lg2);

		mv.addObject("grupoMusicas3", grupoMusicas3);
		List<Louvor> lg3 = new ArrayList<Louvor>();
		if (grupoMusicas3 != null) {
			for (GruposDeMusicas l : grupoMusicas3) {
				for (Louvor lteste : l.getLouvores()) {
					lg3.add(lteste);
				}
			}
		}
		mv.addObject("lg3", lg3);

		mv.addObject("grupoMusicas4", grupoMusicas4);
		List<Louvor> lg4 = new ArrayList<Louvor>();
		if (grupoMusicas4 != null) {
			for (GruposDeMusicas l : grupoMusicas4) {
				for (Louvor lteste : l.getLouvores()) {
					lg4.add(lteste);
				}
			}
		}
		mv.addObject("lg4", lg4);

		mv.addObject("grupoMusicas5", grupoMusicas5);
		List<Louvor> lg5 = new ArrayList<Louvor>();
		if (grupoMusicas5 != null) {
			for (GruposDeMusicas l : grupoMusicas5) {
				for (Louvor lteste : l.getLouvores()) {
					lg5.add(lteste);
				}
			}
		}
		mv.addObject("lg5", lg5);

		mv.addObject("grupoMusicas6", grupoMusicas6);
		List<Louvor> lg6 = new ArrayList<Louvor>();
		if (grupoMusicas6 != null) {
			for (GruposDeMusicas l : grupoMusicas6) {
				for (Louvor lteste : l.getLouvores()) {
					lg6.add(lteste);
				}
			}
		}
		mv.addObject("lg6", lg6);

		mv.addObject("grupoMusicas7", grupoMusicas7);
		List<Louvor> lg7 = new ArrayList<Louvor>();
		if (grupoMusicas7 != null) {
			for (GruposDeMusicas l : grupoMusicas7) {
				for (Louvor lteste : l.getLouvores()) {
					lg7.add(lteste);
				}
			}
		}
		mv.addObject("lg7", lg7);

		mv.addObject("grupoMusicas8", grupoMusicas8);
		List<Louvor> lg8 = new ArrayList<Louvor>();
		if (grupoMusicas8 != null) {
			for (GruposDeMusicas l : grupoMusicas8) {
				for (Louvor lteste : l.getLouvores()) {
					lg8.add(lteste);
				}
			}
		}
		mv.addObject("lg8", lg8);

		return mv;
	}

	@GetMapping("/obter-grupos")
	public Iterable<GruposDeMusicas> obterGrupos() {
		return gruposDeMusicasRepository.findAll(); // Substitua pelo método correto do seu repositório
	}

	@PostMapping("/salvar-louvor-ao-grupo")
	public ResponseEntity<String> adicionarLouvorAoGrupo(@RequestParam Long louvorId, @RequestParam Long grupoId,
			@RequestParam String nomeDoGrupo) {

		System.out.println("Recebendo solicitação para adicionar ou alterar louvor ao grupo. Louvor ID: " + louvorId
				+ ", Grupo ID: " + grupoId);

		Optional<Louvor> louvorOptional = louvorRepository.findById(louvorId);

		if (louvorOptional.isPresent()) {
			Louvor louvor = louvorOptional.get();

			if (louvor.getGruposDeMusicas() == null || louvor.getGruposDeMusicas().isEmpty()) {
				// Louvor existe e não está em nenhum grupo, então adiciona ao grupo
				GruposDeMusicas grupoDeMusicas = new GruposDeMusicas();
				grupoDeMusicas.setLouvores(new ArrayList<>()); // Inicializa a lista de louvores
				grupoDeMusicas.setNome(nomeDoGrupo);
				grupoDeMusicas.getLouvores().add(louvor);
				gruposDeMusicasRepository.save(grupoDeMusicas);

				louvor.setNoGrupo(true);
				louvorRepository.save(louvor);

				return ResponseEntity.ok("Louvor adicionado ao grupo com sucesso!");
			} else {
				// Louvor já está em algum grupo, então apenas atualiza o grupo
				GruposDeMusicas grupoAtual = gruposDeMusicasRepository.findByLouvoresId(louvorId);

				if (grupoAtual != null) {
					// Atualiza o nome do grupo
					grupoAtual.setNome(nomeDoGrupo);
					gruposDeMusicasRepository.save(grupoAtual);

					return ResponseEntity.ok("Grupo do louvor atualizado com sucesso!");
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grupo do louvor não encontrado.");
				}
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Louvor não encontrado.");
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	@PostMapping("/mover-para-lista-de-origem")
	public ResponseEntity<String> moverParaOrigem(@RequestParam Long louvorId, @RequestParam String tipoLouvor) {
		try {
			Optional<Louvor> louvorOptional = louvorRepository.findById(louvorId);

			if (louvorOptional.isPresent()) {
				Louvor louvor = louvorOptional.get();

				// Lógica para remover o louvor do grupo
				louvor.getGruposDeMusicas().remove(louvor);
				louvor.setNoGrupo(false);

				// Louvor já está em algum grupo, então apenas atualiza o grupo
				GruposDeMusicas grupoAtual = gruposDeMusicasRepository.findByLouvoresId(louvorId);

				if (grupoAtual != null) {
					// Atualiza o nome do grupo
					gruposDeMusicasRepository.delete(grupoAtual);
				}

				louvorRepository.save(louvor);

				return ResponseEntity.ok("Louvor movido de volta para a lista principal com sucesso.");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Louvor ou origem não encontrado.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao mover o louvor de volta para a lista de origem: " + e.getMessage());
		}
	}

}
