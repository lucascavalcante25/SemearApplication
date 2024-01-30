package com.semearApp.semearApp.models;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class GruposDeMusicas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany
    private List<Louvor> louvores;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Louvor> getLouvores() {
		return louvores;
	}

	public void setLouvores(List<Louvor> louvores) {
		this.louvores = louvores;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, louvores, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GruposDeMusicas other = (GruposDeMusicas) obj;
		return Objects.equals(id, other.id) && Objects.equals(louvores, other.louvores)
				&& Objects.equals(nome, other.nome);
	}
    

}
