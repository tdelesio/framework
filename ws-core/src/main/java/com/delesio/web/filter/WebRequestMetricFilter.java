package com.delesio.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMethod;

import com.delesio.metrics.IMetricService;
import com.delesio.metrics.WebRequestMetric;
import com.google.common.io.CharStreams;

public class WebRequestMetricFilter implements Filter {


	@Autowired
	private IMetricService metricService;
	private boolean captureWebMetrics=false;
	
	
	
	public void setCaptureWebMetrics(boolean captureWebMetrics) {
		this.captureWebMetrics = captureWebMetrics;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		if (!captureWebMetrics)
		{
			chain.doFilter(req,resp);
			return;
		}
		else
		{
			WebRequestMetric metric = new WebRequestMetric();
			String ip = request.getRemoteAddr();
			
			String operation = request.getMethod();
			if (operation.equals(RequestMethod.POST))
			{
				String requestPayload = CharStreams.toString(request.getReader());
				metric.setRequestPayload(requestPayload);
			}
			
			String tgt = request.getParameter("tgt");
			if (tgt!=null && tgt.startsWith("TGT-"))
			{
				String email=null;
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication != null)
					email = (String)authentication.getPrincipal();
				
				metric.setEmail(email);
			}
			String requestURI = request.getRequestURI();
			
			metric.setOperation(operation);
			metric.setIpAddress(ip);		
			
			metric.setRequestURI(requestURI);
			metric.setSystemTime(new DateTime().getMillis());
			metric.setTgt(tgt);
			
			HttpServletResponseWithGetStatus rsp = new HttpServletResponseWithGetStatus(response);
			try {
	            chain.doFilter(request, rsp);
	            rsp.flushBuffer();
	        } finally {
	            byte[] copy = rsp.getCopy();
	            String responsePayload = new String(copy, response.getCharacterEncoding()); 
	            int status = rsp.getStatus();
	            
	            metric.setStatus(status);
	    		metric.setResponsePayload(responsePayload);
	    		
	    		metricService.saveWebRequestMetric(metric);
	        }
		}
			
	}
	

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	private  class HttpServletResponseWithGetStatus extends HttpServletResponseWrapper {
	    private int status = 200; // default status
	    private ServletOutputStream outputStream;
	    private PrintWriter writer;
	    private ServletOutputStreamCopier copier;

	    public HttpServletResponseWithGetStatus(HttpServletResponse resp) {
		    super(resp);
	    }

	    public void setStatus(int sc) {
		this.status = sc;
		super.setStatus(sc);
	    }

	    public void sendError(int sc) throws IOException {
		this.status = sc;
		super.sendError(sc);
	    }

	    public void sendError(int sc, String msg) throws IOException {
		this.status = sc;
		super.sendError(sc, msg);
	    }

	    public void sendRedirect(String location) throws IOException {
		this.status = 302;
		super.sendRedirect(location);
	    }

	    public int getStatus() {
		return this.status;
	    }
	    
	    @Override
	    public ServletOutputStream getOutputStream() throws IOException {
	        if (writer != null) {
	            throw new IllegalStateException("getWriter() has already been called on this response.");
	        }

	        if (outputStream == null) {
	            outputStream = getResponse().getOutputStream();
	            copier = new ServletOutputStreamCopier(outputStream);
	        }

	        return copier;
	    }

	    @Override
	    public PrintWriter getWriter() throws IOException {
	        if (outputStream != null) {
	            throw new IllegalStateException("getOutputStream() has already been called on this response.");
	        }

	        if (writer == null) {
	            copier = new ServletOutputStreamCopier(getResponse().getOutputStream());
	            writer = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()), true);
	        }

	        return writer;
	    }
	    
	    @Override
	    public void flushBuffer() throws IOException {
	        if (writer != null) {
	            writer.flush();
	        } else if (outputStream != null) {
	            copier.flush();
	        }
	    }

	    public byte[] getCopy() {
	        if (copier != null) {
	            return copier.getCopy();
	        } else {
	            return new byte[0];
	        }
	    }
	}
	
	private class ServletOutputStreamCopier extends ServletOutputStream {

	    private OutputStream outputStream;
	    private ByteArrayOutputStream copy;

	    public ServletOutputStreamCopier(OutputStream outputStream) {
	        this.outputStream = outputStream;
	        this.copy = new ByteArrayOutputStream(1024);
	    }

	    @Override
	    public void write(int b) throws IOException {
	        outputStream.write(b);
	        copy.write(b);
	    }

	    public byte[] getCopy() {
	        return copy.toByteArray();
	    }

	}

}
