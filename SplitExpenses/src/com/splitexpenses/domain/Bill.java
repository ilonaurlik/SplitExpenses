package com.splitexpenses.domain;

import java.math.BigDecimal;
import java.util.List;

public class Bill {	
	private Person payer;
	private BigDecimal expenses;
	private List<Person> consumers;
	 
	public Bill(Person payer, BigDecimal expenses, List<Person> consumers) {
		this.payer = payer;
		this.expenses = expenses;
		this.consumers = consumers;
	}

	public Person getPayer() {
		return payer;
	}

	public BigDecimal getExpenses() {
		return expenses;
	}

	public List<Person> getConsumers() {
		return consumers;
	}
}