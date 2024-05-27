package com.agro360.vd.av;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FactureStatusEnumVd {
	BRLN("Brouillon"),
    ATAP("Att. Appro"),
    TERM("Terminée"),
	RGLM("Règlement En cours", true),
    SOLD("Soldée", true),
    ANNL("Annulée"),
    AANN("Att. annulation"),
    REJT("Rejetée"),
    CLOT("Cloturée");
	
	private final String libelle;
	
	/**
	 * Si true, on ne doit pas lister le statut au front-end lors du changement de status
	 */
	private final boolean internalOnly;
    
    FactureStatusEnumVd(String libelle) {
		this.libelle = libelle;
		this.internalOnly = false;
	}
    
    FactureStatusEnumVd(String libelle, boolean internalOnly) {
    	this.libelle = libelle;
    	this.internalOnly = internalOnly;
    }

	public String getLibelle() {
		return libelle;
	}
	
	public static Map<Object, String> getAsMap() {
		return Arrays.stream(values()).collect(Collectors.toMap(e -> e.name(), e -> e.libelle));
	}

	public boolean isInternalOnly() {
		return internalOnly;
	}
}
