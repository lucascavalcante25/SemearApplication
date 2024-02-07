package com.semearApp.semearApp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.semearApp.semearApp.models.TransacaoEntrada;

public interface TransacaoEntradaRepository extends CrudRepository<TransacaoEntrada, Long>{
	
    List<TransacaoEntrada> findByDataBetween(String startDate, String endDate);
	

}
