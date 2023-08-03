package com.agro360.vd.production.avicole;

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
}
