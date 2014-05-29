package com.delesio.dao.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.delesio.SpringBean;
import com.delesio.dao.Persistable;

public class HibernateDao extends SpringBean {

	private SessionFactory sessionFactory;
	
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}



	
	public <T> void refresh(T object)
	{
		getSession().refresh(object);
	}

	protected Session getSession()
	{
		 return sessionFactory.getCurrentSession();
	}
	
	protected Query getQuery(String hqlQuey)
	{
		return getSession().createQuery(hqlQuey);
	}
	
	
	protected Persistable extractSingleElement(List<Persistable> list)
	{
		if (list.isEmpty())
		{
			return null;
		}
		else
		{
			return list.get(0);
		}
	}
	
	protected String buildHQL(String prefix, String query, String suffix)
	{
		StringBuffer buffer = new StringBuffer(prefix);
		if ( !(prefix.endsWith(" ") || query.startsWith(" ")))
			buffer.append(" ");
		buffer.append(query);
		if ( !(query.endsWith(" ") || suffix.startsWith(" ")))
			buffer.append(" ");
		buffer.append(suffix);
		
		return buffer.toString();
	}
	
	public <T>T loadByPrimaryKey(Class<T> clazz, long pk)
	{
//		Query q = getQuery("from "+clazz.getSimpleName()+" modelalias where modelalias.id=?");
//		q.setLong(0, pk);
//		return (T)q.uniqueResult();
		return (T)getSession().load(clazz, pk);
	}
	
	public <T>T loadByPrimaryKey(Class<T> clazz, String pk)
	{
//		Query q = getQuery("from "+clazz.getSimpleName()+" modelalias where modelalias.id=?");
//		q.setString(0, pk);
//		return (T)q.uniqueResult();
		return (T)getSession().load(clazz, pk);
	}
	
	
//	public <T>T loadObject(Class<T> entityClass, Serializable primaryKey) {
////		return (T)getSession().load(entityClass, primaryKey);
//		Query query = getQuery("from "+entityClass.getSimpleName()+" entityClass where entityClass.id=?");
//		if (primaryKey instanceof String)
//			query.setString(0, (String)primaryKey);
//		else
//			query.setLong(0, (Long)primaryKey);
//		return (T)query.uniqueResult();
//			
//	}


	




	/* (non-Javadoc)
	 * @see com.homefellas.dao.core.IDao#save(java.lang.Object)
	 */
	
	public <T> Serializable save(T model)
	{
		return getSession().save(model);
	}



	/* (non-Javadoc)
	 * @see com.homefellas.dao.core.IDao#saveOrUpdate(java.lang.Object)
	 */
	
	public <T> void saveOrUpdate(T object)
	{
		getSession().saveOrUpdate(object);
	}



	/* (non-Javadoc)
	 * @see com.homefellas.dao.core.IDao#deleteObject(java.lang.Object)
	 */
	
	public <T> void deleteObject(T object)
	{
		getSession().delete(object);
	}



	/* (non-Javadoc)
	 * @see com.homefellas.dao.core.IDao#updateObject(java.lang.Object)
	 */
	
	public <T> void updateObject(T object)
	{
		getSession().update(object);
	}



	/* (non-Javadoc)
	 * @see com.homefellas.dao.core.IDao#merge(java.lang.Object)
	 */
	
	public <T> void merge(T object)
	{
		getSession().merge(object);
	}
	

	
	public void flush() {
		getSession().flush();
		
	}

	
	public <T> List<T> loadAllObjects(Class<T> entityClass) {
		Query query = getQuery("from "+entityClass.getName());
		return query.list();
				
	}

	
	public void persist(Object object) {
		getSession().persist(object);
	}



	/* (non-Javadoc)
	 * @see com.homefellas.dao.core.IDao#saveAllObjects(java.util.Collection)
	 */
	
	public <T> void saveAllObjects(Collection<T> objects)
	{
		for(Iterator<T> it = objects.iterator(); it.hasNext();) 
		{
			getSession().save(it.next());
		}
	}



	/* (non-Javadoc)
	 * @see com.homefellas.dao.core.IDao#deleteAllObjects(java.util.Collection)
	 */
	
	public <T> void deleteAllObjects(Collection<T> objects)
	{
		for(Iterator<T> it = objects.iterator(); it.hasNext();) 
		{
			getSession().delete(it.next());
		}
	}



	
	public <T> T loadByPrimaryKey(Class<T> clazz, Serializable pk)
	{
		if (pk instanceof String)
			return loadByPrimaryKey(clazz, (String)pk);
		else
			return loadByPrimaryKey(clazz, (Long)pk);
	}
	
	

}
