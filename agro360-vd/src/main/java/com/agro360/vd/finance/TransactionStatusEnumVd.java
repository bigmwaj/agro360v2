package com.agro360.vd.finance;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TransactionStatusEnumVd {

	ENCOURS("En Cours"),

	ANNULEE("Annulée"),
	
	RESERVEE("Réservée", true),
	
	APPROUVEE("Approuvée"),
	
	CLOTUREE("Clôturée");
	
	private final String libelle;

	/**
	 * Si true, on ne doit pas lister le statut au front-end lors du changement de status
	 */
	private final boolean internalOnly;
	
	private TransactionStatusEnumVd(String libelle) {
		this.libelle = libelle;
		this.internalOnly = false;
	}
	
	private TransactionStatusEnumVd(String libelle, boolean internalOnly) {
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
