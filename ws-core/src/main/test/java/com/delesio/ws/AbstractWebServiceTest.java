package com.delesio.ws;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

import com.delesio.model.AbstractModel;
import com.delesio.rule.AbstractEmbeddedJettyServer;
import com.delesio.web.ws.AbstractWebService;
import com.delesio.web.ws.WsHelper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public abstract class AbstractWebServiceTest {

	private static String testName;
	private static Map<String, List<WebServiceCall>> singleMethodTest = new HashMap<String, List<WebServiceCall>>();
	private List<WebServiceCall> wsCalls;
	private WebServiceCall wsCall;
	
	@Rule 
	public TestName name = new TestName();
		
	
	
	protected abstract void createDatabaseDefaults();
	protected abstract void initializeDataSource();
	protected abstract AbstractEmbeddedJettyServer getServer();
//	protected abstract String getContextRoot();
	
	@Before
	public void setupDatasource()
	{
		initializeDataSource();
		createDatabaseDefaults();
		
		wsCalls = new ArrayList<WebServiceCall>();
		testName = this.getClass().getSimpleName();
	}

	
	@After
	public void recordTest()
	{
		singleMethodTest.put(name.getMethodName(), wsCalls);
	}
	
//	@AfterClass
//	public static void afterClass()
//	{
//		Template template = null;
//		VelocityContext context = new VelocityContext();
//		StringWriter sw = new StringWriter();
//		try
//		{
//			Velocity.setProperty("resource.loader", "class");
//			Velocity.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
//			Velocity.init();
//			
//			
//			context.put("map", singleMethodTest);
//		   template = Velocity.getTemplate("com/homefellas/docs/templates/wsTestTemplate.vm");
//		   
//		   template.merge( context, sw );
//		   
//		   FileWriter fstream = new FileWriter("target/enunciate/build/docs/"+testName+".html");
//		   BufferedWriter out = new BufferedWriter(fstream);
//		   out.write(sw.toString());
//		   out.close();
//		}
//		catch( Exception e )
//		{
//			e.printStackTrace();
//		}		
//
//	}
	
	protected String getJerseyMapping()
	{
		return "/rest";
	}
	
	
	
		
	
	
	private void recordTest(Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, Object inputObject, URI action)
	{
		wsCall = new WebServiceCall();
		wsCall.setHttpMethod(WsHelper.getHTTPOperationForMethod(webServiceClass, webServiceMethodName, (inputObject==null) ? null:new Class[]{inputObject.getClass()}, null));
		wsCall.setService(webServiceClass);
		wsCall.setMethodName(webServiceMethodName);
		wsCall.setAction(action);
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			mapper.writeValue(new File("c:\\user.json"), inputObject);
			wsCall.setJsonInput(mapper.writeValueAsString(inputObject));
		}
		catch (JsonGenerationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JsonMappingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		wsCall.jsonInput = (inputObject==null) ? null:new Gson().toJson(inputObject);
		
		wsCalls.add(wsCall);
		
	}
	
	protected Map<String, String> buildPathParms(String key, String value)
	{
	
		Map<String, String> pathParms = new HashMap<String, String>();
		pathParms.put(key, value);
		return pathParms;
	}
	
	protected Map<String, String> buildPathParms(String key, long value)
	{
	
		return buildPathParms(key, String.valueOf(value));
	}
	
	private WebResource resource(String path) {
		return createClient().resource(getServer().uri()).path(path);
	}
	
	protected void assertOKStatus(Response response)
	{
		Assert.assertEquals(Response.Status.OK, response.getStatus());
	}
	
	protected void assertOKStatus(ClientResponse response)
	{
		Assert.assertEquals(ClientResponse.Status.OK.getStatusCode(), response.getStatus());
	}
	
	protected <T>T callWebService(Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, GenericType<T> returnClass, AbstractModel inputObject)
	{
		HTTPMethod method = WsHelper.getHTTPOperationForMethod(webServiceClass, webServiceMethodName, (inputObject==null) ? null:new Class[]{inputObject.getClass()}, null);
		if (method.equals(HTTPMethod.PUT))
			return putToWebService(webServiceClass, webServiceMethodName, returnClass, inputObject);
		else if (method.equals(HTTPMethod.POST))
			return postToWebService(webServiceClass, webServiceMethodName, returnClass, inputObject);
		else
			throw new RuntimeException("Undefined method:"+method+" in callWebService");
	}
	
	protected <T>T callWebService(Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, Class<T> returnClass, Object inputObject)
	{
		HTTPMethod method = WsHelper.getHTTPOperationForMethod(webServiceClass, webServiceMethodName, (inputObject==null) ? null:new Class[]{inputObject.getClass()}, null);
		if (method.equals(HTTPMethod.PUT))
			return putToWebService(webServiceClass, webServiceMethodName, returnClass, inputObject);
		else if (method.equals(HTTPMethod.POST))
			return postToWebService(webServiceClass, webServiceMethodName, returnClass, inputObject);
		else
			throw new RuntimeException("Undefined method:"+method+" in callWebService");
	}
	protected <T>T callWebService(Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, GenericType<T> returnClass, Map<String, String> pathParms)
	{
		return callWebService(webServiceClass, webServiceMethodName, returnClass, pathParms, null);
	}
	
	
	protected <T>T callWebService(Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, GenericType<T> returnClass, Map<String, String> pathParms, AbstractModel inputObject)
	{
		Client client = createClient();
		return callWebService(client, webServiceClass, webServiceMethodName, returnClass, pathParms, inputObject);
	}
	protected <T>T callWebService(Client client, Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, GenericType<T> returnClass, Map<String, String> pathParms, AbstractModel inputObject)
	{
		HTTPMethod method = WsHelper.getHTTPOperationForMethod(webServiceClass, webServiceMethodName, (inputObject==null) ? null:new Class[]{inputObject.getClass()}, pathParms);
		if (method.equals(HTTPMethod.PUT))
			return putToWebService(webServiceClass, webServiceMethodName, returnClass, inputObject);
		else if (method.equals(HTTPMethod.POST))
			return postToWebService(webServiceClass, webServiceMethodName, pathParms, returnClass, inputObject);
		else if (method.equals(HTTPMethod.GET))
			return getToWebService(client, webServiceClass, webServiceMethodName, pathParms, returnClass);
		else
			throw new RuntimeException("Undefined method:"+method+" in callWebService");
	}
	
	protected <T>T callWebService(Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, Class<T> returnClass, Map<String, String> pathParms)
	{
		return callWebService(webServiceClass, webServiceMethodName, returnClass, pathParms, null);
	}
	
	
	protected <T>T callWebService(Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, Class<T> returnClass, Map<String, String> pathParms, Object inputObject)
	{
		if (pathParms!=null&&!pathParms.isEmpty())
		{
			Set<String> keys = pathParms.keySet();
			for (String key:keys)
			{
				if (!key.startsWith("{") || !key.endsWith("}"))
					Assert.fail("PathParm keys must start with a { and end with }"); 
//					RuntimeException();
			}
		}
		HTTPMethod method = WsHelper.getHTTPOperationForMethod(webServiceClass, webServiceMethodName, (inputObject==null) ? null:new Class[]{inputObject.getClass()}, pathParms);
		if (method.equals(HTTPMethod.PUT))
			return putToWebService(webServiceClass, webServiceMethodName, returnClass, inputObject);
		else if (method.equals(HTTPMethod.POST))
			return postToWebService(webServiceClass, webServiceMethodName, pathParms, returnClass, inputObject);
		else if (method.equals(HTTPMethod.GET))
			return getToWebService(webServiceClass, webServiceMethodName, pathParms, returnClass);
		else
			throw new RuntimeException("Undefined method:"+method+" in callWebService");
	}
	
	//POST
		private <T>T postToWebService(Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, Class<T> returnClass, Object inputObject)
		{
			URI uri = buildURI(webServiceClass, webServiceMethodName, inputObject, null);
			return createClient().resource(getServer().uri()).path(uri.getPath()).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(returnClass, inputObject);
		}
		
		private <T>T postToWebService(Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, GenericType<T> returnClass, Object inputObject)
		{
			URI uri = buildURI(webServiceClass, webServiceMethodName, inputObject, null);
			return createClient().resource(getServer().uri()).path(uri.getPath()).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(returnClass, inputObject);
		}
		
		private <T>T postToWebService(Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, Map<String, String> pathParms, Class<T> returnClass, Object inputObject)
		{
			URI uri = buildURI(webServiceClass, webServiceMethodName, inputObject, pathParms);
			return createClient().resource(getServer().uri()).path(uri.getPath()).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(returnClass, inputObject);
		}
		
		private <T>T postToWebService(Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, Map<String, String> pathParms, GenericType<T> returnClass, Object inputObject)
		{
			URI uri = buildURI(webServiceClass, webServiceMethodName, inputObject, pathParms);
			return createClient().resource(getServer().uri()).path(uri.getPath()).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(returnClass, inputObject);
		}
		
		
		//GET
		private <T>T getToWebService(Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, Map<String, String> pathParms, Class<T> returnClass)
		{
			return getToWebService(createClient(), webServiceClass, webServiceMethodName, pathParms, returnClass);
		}
		private <T>T getToWebService(Client client, Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, Map<String, String> pathParms, Class<T> returnClass)
		{
			URI uri = buildURI(webServiceClass, webServiceMethodName, null, pathParms);
			return client.resource(getServer().uri()).path(uri.getPath()).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(returnClass);
		}
		
		private <T>T getToWebService(Client client, Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, Map<String, String> pathParms, GenericType<T> returnClass)
		{
			URI uri = buildURI(webServiceClass, webServiceMethodName, null, pathParms);
			return client.resource(getServer().uri()).path(uri.getPath()).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(returnClass);
		}
		
		
		//PUT
		private <T>T putToWebService(Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, Class<T> returnClass, Object inputObject)
		{
			URI uri = buildURI(webServiceClass, webServiceMethodName, inputObject, null);
			return createClient().resource(getServer().uri()).path(uri.getPath()).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).put(returnClass, inputObject);
		}
		
		private <T>T putToWebService(Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, GenericType<T> returnClass, Object inputObject)
		{
			URI uri = buildURI(webServiceClass, webServiceMethodName, inputObject, null);
			return createClient().resource(getServer().uri()).path(uri.getPath()).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).put(returnClass, inputObject);
		}
		
		private URI buildURI(Class<? extends AbstractWebService> webServiceClass, String webServiceMethodName, Object inputClass, Map<String, String> pathParms)
		{
			String wsPath;
			wsPath = getPathForMethod(webServiceClass, webServiceMethodName, (inputClass==null) ? null:new Class[]{inputClass.getClass()}, pathParms);
			
			
			URI uri = UriBuilder.fromPath(getServer().getContextRoot()+getJerseyMapping()+wsPath).build();
			
//			recordTest(webServiceClass, webServiceMethodName, inputClass, uri);
			return uri;
		}
		
		public String getPathForMethod(Class clazz, String methodName, Class[] arguements)
		{
			return getPathForMethod(clazz, methodName, arguements, null);
		}
		
		
		public String getPathForMethod(Class clazz, String methodName, Class[] arguements, Map<String, String> pathParams)
		{

			Method method=null;
			StringBuffer returnPath = new StringBuffer();
			
			
			try
			{
//				java.lang.annotation.Annotation clazzAnnotation = 
				
				Path clazzPath = (Path)clazz.getAnnotation(Path.class);
				returnPath.append(clazzPath.value());
				
				method = WsHelper.getMethod(clazz, methodName, arguements, pathParams);
				Path path = method.getAnnotation(Path.class);
				
				String pathValue = path.value(); 
				while (pathParams!=null && pathValue.contains("{") && pathValue.contains("}"))
				{
					int startIndex = pathValue.indexOf("{");
					int endIndex = pathValue.indexOf("}");
					String pathParm = pathValue.substring(startIndex, endIndex+1);
					String value = pathParams.get(pathParm);
					if (value == null)
					{
						Assert.fail(pathParm+" was not found in the pathParams map");
					}
					pathValue = pathValue.replace(pathParm, value);
				}
					
				returnPath.append(pathValue);
				return returnPath.toString();
			}
			catch (SecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	
		protected Client createClient()
		{
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
					Boolean.TRUE);
			return Client.create(clientConfig);
		}

		private WebResource resource(String href, Map<String, ?> params)
		{
			URI uri = UriBuilder.fromPath(href).buildFromMap(params);
			
			
			return resource(uri.getPath());
		}
	
}
