package com.agro360.vd.tiers;

public enum TiersTypeEnumVd {

	PERSON("Person"),
	
	COMPANY("Company");
	
	private final String libelle;
	
	private TiersTypeEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
	
}
