package com.pingidentity.pingintelligence.demo.api;

import java.util.Arrays;
import java.util.List;

import com.pingidentity.pingintelligence.demo.beans.Account;
import com.pingidentity.pingintelligence.demo.beans.Transaction;

import kong.unirest.JacksonObjectMapper;
import kong.unirest.Unirest;

public class ApiServiceImpl implements ApiService {

	private String apiServer;
	private String apiPort;
	private String endpointTransactions;
	private String endpointAccount;
	private String uriTransactions;
	private String uriAccount;
	
	public ApiServiceImpl(ApiServiceBuilder builder) {
		this.apiServer = builder.apiServer;
		this.apiPort = builder.apiPort;
		this.endpointTransactions = builder.endpointTransactions;
		this.endpointAccount = builder.endpointAccount;
		
        this.uriTransactions = "https://" 
        		+ this.apiServer + ":" + this.apiPort
        		+ this.endpointTransactions;
        
        System.out.println("jek transactions uri is : " + this.uriTransactions);
        
        this.uriAccount = "https://" 
        		+ this.apiServer + ":" + this.apiPort
        		+ this.endpointAccount;

        System.out.println("jek accounts uri is : " + this.uriAccount);
        
        //Unirest.config().verifySsl(false);  
        //Unirest.config().setObjectMapper(new JacksonObjectMapper());
	}
	
	//curl -k https://18.130.219.81:8065/v3/transactions -vv
	@Override
	public List<Transaction> getTransactions() {
		Transaction[] transactions = Unirest.get(this.uriTransactions)
                .asObject(Transaction[].class)
                .getBody();
		
		if(transactions != null && transactions.length > 0) {
			return Arrays.asList(transactions);
		}
		
		return null;
	}
	
	
	//curl -k https://18.130.219.81:8065/v3/accounts/1691062715099 -vv
	@Override
	public Account getAccount(String id) {
		Account account = Unirest.get(this.uriAccount + "/" + id)
                .asObject(Account.class)
                .getBody();
		
		return account;
	}
	
	@Override
	public String getServer() {
		return this.apiServer;
	}

	@Override
	public String getPort() {
		return this.apiPort;
	}

	@Override
	public String getUriTransactions() {
		return this.uriTransactions;
	}

	@Override
	public String getUriAccount() {
		return this.uriAccount;
	}

	@Override
	public String getEndpointTransactions() {
		return this.endpointTransactions;
	}

	@Override
	public String getEndpointAccount() {
		return this.endpointAccount;
	}

	public static class ApiServiceBuilder {
		private String apiServer;
		private String apiPort;
		private String endpointTransactions;
		private String endpointAccount;
		
		public ApiServiceBuilder setApiServer(String apiServer) {
			this.apiServer = apiServer;
			return this;
		}

		public ApiServiceBuilder setApiPort(String apiPort) {
			this.apiPort = apiPort;
			return this;
		}

		public ApiServiceBuilder setEndpointTransactions(String endpointTransactions) {
			this.endpointTransactions = endpointTransactions;
			return this;
		}

		public ApiServiceBuilder setEndpointAccount(String endpointAccount) {
			this.endpointAccount = endpointAccount;
			return this;
		}

		public ApiServiceImpl build(){
			return new ApiServiceImpl(this);
		}		
	}

}
