package com.agro360.vd.tiers;

public enum TiersStatusEnumVd {

	ACTIVE("Active"),
	
	INACTIVE("Inactive");
	
	public static final int COLUMN_LENGTH = 8;
	
	private final String libelle;
	
	private TiersStatusEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
