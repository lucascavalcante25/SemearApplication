package com.semearApp.semearApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.semearApp.semearApp.models.Louvor;

public interface LouvorRepository extends CrudRepository<Louvor, Long>{
	
	Louvor findById(long id);
	Louvor findByNome(String nome);
	
	// Query para a busca
	@Query(value = "select u from Louvor u where u.nome like %?1%")
	List<Louvor>findByNomes(String nome);

}
