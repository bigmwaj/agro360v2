package com.agro360.vd.production.avicole;

public enum RubriqueEnumVd {
	BRLN("Brouillon"), 
	APPR("Approuvé"), 
	ENCR("En Cours"), 
	TERM("Terminé");
	
	public static final int COLUMN_LENGTH = 16;
	
	private final String libelle;
	
	private RubriqueEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
