package com.pingidentity.pingintelligence.demo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pingidentity.pingintelligence.demo.JwtHelper;
import com.pingidentity.pingintelligence.demo.beans.Account;
import com.pingidentity.pingintelligence.demo.beans.Transaction;
import com.pingidentity.pingintelligence.demo.test.TestUtilities;

/**
 * Servlet implementation class UserAccount
 */
public class UserAccount extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Account account = null;
		
		String username = request.getParameter("username"); 
		if(username != null) {
			// coming here from login form so lookup the account and persist it to the session
			// use token to call the transaction api
			account = this.getApiService().getAccount(username);
			if(account == null) {
				response.sendRedirect(request.getContextPath() + "?status=fail");
				return;
			}
			request.getSession().setAttribute("account", account);
		}
		
		if(account == null) {
			// user hit the url directly, so must already have a session
			account = (Account)request.getSession().getAttribute("account");
			if(account == null) {
				response.sendRedirect(request.getContextPath());
				return;
			}
		}
		
		// save the clientIp in the session, to be shared as a default amongst hacker pages
		request.getSession().setAttribute("clientIp", request.getServletContext().getInitParameter("clientIp"));
		
		List<Transaction> transactions = this.getApiService().getTransactions();
		//Account account = TestUtilities.getAccount();
		//List<Transaction> transactions = TestUtilities.getTransactions();
		
		request.setAttribute("transactions", transactions);
		request.setAttribute("username", account.getAccountName());
		
		String token = JwtHelper.createJWT("1", "ping", account.getAccountName(), 0);
		request.setAttribute("token", token);
		request.getSession().setAttribute("token", token);
		
        String jsp = "/useraccount.jsp";
        request.getServletContext().getRequestDispatcher(jsp).forward(request, response);		
	}

}
