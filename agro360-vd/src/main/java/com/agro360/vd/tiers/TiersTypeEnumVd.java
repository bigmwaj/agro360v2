package com.agro360.vd.tiers;

public enum TiersTypeEnumVd {

	PERSON("Person"),
	
	COMPANY("Company");
	
	public static final int COLUMN_LENGTH = 8;
	
	private final String libelle;
	
	private TiersTypeEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
	
}
