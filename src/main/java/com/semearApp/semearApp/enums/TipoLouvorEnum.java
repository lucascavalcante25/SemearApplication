package com.semearApp.semearApp.enums;

public enum TipoLouvorEnum {

	ADORACAO(0, "Adoração"), JUBILO(1, "Júbilo"), CEIA(2, "Ceia");

	private Integer id;
	private String descricao;

	private TipoLouvorEnum(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoLouvorEnum fromId(Integer id) {
		for (TipoLouvorEnum tipo : TipoLouvorEnum.values()) {
			if (tipo.getId().equals(id)) {
				return tipo;
			}
		}
		throw new IllegalArgumentException("ID de TipoLouvorEnum inválido: " + id);
	}
}
