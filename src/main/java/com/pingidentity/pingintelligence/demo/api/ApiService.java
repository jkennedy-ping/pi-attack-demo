package com.pingidentity.pingintelligence.demo.api;

import java.util.List;

import com.pingidentity.pingintelligence.demo.beans.Account;
import com.pingidentity.pingintelligence.demo.beans.Transaction;

public interface ApiService {

	public List<Transaction> getTransactions();
	
	public Account getAccount(String id);
	
	public String getServer();
	public String getPort();
	public String getUriTransactions();
	public String getUriAccount();
	public String getEndpointTransactions();
	public String getEndpointAccount();

}
