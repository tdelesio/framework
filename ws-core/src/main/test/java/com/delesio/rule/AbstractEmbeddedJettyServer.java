package com.delesio.rule;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.junit.rules.ExternalResource;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.log.StdErrLog;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public abstract class AbstractEmbeddedJettyServer extends ExternalResource
{

	private Server server;
	private int port = 8888;
	
	public abstract String getContextRoot();	
	
	public Object getSpringBean(String beanName) {
//        WebAppContext jettyWebAppContext = (WebAppContext) server.getHandler();
        
        HandlerCollection handlerCollection = (HandlerCollection)server.getHandler();
        WebAppContext jettyWebAppContext = (WebAppContext)handlerCollection.getChildHandlerByClass(WebAppContext.class);
        
        ServletContext servletContext = jettyWebAppContext.getServletHandler().getServletContext();
        
        WebApplicationContext springWebAppContext =
            WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        return springWebAppContext.getBean(beanName);
    }
	
//	public void setSessionValue(String email)
//	{
//		HandlerCollection handlerCollection = (HandlerCollection)server.getHandler();
//        WebAppContext jettyWebAppContext = (WebAppContext)handlerCollection.getChildHandlerByClass(WebAppContext.class);
//        
//        ServletContext servletContext = jettyWebAppContext.getServletHandler().getServletContext();
//        
//        servletContext.setAttribute(AbstractCasFilter.CONST_CAS_ASSERTION, email);
//	}
	
	@Override
	protected void before() throws Throwable
	{
		server = new Server(port);
		server.addHandler(new WebAppContext("src/test/webapp", getContextRoot()));

		final Context context = new Context(server, "/servlets", Context.SESSIONS);
		context.addServlet(new ServletHolder(new Servlet() {
			
			@Override
			public void service(ServletRequest req, ServletResponse resp)
					throws ServletException, IOException
			{
				HttpServletRequest request = (HttpServletRequest)req;
				
				String email = request.getParameter("email");
		    	HttpSession session = request.getSession();
		    	
		    	session.setAttribute(AbstractCasFilter.CONST_CAS_ASSERTION, email);
			}
			
			@Override
			public void init(ServletConfig arg0) throws ServletException
			{
				
			}
			
			@Override
			public String getServletInfo()
			{
				return null;
			}
			
			@Override
			public ServletConfig getServletConfig()
			{
				return null;
			}
			
			@Override
			public void destroy()
			{
				
			}
		}), "/session");
		 
		Properties p = new Properties();
	
		try
		{
		server.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void after()
	{
		try
		{
			server.stop();
		}
		catch (Throwable t)
		{
		}
	}

	public String uri()
	{
		return "http://localhost:" + port;
	}
}
