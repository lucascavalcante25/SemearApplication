package com.semearApp.semearApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.semearApp.semearApp.models.Membro;

public interface MembroRepository extends CrudRepository<Membro, Long> {

	Membro findById(long id);

	@Query(value = "SELECT * FROM Membro m WHERE EXTRACT(MONTH FROM TO_DATE(m.data_nascimento, 'DD/MM/YYYY')) = EXTRACT(MONTH FROM CURRENT_DATE) AND EXTRACT(DAY FROM TO_DATE(m.data_nascimento, 'DD/MM/YYYY')) <= EXTRACT(DAY FROM CURRENT_DATE)", nativeQuery = true)
	List<Membro> findAniversariantesDoMes();
	
    Membro findByCpf(String cpf); 


}
