package com.agro360.vd.core;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum UserAccountStatusEnumVd {

	LOCKED("Bloqué"),
	
	ENABLED("Activé"),
	
	DISABLED("Désactivé");
	
	private final String libelle;
	
	private UserAccountStatusEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
	
	public static Map<Object, String> getAsMap() {
		return Arrays.stream(values())
				.collect(Collectors.toMap(UserAccountStatusEnumVd::name, UserAccountStatusEnumVd::getLibelle));
	}
}
