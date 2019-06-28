package com.pingidentity.pingintelligence.demo.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.pingidentity.pingintelligence.demo.api.ApiServiceImpl;

public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = -959658393530276058L;
	
	private ApiServiceImpl apiService;
	
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext ctx = config.getServletContext();
    	this.apiService = new ApiServiceImpl.ApiServiceBuilder()
    			.setApiServer(ctx.getInitParameter("apiServer"))
    			.setApiPort(ctx.getInitParameter("apiPort"))
    			.setEndpointAccount(ctx.getInitParameter("endpointAccount"))
    			.setEndpointTransactions(ctx.getInitParameter("endpointTransactions"))
		.build();

    }

	public ApiServiceImpl getApiService() {
		return apiService;
	}
    
	/*
	 * first checks in the request for the attribute, then session.
	 * if attribute exists in request, save it to the session 
	 * If it doesn't exist in either, use the default provided
	 */
    protected String getAttribute(final String name, final String defaultValue, final HttpServletRequest request) {
    	String attr = request.getParameter(name);
    	HttpSession session = request.getSession();
    	if(attr != null) {
    		session.setAttribute(name, attr);
    		return attr;
    	}
    	
    	attr = (String)session.getAttribute(name);
    	if(attr != null) {
    		return attr;
    	}
    	
    	return defaultValue;
    }

	/*
	 * first checks in the request for the attribute
	 * If it doesn't exist in either, use the default provided
	 */
    protected String getParameter(final String name, final String defaultValue, final HttpServletRequest request) {
    	String attr = request.getParameter(name);
    	if(attr != null) {
    		return attr;
    	}
    	
    	return defaultValue;
    }
}
