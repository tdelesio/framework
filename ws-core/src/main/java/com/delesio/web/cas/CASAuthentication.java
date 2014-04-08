package com.delesio.web.cas;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

public class CASAuthentication {

private String id;
	
	@JsonIgnore
	private Map<String, Object> attributes;
	 
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public Map<String, Object> getAttributes()
	{
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes)
	{
		this.attributes = attributes;
	}
}
