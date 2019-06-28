package com.pingidentity.pingintelligence.demo.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
	private String id;
	private String date;
	private String payee;
	private String amount;
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDateDisplay(SimpleDateFormat format) {
		return format.format(date);
	}
	
	public String getDateDisplay() {
		return DATE_FORMAT.format(date);
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getPayee() {
		return payee;
	}
	
	public void setPayee(String payee) {
		this.payee = payee;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

}
