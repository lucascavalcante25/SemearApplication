package com.semearApp.semearApp.models;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.semearApp.semearApp.enums.TipoLouvorEnum;

@Entity
public class Louvor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String nome;
	private String artista;
	private String tonalidade;
	private Integer andamento;
	private String linkVersao;

	@Column(columnDefinition = "boolean default false")
	private boolean noGrupo;

	@Column(name = "tipo_louvor_enum", nullable = false)
	@Enumerated(EnumType.STRING)
	@ElementCollection
	private List<TipoLouvorEnum> tipoLouvorEnum;

	@Column(columnDefinition = "boolean default false")
	private boolean ativo;

	@ManyToMany(mappedBy = "louvores")
	private List<GruposDeMusicas> gruposDeMusicas;

	public boolean isNoGrupo() {
		return noGrupo;
	}

	public void setNoGrupo(boolean noGrupo) {
		this.noGrupo = noGrupo;
	}

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

	public String getTonalidade() {
		return tonalidade;
	}

	public void setTonalidade(String tonalidade) {
		this.tonalidade = tonalidade;
	}

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

	public List<TipoLouvorEnum> getTipoLouvorEnum() {
		return tipoLouvorEnum;
	}

	public void setTipoLouvorEnum(List<TipoLouvorEnum> tipoLouvorEnum) {
		this.tipoLouvorEnum = tipoLouvorEnum;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<GruposDeMusicas> getGruposDeMusicas() {
		return gruposDeMusicas;
	}

	public void setGruposDeMusicas(List<GruposDeMusicas> gruposDeMusicas) {
		this.gruposDeMusicas = gruposDeMusicas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(andamento, artista, ativo, gruposDeMusicas, id, linkVersao, noGrupo, nome, tipoLouvorEnum,
				tonalidade);
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
		return Objects.equals(andamento, other.andamento) && Objects.equals(artista, other.artista)
				&& ativo == other.ativo && Objects.equals(gruposDeMusicas, other.gruposDeMusicas) && id == other.id
				&& Objects.equals(linkVersao, other.linkVersao) && noGrupo == other.noGrupo
				&& Objects.equals(nome, other.nome) && Objects.equals(tipoLouvorEnum, other.tipoLouvorEnum)
				&& Objects.equals(tonalidade, other.tonalidade);
	}

}
