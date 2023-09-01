package com.agro360.vd.vente;

public enum StatusCommandeEnumVd {
	BRLN("Brouillon"),
    ATAP("Att. Appro"),
    APPR("Approuvée"),
    ANNL("Annulée"),
    REJT("Rejetée");
	
	public static final int COLUMN_LENGTH = 4;
	
	private final String libelle;
    
	StatusCommandeEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
