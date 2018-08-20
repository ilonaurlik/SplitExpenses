package com.splitexpenses.domain;

import java.math.BigDecimal;

public class Payment {
	private Person from;
	private Person to;
	BigDecimal amount;
	
	public Payment(Person from, Person to, BigDecimal amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}
	
	public Person getFrom() {
		return from;
	}

	public Person getTo() {
		return to;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "Payment [from=" + from + ", to=" + to + ", amount=" + amount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}	
}
