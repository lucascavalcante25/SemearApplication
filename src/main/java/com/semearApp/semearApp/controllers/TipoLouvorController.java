package com.semearApp.semearApp.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.semearApp.semearApp.enums.TipoLouvorEnum;

@Controller
public class TipoLouvorController  implements Serializable {

	private static final long serialVersionUID = 1L;
	public List<TipoLouvorEnum> getListaTipoLouvorEnum() {

		List<TipoLouvorEnum> listaTipoLouvorEnum = new ArrayList<TipoLouvorEnum>();
		for (TipoLouvorEnum tipoLouvorEnum : TipoLouvorEnum.values()) {
			listaTipoLouvorEnum.add(tipoLouvorEnum);
		}
		return listaTipoLouvorEnum;
	}
	
	public TipoLouvorEnum getADORACAO() {
		return TipoLouvorEnum.ADORACAO;
	}
	
	public TipoLouvorEnum getJUBILO() {
		return TipoLouvorEnum.JUBILO;
	}
	
	public TipoLouvorEnum getCEIA() {
		return TipoLouvorEnum.CEIA;
	}
	
}
