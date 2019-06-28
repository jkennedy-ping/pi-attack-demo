package com.pingidentity.pingintelligence.demo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pingidentity.pingintelligence.demo.JwtHelper;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.util.ArrayList;
import java.util.List;


/**
 * Servlet implementation class StolenToken
 */
public class DecoyApi extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DecoyApi() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setAttribute("server", this.getAttribute("server", this.getApiService().getServer(), request));
		request.setAttribute("port", this.getAttribute("port", this.getApiService().getPort(), request));
		request.setAttribute("endpointDecoy", this.getAttribute("endpointDecoy", request.getServletContext().getInitParameter("endpointDecoy"), request));
		request.setAttribute("clientIp", this.getAttribute("clientIp", request.getServletContext().getInitParameter("clientIp"), request));
		request.setAttribute("token", this.getAttribute("token", "", request));

		String jsp = "/decoyapi.jsp";
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
		String url = request.getParameter("endpointDecoy");
		String ip = request.getParameter("clientIp");
		String token = request.getParameter("token");

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

		// hit the endpoint for the defined iteration
		System.out.println("hitting decoy endpoint: " + endpoint.toString());

		List<String> results = new ArrayList<String>();

			Unirest.config().verifySsl(false);

			HttpResponse<String> result = Unirest.get(endpoint.toString())
												.header("Accept", "application/json")
												.header("Authorization", "Bearer " + token)
												.header("x-forwarded-for", ip)
												.asString();

			if (result.getStatus() != 200) {
				results.add("Failed : HTTP status : " + result.getStatus() + " - " + result.getStatusText());
			} else {
				results.add("Success : HTTP status code : " + result.getStatus());
			}


		request.setAttribute("results", results);

		doGet(request, response);
	}

}
