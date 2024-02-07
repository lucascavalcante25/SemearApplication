package com.semearApp.semearApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.semearApp.semearApp.models.Visitante;

public interface VisitanteRepository extends CrudRepository<Visitante, Long>{
	
	
	@Query(value = "SELECT * FROM public.visitante ORDER by data desc", nativeQuery = true)
	List<Visitante> buscarListaDeVisitantesOrdenada();
	

}
