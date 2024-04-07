package com.agro360.vd.production.avicole;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum PhaseEnumVd {
	PLAN("Planification"), 
	REAL("Réalisation");
	
	public static final int COLUMN_LENGTH = 4;
	
	private final String libelle;
	
	private PhaseEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
	
	public static Map<Object, String> getAsMap() {
		return Arrays.stream(values()).collect(Collectors.toMap(e->e.name(), e->e.libelle));
	}
}
