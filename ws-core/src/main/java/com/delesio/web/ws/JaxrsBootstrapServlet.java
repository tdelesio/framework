package com.delesio.web.ws;

import javax.servlet.http.HttpServlet;

import com.wordnik.swagger.jaxrs.JaxrsApiReader;

public class JaxrsBootstrapServlet extends HttpServlet
{
	static {
	    JaxrsApiReader.setFormatString("");
	  }
}
