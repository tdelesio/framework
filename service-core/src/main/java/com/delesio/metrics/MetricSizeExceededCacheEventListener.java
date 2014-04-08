package com.delesio.metrics;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.scheduling.TaskScheduler;

public class MetricSizeExceededCacheEventListener implements CacheEventListener, BeanFactoryPostProcessor, BeanPostProcessor {

	private long threshold=1000;
	private TaskScheduler scheduler;
	private IMetricService metricService;
	public int delayInSeconds;
	
	Log log = LogFactory.getLog(MetricSizeExceededCacheEventListener.class);
	
	private ConfigurableListableBeanFactory factory = null;
	private Map config = null;
	
	private class MetricSizeExceededTask implements Runnable {

		public void run() {
System.out.println("exceeded size, running now");			
			metricService.flushCache();
		}
		
	}
	
	public void dispose() {
		
	}

	public void notifyElementEvicted(Ehcache arg0, Element arg1) {
		
	}

	public void notifyElementExpired(Ehcache arg0, Element arg1) {
		
	}

	public void notifyElementPut(Ehcache cache, Element element)
			throws CacheException {
		
		long numberElementsInMemory = cache.getMemoryStoreSize();
		if (numberElementsInMemory>=threshold)
		{
			//do some task
//			Task
			long millisecondDelay = delayInSeconds*1000;
			long delay = System.currentTimeMillis()+millisecondDelay;
			scheduler.schedule(new MetricSizeExceededTask(), new Date(delay));
		}
	}

	public void notifyElementRemoved(Ehcache arg0, Element arg1)
			throws CacheException {
		
	}

	public void notifyElementUpdated(Ehcache arg0, Element arg1)
			throws CacheException {
		
	}

	public void notifyRemoveAll(Ehcache arg0) {
		
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public void setThreshold(long threshold) {
		this.threshold = threshold;
	}

	public void setScheduler(TaskScheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void setMetricService(IMetricService metricService) {
		this.metricService = metricService;
	}

	public void setDelayInSeconds(int delayInSeconds) {
		this.delayInSeconds = delayInSeconds;
	}

	public void setConfig(Map config) {
		this.config = config;
	}
	
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		//log.debug("before: "+bean+", "+beanName);
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.equals("metricService"))
			setMetricService((IMetricService)bean);
		
		return bean;
	}
	
//	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//		//log.debug("after: "+bean+", "+beanName);
//		// Now set the dependency
//		if (config == null) return bean;
//		Map beanConfig = (Map) config.get(beanName);
//		if (beanConfig != null) {
//			BeanInfo beanInfo = null;
//			try {
//				beanInfo = Introspector.getBeanInfo(bean.getClass());
//				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//				if (propertyDescriptors != null) {
//					for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
//						String propertyName = propertyDescriptor.getName();
//						if (beanConfig.get(propertyName) != null) {
//							String beanId = (String) beanConfig.get(propertyName);
//							if (log.isDebugEnabled()) {
//								log.debug("Found propertyDescriptor for " + beanName + ": " + propertyName);
//							}
//							try {
//								Object targetBean = factory.getBean(beanId);
//								Method setter = propertyDescriptor.getWriteMethod();
//								setter.invoke(bean, targetBean);
//							} catch (NoSuchBeanDefinitionException nsbde) {
//								log.error("Bean not found", nsbde);
//							} catch (IllegalAccessException e) {
//								log.error("IllegalAccessException", e);
//							} catch (InvocationTargetException e) {
//								log.error("InvocationTargetException", e);
//							}
//						}
//					}
//				}
//			} catch (IntrospectionException ie) {
//				log.error("IntrospectionException", ie);
//			}
//		}
//		return bean;
//	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
		this.factory = factory;
	}
 
}
