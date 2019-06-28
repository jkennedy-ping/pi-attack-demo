package com.pingidentity.pingintelligence.demo.beans;

import java.util.Comparator;

public class AttackResult implements Comparator<AttackResult> {
	public static final Comparator<AttackResult> COMPARATOR = Comparator.comparing(AttackResult::getIteration);	
	
	private int status;
	private String description;
	private int iteration;
	private boolean success;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getIteration() {
		return iteration;
	}
	public void setIteration(int iteration) {
		this.iteration = iteration;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	@Override
	public String toString() {
		return "Iteration: " + this.getIteration() + " : HTTP Status: " + this.getStatus() + " " + this.getDescription();
	}
	
	@Override
	public int compare(AttackResult o1, AttackResult o2) {
		return COMPARATOR.compare(o1, o2);
	}
	
}
