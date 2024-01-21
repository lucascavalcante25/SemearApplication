package com.semearApp.semearApp.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Louvor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nome;
	private String artista;
//	private String letra;
//	private String cifra;
	private String tonalidade;
	private Integer andamento;
	private String linkVersao;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

//	public String getLetra() {
//		return letra;
//	}
//
//	public void setLetra(String letra) {
//		this.letra = letra;
//	}
//
//	public String getCifra() {
//		return cifra;
//	}
//
//	public void setCifra(String cifra) {
//		this.cifra = cifra;
//	}
	
	public Integer getAndamento() {
		return andamento;
	}

	public void setAndamento(Integer andamento) {
		this.andamento = andamento;
	}


	public String getLinkVersao() {
		return linkVersao;
	}

	public void setLinkVersao(String linkVersao) {
		this.linkVersao = linkVersao;
	}

	public String getTonalidade() {
		return tonalidade;
	}

	public void setTonalidade(String tonalidade) {
		this.tonalidade = tonalidade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(andamento, artista, id, linkVersao, nome, tonalidade);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Louvor other = (Louvor) obj;
		return Objects.equals(andamento, other.andamento) && Objects.equals(artista, other.artista) && id == other.id
				&& Objects.equals(linkVersao, other.linkVersao) && Objects.equals(nome, other.nome)
				&& Objects.equals(tonalidade, other.tonalidade);
	}

}
