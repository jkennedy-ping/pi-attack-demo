package com.pingidentity.pingintelligence.demo.listeners;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.pingidentity.pingintelligence.demo.Constants;

import kong.unirest.JacksonObjectMapper;
import kong.unirest.Unirest;

public class AppContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		Unirest.config().setObjectMapper(new JacksonObjectMapper());
		Unirest.config().verifySsl(false);
		
	    final ExecutorService threadPool = Executors.newFixedThreadPool(2); // starts thread pool
	    servletContextEvent.getServletContext().setAttribute(Constants.ATTR_THREAD_POOL, threadPool);		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	    final ExecutorService threadPool = (ExecutorService) servletContextEvent.getServletContext().getAttribute(Constants.ATTR_THREAD_POOL);
	    threadPool.shutdown();
	}
}
