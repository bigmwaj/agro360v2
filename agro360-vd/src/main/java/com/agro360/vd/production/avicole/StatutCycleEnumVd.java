package com.agro360.vd.production.avicole;

public enum StatutCycleEnumVd {
	BRLN("Brouillon"), 
	APPR("Approuvé"), 
	ENCR("En Cours"), 
	TERM("Terminé");
	
	public static final int COLUMN_LENGTH = 4;
	
	private final String libelle;
	
	private StatutCycleEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
