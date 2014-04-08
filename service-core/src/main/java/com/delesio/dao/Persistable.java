package com.delesio.dao;

import java.io.Serializable;

public interface Persistable {

	
	/**
	 * Return the object primary key
	 * 
	 * @return Serializable
	 */
	public Serializable getPrimaryKey();
	
}
