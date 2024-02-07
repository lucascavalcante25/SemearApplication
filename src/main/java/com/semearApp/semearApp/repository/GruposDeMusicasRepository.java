package com.semearApp.semearApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.semearApp.semearApp.models.GruposDeMusicas;

public interface GruposDeMusicasRepository extends CrudRepository<GruposDeMusicas, Long> {

	Optional<GruposDeMusicas> findByNome(String nome);

	@Query(value = "select gruposdemusicas.louvores_id as idLouvor,  gruposdemusicas.grupos_de_musicas_id as idGrupoDoLouvor, gruposdemusicasLouvores.id as idGrupo, gruposdemusicasLouvores.nome as nomeGrupo from grupos_de_musicas_louvores gruposdemusicas inner join grupos_de_musicas gruposdemusicasLouvores on gruposdemusicas.grupos_de_musicas_id = gruposdemusicasLouvores.id where gruposdemusicas.louvores_id = :idLouvor", nativeQuery = true)
	Optional<GruposDeMusicas> findByNomeGrupoPorPouvor(@Param("idLouvor") Long idLouvor);
	
    GruposDeMusicas findByLouvoresId(Long louvorId);


}
