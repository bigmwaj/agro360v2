package com.agro360.vd.stock;

public enum StatutCaisseEnumVd {
	OUVERTE("Ouverte"), 
	FERMEE("Fermée");
	
	public static final int COLUMN_LENGTH = 8;
	
	private final String libelle;
	
	private StatutCaisseEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
