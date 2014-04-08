package com.delesio.web.ws;

import org.codehaus.jackson.annotate.JsonIgnore;

public class WebServiceSystemException
{
	private long occurance;
	private String message;
	private String exceptionType;
	private Exception exception;
	
	public WebServiceSystemException(Exception exception)
	{
		this.exception = exception;
		this.message = exception.getMessage();
		this.exceptionType = exception.toString();
		this.occurance = System.currentTimeMillis(); 
		
	}
	
	public WebServiceSystemException(String message, Exception exception)
	{
		this.exception = exception;
		this.message = message;
		this.exceptionType = exception.toString();
		this.occurance = System.currentTimeMillis(); 		
	}
	
	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public long getOccurance()
	{
		return occurance;
	}

	public void setOccurance(long occurance)
	{
		this.occurance = occurance;
	}

	public String getExceptionType()
	{
		return exceptionType;
	}

	public void setExceptionType(String exceptionType)
	{
		this.exceptionType = exceptionType;
	}

	@JsonIgnore
	public Exception getException()
	{
		return exception;
	}
	
	

}
