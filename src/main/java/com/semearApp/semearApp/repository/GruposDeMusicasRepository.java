package com.semearApp.semearApp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.semearApp.semearApp.models.GruposDeMusicas;

public interface GruposDeMusicasRepository extends CrudRepository<GruposDeMusicas, Long>{
	
    Optional<GruposDeMusicas> findByNome(String nome);

	

}
