package com.semearApp.semearApp.models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Membro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomeCompleto;
	private String cpf;
	private String genero;
	private String dataNascimento;
	private String endereco;
	private String telefone;
	private String telefoneEmergencia;
	private String email;
	private String dataBatismo;
	private String cargoMinisterio;
	private String estadoCivil;
	private String senha;
	private String observacoes;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

//	public void setSenha(String senha) {
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		this.senha = passwordEncoder.encode(senha);
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTelefoneEmergencia() {
		return telefoneEmergencia;
	}

	public void setTelefoneEmergencia(String telefoneEmergencia) {
		this.telefoneEmergencia = telefoneEmergencia;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDataBatismo() {
		return dataBatismo;
	}

	public void setDataBatismo(String dataBatismo) {
		this.dataBatismo = dataBatismo;
	}

	public String getCargoMinisterio() {
		return cargoMinisterio;
	}

	public void setCargoMinisterio(String cargoMinisterio) {
		this.cargoMinisterio = cargoMinisterio;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cargoMinisterio, cpf, dataBatismo, dataNascimento, email, endereco, estadoCivil, genero, id,
				nomeCompleto, observacoes, senha, telefone, telefoneEmergencia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Membro other = (Membro) obj;
		return Objects.equals(cargoMinisterio, other.cargoMinisterio) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(dataBatismo, other.dataBatismo)
				&& Objects.equals(dataNascimento, other.dataNascimento) && Objects.equals(email, other.email)
				&& Objects.equals(endereco, other.endereco) && Objects.equals(estadoCivil, other.estadoCivil)
				&& Objects.equals(genero, other.genero) && Objects.equals(id, other.id)
				&& Objects.equals(nomeCompleto, other.nomeCompleto) && Objects.equals(observacoes, other.observacoes)
				&& Objects.equals(senha, other.senha) && Objects.equals(telefone, other.telefone)
				&& Objects.equals(telefoneEmergencia, other.telefoneEmergencia);
	}

}
