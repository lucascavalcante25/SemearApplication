package com.semearApp.semearApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.semearApp.semearApp.models.Aviso;
import com.semearApp.semearApp.models.Louvor;

public interface AvisoRepository extends CrudRepository<Aviso, Long>{
	
	
	@Query(value = "SELECT * FROM public.aviso ORDER by data desc", nativeQuery = true)
	List<Aviso> buscarListaDeAvisosOrdenada();
	
	Aviso findById(long id);

}
