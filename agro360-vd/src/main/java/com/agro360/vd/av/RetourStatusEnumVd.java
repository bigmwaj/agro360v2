package com.agro360.vd.av;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum RetourStatusEnumVd {
	BRLN("Brouillon"),
    ATAP("Att. Appro"),
    APPR("Approuvée"),
    ANNL("Annulée"),
    REJT("Rejetée");
	
	public static final int COLUMN_LENGTH = 4;
	
	private final String libelle;
    
    RetourStatusEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
	
	public static Map<Object, String> getAsMap() {
		return Arrays.stream(values()).collect(Collectors.toMap(e->e.name(), e->e.libelle));
	}
}
