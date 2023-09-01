package com.agro360.vd.common;

public enum EditActionEnumVd {

	/**
	 * L'information est synchronisée avec la source de donnée(BD)
	 */
	SYNC,
	
	/**
	 * L'information doit être créée dans la source de données(BD)
	 */
	CREATE,
	
	/**
	 * L'information doit être modifiée dans la source de données(BD)
	 */
	UPDATE,
	
	/**
	 * L'information doit être supprimée dans la source de données(BD)
	 */
	DELETE,
	
	/**
	 * Le statut de l'information doit être modifiée dans la source de données(BD)
	 */
	CHANGE_STATUS;
	
}
