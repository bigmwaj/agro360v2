package com.agro360.vd.finance;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum CompteTypeEnumVd {

	GENERAL("Général"),
	
	EMPLOYE("Employé"),
	
	FOURNISSEUR("Fournisseur"),
	
	CLIENT("Client");
	
	public static final int COLUMN_LENGTH = 12;
	
	private final String libelle;
	
	private CompteTypeEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
	
	public static Map<Object, String> getAsMap() {
		return Arrays.stream(values()).collect(Collectors.toMap(e->e.name(), e->e.libelle));
	}
}
