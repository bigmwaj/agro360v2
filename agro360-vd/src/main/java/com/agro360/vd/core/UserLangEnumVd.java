package com.agro360.vd.core;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum UserLangEnumVd {

	fr("Français"),
	
	en("Anglais");
	
	private final String libelle;
	
	private UserLangEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
	
	public static Map<Object, String> getAsMap() {
		return Arrays.stream(values())
				.collect(Collectors.toMap(UserLangEnumVd::name, UserLangEnumVd::getLibelle));
	}
}
