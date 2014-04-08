package com.delesio.model;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import com.delesio.metrics.ClientMetric;
import com.delesio.metrics.WebRequestMetric;
import com.google.gson.Gson;

public abstract class TestModelBuilder {

	protected static PodamFactory factory = new PodamFactoryImpl();

	public static WebRequestMetric webRequestMetric(String email, String requestURI)
	{
		WebRequestMetric webRequestMetric = new WebRequestMetric();
		webRequestMetric.setEmail(email);
		webRequestMetric.setIpAddress("127.0.0.1");
		webRequestMetric.setOperation("POST");
		webRequestMetric.setRequestPayload(factory.getStrategy().getStringOfLength(2345));
		webRequestMetric.setRequestURI(requestURI);
		webRequestMetric.setStatus(200);
		webRequestMetric.setTgt(factory.getStrategy().getStringOfLength(50));
		
		return webRequestMetric;
	}
	
	protected static void newObjectCheck(AbstractModel baseTO, boolean flag)
	{
		if (baseTO instanceof AbstractSequenceModel)
		{
			AbstractSequenceModel model = (AbstractSequenceModel)baseTO;
			if (flag)
				model.setId(new Long(0));
			else if (model.getId()<0) 
				model.setId(-model.getId());
		}
		else if (baseTO instanceof AbstractGUIDModel)
		{
			AbstractGUIDModel model = (AbstractGUIDModel)baseTO;
			model.generateGUIDKey();				
		}
	}

	
	
	
	public static String convertObjectToJSON(Object object)
	{
		Gson gson = new Gson();
		return gson.toJson(object);
	}
	

	public static ClientMetric clientMetric(boolean newClientMetric)
	{
//		ClientMetric clientMetric = factory.manufacturePojo(ClientMetric.class);
		ClientMetric clientMetric = new ClientMetric();
		clientMetric.setAction(factory.getStrategy().getStringValue());
		clientMetric.setCategory(factory.getStrategy().getStringValue());
		clientMetric.setLabel(factory.getStrategy().getStringValue());
		clientMetric.setMemberId(factory.getStrategy().getStringValue());
		clientMetric.setValue(factory.getStrategy().getInteger());
		newObjectCheck(clientMetric, newClientMetric);
		return clientMetric;
		
	}
	
	public static String randomString()
	{
		return factory.getStrategy().getStringValue();
	}
}
