package com.agro360.vd.stock;

public enum StatusCaisseEnumVd {
	ENPREPAR("En Préparation"),
	OUVERTE("Ouverte"), 
	FERMEE("Fermée");
	
	public static final int COLUMN_LENGTH = 8;
	
	private final String libelle;
	
	private StatusCaisseEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
