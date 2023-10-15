package com.agro360.vd.production.avicole;

public enum ProductionCategoryEnumVd {
	PRODUIT("Produit"), 
	INTRANT("Intrants"), 
	REBUS("Rébus");
	
	public static final int COLUMN_LENGTH = 8;
	
	private final String libelle;
	
	private ProductionCategoryEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
