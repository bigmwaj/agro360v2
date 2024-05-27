package com.agro360.vd.av;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum CommandeStatusEnumVd {
	BRLN("Brouillon"),
	ATAP("Att. Approbation"),
	AANN("Att. Annulation"),
	RGLM("Règlement En cours", true),
	TERM("Approuvée"),
    SOLD("Terminée", true),
    RECP("Recep. Partielle", true),
    RECT("Recep. Totale", true),
    CLOT("Clôturée"),
    ANNL("Annulée");
	
	private final String libelle;
	
	/**
	 * Si true, on ne doit pas lister le statut au front-end lors du changement de status
	 */
	private final boolean internalOnly;
    
    CommandeStatusEnumVd(String libelle) {
		this.libelle = libelle;
		this.internalOnly = false;
	}
    
    CommandeStatusEnumVd(String libelle, boolean internalOnly) {
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
