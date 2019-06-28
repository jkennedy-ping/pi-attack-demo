package com.pingidentity.pingintelligence.demo.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pingidentity.pingintelligence.demo.beans.*;

public class TestUtilities {
	
	public static Account getAccount() {
		Account a1 = new Account();
		a1.setAccountName("Justin Kennedy");
		a1.setAccountNumber("ACCT-1243");
		a1.setEmailAddress("justin@kennedy.ca");
		a1.setId("1");
		return a1;
	}

	public static List<Transaction> getTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();

		Transaction t1 = new Transaction();
		t1.setAmount("14.34");
		t1.setDate("01/01/2018");
		t1.setId("8384234");
		t1.setPayee("John");
		transactions.add(t1);

		Transaction t2 = new Transaction();
		t2.setAmount("23.31");
		t1.setDate("01/01/2018");
		t2.setId("1298934");
		t2.setPayee("Jane");
		transactions.add(t2);

		return transactions;

	}

}
