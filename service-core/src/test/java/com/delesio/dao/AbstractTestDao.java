package com.delesio.dao;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Ehcache;

import org.cassandraunit.CassandraUnit;
import org.cassandraunit.dataset.xml.ClassPathXmlDataSet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.delesio.AbstractTest;
import com.delesio.dao.IDao;
import com.delesio.model.AbstractModel;


public abstract class AbstractTestDao extends AbstractTest {

	@Resource(name="&mySessionFactory")	 
	protected LocalSessionFactoryBean factory;
	
	@Autowired
	private Boolean printSql=false;
	
	@Resource(name="dao")
	protected IDao dao;
		
	@Resource(name="metricCache")
	protected Ehcache metricCache;
	
	protected abstract void createDatabaseDefaults();
	
//	@Rule
//	public CassandraUnit cassandraUnit = new CassandraUnit(new ClassPathXmlDataSet("extendedDataSet.xml"));
//	 
//	@Test
//    public void shouldHaveLoadAnExtendDataSet() throws Exception {
//        //here, a Cassandra server is started and your data from extendedDataSet.xml has been loaded
//        // you can query or do what you want
//        Assert.assertNotNull(cassandraUnit.keyspace);
//    }
	
	protected void assertTableRowChecks()
	{
		dao.flush();
	}
	
	@Before
	public void recreateSchema()
	{
		logger.info("**RECREATION SCHEMA**"); 
		factory.dropDatabaseSchema();
		factory.createDatabaseSchema();
		
		createDatabaseDefaults();
		
		if (printSql)
			logger.info("*************BEGIN***************");
	}
	
	@Before
	public void printBeginMarker()
	{
		
	}
	
	@After
	public void printAfterMarker()
	{
		if (printSql)
			logger.info("***********END*****************");
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
	
	protected void assertRowCount(int expectedRows, AbstractModel modelInstance)
	{
		Assert.assertEquals(expectedRows, countRowsInTable(modelInstance.getTableName()));
	}
	
	protected void assertRowCount(int expectedRows, AbstractModel modelInstance, String joinTableFieldName)
	{
		String joinTable = modelInstance.getJoinTableName(joinTableFieldName);
		Assert.assertEquals(expectedRows, countRowsInTable(joinTable));
	}
}
