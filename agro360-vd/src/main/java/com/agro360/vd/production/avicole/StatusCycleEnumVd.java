package com.agro360.vd.production.avicole;

public enum StatusCycleEnumVd {
	BRLN("Brouillon"), 
	APPR("Approuvé"), 
	ENCR("En Cours"), 
	TERM("Terminé");
	
	public static final int COLUMN_LENGTH = 4;
	
	private final String libelle;
	
	private StatusCycleEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
