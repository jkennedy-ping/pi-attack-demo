package com.pingidentity.pingintelligence.demo.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.pingidentity.pingintelligence.demo.Constants;
import com.pingidentity.pingintelligence.demo.beans.AttackResult;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Servlet implementation class StolenToken
 */
public class DataExfiltration extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private Set<AttackResult> attackResults = Collections.synchronizedSet(new LinkedHashSet<AttackResult>());
	
	// used to determine when attack thread is completed
	Future<Boolean> futureTask = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DataExfiltration() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String headerName = request.getHeader("x-requested-with");
		if(headerName != null) {
			String json = null;
			if(this.futureTask.isDone() && this.attackResults.size() == 0) {
				json = new Gson().toJson("complete");
			} else {
			
				// remove each element from attackresults and put into a separate list to send to the client
				List<String> results = new ArrayList<String>();
				Iterator<AttackResult> itr = this.attackResults.iterator();
				while(itr.hasNext()){
				    AttackResult attackResult = itr.next();
					results.add(attackResult.toString());
					itr.remove();
				}
				json = new Gson().toJson(results);
			}
			
			System.out.println("json is: " + json);
			
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(json);
		    return;
		}		
		
		request.setAttribute("server", this.getAttribute("server", this.getApiService().getServer(), request));
		request.setAttribute("port", this.getAttribute("port", this.getApiService().getPort(), request));
		request.setAttribute("url", this.getAttribute("url", this.getApiService().getEndpointAccount(), request));
		request.setAttribute("clientIp", this.getAttribute("clientIp", request.getServletContext().getInitParameter("clientIp"), request));
		request.setAttribute("token", this.getAttribute("token", "", request));

		String jsp = "/dataexfiltration.jsp";
		request.getServletContext().getRequestDispatcher(jsp).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String server = request.getParameter("server");
		String port = request.getParameter("port");
		String url = request.getParameter("url");
		String ip = request.getParameter("clientIp");
		
		String token = request.getParameter("token");
		Integer iterations = Integer.parseInt(request.getParameter("iterations"));
		Integer delay = Integer.parseInt(request.getParameter("delay"));

		StringBuffer endpoint = new StringBuffer();
		endpoint.append("https://").append(server);

		if (port != null && port.length() > 0) {
			endpoint.append(":").append(port);
		}

		if (url.charAt(0) == '/') {
			endpoint.append(url);
		} else {
			endpoint.append('/').append(url);
		}

		System.out.println("Using endpoint: " + endpoint);

		String[] dataUsers = request.getParameterValues("dataUsers");
		
		int usersLen = dataUsers.length;

		// hit the endpoint for the defined iteration
		System.out.println("hitting endpoint " + iterations + " times, with a " + delay + " second delay");

		Callable<Boolean> callable = new Callable<Boolean>() {
		    public Boolean call() {
				for (Integer i = 0; i < iterations; i++) {
					// grab a random user 
					int userIndex = ThreadLocalRandom.current().nextInt(usersLen);
					String randomUser = dataUsers[userIndex];
		
					Unirest.config().verifySsl(false);
		
					HttpResponse<String> result = Unirest.get(endpoint.toString() + "/" + randomUser)
														.header("Accept", "application/json")
														.header("Authorization", "Bearer " + token)
														.header("x-forwarded-for", ip)
														.asString();
					
					AttackResult attackResult = new AttackResult();
					attackResult.setIteration(i);
					attackResult.setStatus(result.getStatus());
					attackResult.setDescription(result.getStatusText());
					attackResult.setSuccess(result.getStatus() == 200);
					attackResults.add(attackResult);
					
					System.out.println("AttackResult: " + attackResult.toString());
					
		
					if (delay != null && !delay.equals(0)) {
						try {
							Thread.sleep(delay);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				return true;
		    }
		};
		
		final ServletContext servletContext = request.getServletContext();
		final ExecutorService threadPool = (ExecutorService) servletContext.getAttribute(Constants.ATTR_THREAD_POOL);
		this.futureTask = threadPool.submit(callable);
		
	}
}
