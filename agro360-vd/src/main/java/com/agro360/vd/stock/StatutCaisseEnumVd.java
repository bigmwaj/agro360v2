package com.agro360.vd.stock;

public enum StatutCaisseEnumVd {
	OUVERTE("Ouverte"), 
	FERMEE("Fermée");
	
	private final String libelle;
	
	private StatutCaisseEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
