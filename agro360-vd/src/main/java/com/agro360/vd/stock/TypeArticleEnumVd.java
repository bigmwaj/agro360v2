package com.agro360.vd.stock;

public enum TypeArticleEnumVd {
	ARTC("Article"),
	
	SSTD("Serv. Standard");
	
	private final String libelle;
	
	private TypeArticleEnumVd(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
