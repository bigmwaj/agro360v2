package com.agro360.vd.stock;

public enum TypeOperationEnumVd {
	RECETTE("Recette"), 
	DEPENSE("Dépense");
	
	public static final int COLUMN_LENGTH = 8;
	
	private final String libelle;
	
	private TypeOperationEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
