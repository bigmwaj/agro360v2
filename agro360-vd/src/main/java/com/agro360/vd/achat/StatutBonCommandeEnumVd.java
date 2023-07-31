package com.agro360.vd.achat;

public enum StatutBonCommandeEnumVd {
	BRLN("Brouillon"),
    ATAP("Att. Appro"),
    APPR("Approuvée"),
    ANNL("Annulée"),
    REJT("Rejetée"),
    RECP("Recep. Partielle"),
    RECT("Recep. Totale");
	
	public static final int COLUMN_LENGTH = 4;
	
	private final String libelle;
    
    StatutBonCommandeEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
