package com.agro360.vd.common;

public enum EditActionEnumVd {

	/**
	 * Synch the underlined bean with the DB
	 */
	SYNC,
	
	/**
	 * Create the underlined bean in the DB
	 */
	CREATE,
	
	/**
	 * Update the underlined bean in the DB
	 */
	UPDATE,
	
	/**
	 * Delete the underlined bean from the DB
	 */
	DELETE,
	
	/**
	 * Change the underlined bean status in the DB
	 */
	CHANGE_STATUS;
	
}
