package com.agro360.vd.finance;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TransactionTypeEnumVd {

	DEPOT("Dépot"),
	
	DEPENSE("Dépense"),
	
	RETRAIT("Retrait"),
	
	RECETTE("Recette"),
	
	DEBIT("Débit"),
	
	CREDIT("Crédit");
	
	private final String libelle;
	
	private TransactionTypeEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
	
	public static Map<Object, String> getAsMap() {
		return Arrays.stream(values()).collect(Collectors.toMap(e->e.name(), e->e.libelle));
	}
}
