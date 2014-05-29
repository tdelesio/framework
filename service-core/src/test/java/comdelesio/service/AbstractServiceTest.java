package comdelesio.service;

import java.io.IOException;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.delesio.AbstractTest;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:com/homefellas/context/unittest-context.xml"})
public abstract class AbstractServiceTest extends AbstractTest {
//AbstractTransactionalJUnit4SpringContextTests {

//	protected IDao dao;
//	
//	//@Resource(name="&cacheManager")	 
//	protected Ehcache cache;
//	
//	protected PasswordEncoder passwordEncoder;
//	
//	//services
////	protected LoginService loginService = new LoginService();
//	protected AbstractMetricService metricService = new AbstractMetricService();

	//daos
//	protected ILoginDao loginDao;
//	protected IMetricDao metricDao;
	
//	@BeforeTransaction
//	public void init()
//	{
		//setup dao
//		dao = createMock(IDao.class);
//		cache = createMock(Ehcache.class);
//		metricDao = createMock(IMetricDao.class);
//		passwordEncoder = createMock(PasswordEncoder.class);
//		
//		metricService.setDao(dao);
//		metricService.setCache(cache);
//		metricService.setMetricDao(metricDao);
//	}
	
	protected Ehcache createCacheInstance(String cacheName, int maxElementsInMemory, boolean eternal, boolean overflowToDisk, int timeToLive,  MemoryStoreEvictionPolicy memoryStoreEvictionPolicy)
	{
		CacheManager cacheManager = CacheManager.create();
		
		EhCacheFactoryBean factory = new EhCacheFactoryBean();
		factory.setCacheManager(cacheManager);
		factory.setCacheName(cacheName);
		factory.setMaxElementsInMemory(maxElementsInMemory);
		factory.setEternal(eternal);
		factory.setOverflowToDisk(overflowToDisk);
		factory.setTimeToLive(timeToLive);
		factory.setMemoryStoreEvictionPolicy(memoryStoreEvictionPolicy);
		try
		{
			factory.afterPropertiesSet();
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
		
		return factory.getObject();

	}
}
