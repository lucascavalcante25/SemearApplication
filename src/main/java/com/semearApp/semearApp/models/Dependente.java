package com.semearApp.semearApp.models;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.ManyToOne;


@Entity
public class Dependente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true)
	private String cpf;
	
	private String nome;
	private  String datanascimento;
	
	@ManyToOne
	private Louvor funcionario;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDatanascimento() {
		return datanascimento;
	}

	public void setDatanascimento(String datanascimento) {
		this.datanascimento = datanascimento;
	}

	public Louvor getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Louvor funcionario) {
		this.funcionario = funcionario;
	}
	
}
