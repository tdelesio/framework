package com.delesio.web.ws;

import java.lang.reflect.Method;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import com.delesio.ws.HTTPMethod;

public class WsHelper {

	public static Method getMethod(Class clazz, String methodName, Class[] arguements, Map<String, String> pathParams)
	{
		if (pathParams!=null)
		{
			if (arguements==null)
			{
				arguements = new Class[pathParams.size()];
				for (int i=0;i<pathParams.size();i++)
				{
					arguements[i] = String.class;
				}
			}
			else
			{
				Class[] oldArguements = arguements.clone();
				arguements = new Class[pathParams.size()+arguements.length];
				for (int i=0;i<arguements.length;i++)
				{
					if (i<oldArguements.length)
						arguements[i] = oldArguements[i];
					else
						arguements[i] = String.class;
					
				}
			}
		}
		
		Method method=null;
		try
		{
			method = clazz.getMethod(methodName, arguements);
		}
		catch (NoSuchMethodException e)
		{
			for (Method m:clazz.getMethods())
			{
				if (m.getName().equals(methodName))
					method = m;
			}
			
		}
		
		return method;
	}
	
	public static HTTPMethod getHTTPOperationForMethod(Class clazz, String methodName, Class[] arguements, Map<String, String> pathParams)
	{
		Method method = getMethod(clazz, methodName, arguements, pathParams);
		if (method.getAnnotation(PUT.class)!=null)
			return HTTPMethod.PUT;
		else if (method.getAnnotation(GET.class)!=null)
			return HTTPMethod.GET;
		else if (method.getAnnotation(POST.class)!=null)
			return HTTPMethod.POST;
		else if (method.getAnnotation(DELETE.class)!=null)
			return HTTPMethod.DELETE;
		else
			return HTTPMethod.UNDEFINED;
			
			
	}
}
