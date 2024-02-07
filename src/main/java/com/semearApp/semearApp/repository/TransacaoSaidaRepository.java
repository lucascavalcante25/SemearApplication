package com.semearApp.semearApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.semearApp.semearApp.models.TransacaoSaida;

public interface TransacaoSaidaRepository extends CrudRepository<TransacaoSaida, Long>{
	
    List<TransacaoSaida> findByDataBetween(String startDate, String endDate);

}
