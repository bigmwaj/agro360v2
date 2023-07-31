package com.agro360.vd.stock;

public enum TypeArticleEnumVd {
	ARTC("Article"),
	
	SSTD("Serv. Standard");
	
	public static final int COLUMN_LENGTH = 4;
	
	private final String libelle;
	
	private TypeArticleEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
