package com.agro360.vd.av;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum CommandeStatusEnumVd {
	BRLN("Brouillon"),
	RGLM("Règlement En cours"),
    TERM("Terminée"),
    ATAP("Att. Approbation"),
    APPR("Approuvée"),
    AANN("Att. Annulation"),
    ANNL("Annulée"),
    CLOT("Clôturée"),
    RECP("Recep. Partielle"),
    RECT("Recep. Totale");
	
	public static final int COLUMN_LENGTH = 4;
	
	private final String libelle;
    
    CommandeStatusEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
	
	public static Map<Object, String> getAsMap() {
		return Arrays.stream(values()).collect(Collectors.toMap(e -> e.name(), e -> e.libelle));
	}
}
