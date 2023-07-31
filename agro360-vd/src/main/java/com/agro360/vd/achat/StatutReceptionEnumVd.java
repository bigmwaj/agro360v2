package com.agro360.vd.achat;

public enum StatutReceptionEnumVd {
    ATAP("Att. Appro"),
    APPR("Approuvée"),
    ANNL("Annulée");
	
	public static final int COLUMN_LENGTH = 4;
	
	private final String libelle;
    
    StatutReceptionEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}