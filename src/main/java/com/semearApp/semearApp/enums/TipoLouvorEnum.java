package com.semearApp.semearApp.enums;

public enum TipoLouvorEnum {

    ADORACAO(0, "Adoração"),
    JUBILO(1, "Júbilo"),
    CEIA(2, "Ceia");
	
	private Integer id;
	private String descricao;

	private TipoLouvorEnum(Integer id, String descricao) {
		this.id=id;
		this.descricao=descricao;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
