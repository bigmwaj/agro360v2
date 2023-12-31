package com.agro360.vd.stock;

public enum TypeLigneEnumVd {
	SSTD("Serv. Standard"), 
	MATR("Matière"), 
	SERV("Service"), 
	ARTC("Article"), 
	CMDE("Commande");
	
	public static final int COLUMN_LENGTH = 4;
	
	private final String libelle;
	
	private TypeLigneEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
