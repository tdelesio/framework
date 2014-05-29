package com.delesio.dao;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Ehcache;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.delesio.AbstractTest;


public abstract class AbstractHibernateDaoTest extends AbstractTest {

	@Resource(name="&mySessionFactory")	 
	protected LocalSessionFactoryBean factory;
	
		
//	@Resource(name="metricCache")
//	protected Ehcache metricCache;
	
	
	
//	@Rule
//	public CassandraUnit cassandraUnit = new CassandraUnit(new ClassPathXmlDataSet("extendedDataSet.xml"));
//	 
//	@Test
//    public void shouldHaveLoadAnExtendDataSet() throws Exception {
//        //here, a Cassandra server is started and your data from extendedDataSet.xml has been loaded
//        // you can query or do what you want
//        Assert.assertNotNull(cassandraUnit.keyspace);
//    }
	
	@Override
	protected void initializeDataSource()
	{
		logger.info("**RECREATION SCHEMA**"); 
		factory.dropDatabaseSchema();
		factory.createDatabaseSchema();
	}
	
	
	public void assertInCache(Ehcache cacheName, Object valueUnderAssert)
	{
		@SuppressWarnings("rawtypes")
		Map cacheMap = cacheName.getAllWithLoader(cacheName.getKeys(), null);
		Assert.assertTrue(cacheMap.containsValue(valueUnderAssert));
	}
	
	public void assertNotInCache(Ehcache cacheName, Object valueUnderAssert)
	{
		@SuppressWarnings("rawtypes")
		Map cacheMap = cacheName.getAllWithLoader(cacheName.getKeys(), null);
		Assert.assertFalse(cacheMap.containsValue(valueUnderAssert));
	}
	
//	protected void assertRowCount(int expectedRows, AbstractModel modelInstance)
//	{
//		Assert.assertEquals(expectedRows, countRowsInTable(modelInstance.getTableName()));
//	}
//	
//	protected void assertRowCount(int expectedRows, AbstractModel modelInstance, String joinTableFieldName)
//	{
//		String joinTable = modelInstance.getJoinTableName(joinTableFieldName);
//		Assert.assertEquals(expectedRows, countRowsInTable(joinTable));
//	}
}
