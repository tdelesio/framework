package com.delesio.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


public interface IDao {
	/**
	 * Load an Object from the database by primary key
	 * 
	 * @param entityClass
	 * @param primaryKey
	 * @return Object
	 */
	public <T>T loadObject(Class<T> entityClass, Serializable primaryKey);
	
	
	
	/**
	 * Load all Objects from the database
	 * 
	 * @param entityClass
	 */
    public <T> List<T> loadAllObjects(Class<T> entityClass);
    
    /**
	 * Save an Object in the database
	 * 
	 * @param entityName
	 * @param object
	 */
	public <T> Serializable save(T model);
	
	/**
	 * Save all Objects in the database
	 * 
	 * @param entityName
	 * @param object
	 */
	public <T> void saveAllObjects(Collection<T> objects);
//	public void saveAllObjects(Collection<? extends AbstractModel> objects);

	/**
	 * Save or Update an Object in the database
	 * 
	 * @param entityName
	 * @param object
	 */
	public <T> void saveOrUpdate(T object);
	
	 /**
	 * Delete Object from the database
	 * 
	 * @param object
	 */
    public <T> void deleteObject(T object);
        
	/**
	 * Delete All Objects from the database
	 * 
	 * @param objects
	 */
	public <T> void deleteAllObjects(Collection<T> objects);	
	
	public <T> void updateObject(T object);
	
	public <T>T loadByPrimaryKey(Class<T> clazz, long pk);
	public <T>T loadByPrimaryKey(Class<T> clazz, String pk);
	public <T>T loadByPrimaryKey(Class<T> clazz, Serializable pk);
	public <T> void merge(T object);
	public void flush();
	public void persist(Object object);
	public <T> void refresh(T object);
}
