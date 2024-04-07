package com.agro360.vd.achat;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum BonCommandeStatusEnumVd {
	BRLN("Brouillon"),
    ATAP("Att. Appro"),
    APPR("Approuvée"),
    ANNL("Annulée"),
    REJT("Rejetée"),
    RECP("Recep. Partielle"),
    RECT("Recep. Totale");
	
	public static final int COLUMN_LENGTH = 4;
	
	private final String libelle;
    
    BonCommandeStatusEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
	
	public static Map<Object, String> getAsMap() {
		return Arrays.stream(values()).collect(Collectors.toMap(e->e.name(), e->e.libelle));
	}
}
