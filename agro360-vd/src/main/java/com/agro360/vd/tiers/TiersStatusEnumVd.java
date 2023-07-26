package com.agro360.vd.tiers;

public enum TiersStatusEnumVd {

	ACTIVE("Active");
	
	private final String libelle;
	
	private TiersStatusEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
