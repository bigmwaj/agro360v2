package com.agro360.service.bean.stock;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CaisseIdBean implements Serializable {

	private static final long serialVersionUID = 2661082196384553129L;

	private String magasin;
	
	private String agent;

	private LocalDate journee;

	public CaisseIdBean(String magasin, String agent, LocalDate journee) {
		super();
		this.magasin = magasin;
		this.agent = agent;
		this.journee = journee;
	}

}
