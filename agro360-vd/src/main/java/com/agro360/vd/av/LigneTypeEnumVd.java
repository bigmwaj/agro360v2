package com.agro360.vd.av;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum LigneTypeEnumVd {
	SSTD("Serv. Standard"), 
	MATR("Matière"), 
	SERV("Service"), 
	ARTC("Article");
	
	private final String libelle;
	
	private LigneTypeEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
	
	public static Map<Object, String> getAsMap() {
		return Arrays.stream(values()).collect(Collectors.toMap(e -> e.name(), e -> e.libelle));
	}
}
