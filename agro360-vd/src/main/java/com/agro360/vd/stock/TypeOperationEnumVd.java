package com.agro360.vd.stock;

public enum TypeOperationEnumVd {
	RECETTE("Recette"), 
	DEPENSE("Dépense");
	
	private final String libelle;
	
	private TypeOperationEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
