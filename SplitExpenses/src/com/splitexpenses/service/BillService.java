package com.splitexpenses.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.splitexpenses.domain.Bill;
import com.splitexpenses.domain.Person;

public class BillService {	
	public boolean isConsumerEqualToBillsPayer(Bill bill, Person person) {
		return (bill.getConsumers().contains(person) && bill.getPayer().equals(person));
	}
	
	public boolean isPersonOneOfConsumers(Bill bill, Person person) {
		return bill.getConsumers().contains(person);
	}
	
	public boolean isPersonEqualToPayer(Bill bill, Person person) {
		return bill.getPayer().equals(person);
	}

	public BigDecimal calculateAmountOwedPerPerson(Person person, List<Bill> personBills) {
		BigDecimal owedAmount = BigDecimal.ZERO;
		
		for(Bill b: personBills) {
			BigDecimal numberOfConsumers = BigDecimal.valueOf(b.getConsumers().size());
			BigDecimal amountProPerson = b.getExpenses().divide(numberOfConsumers, 2, RoundingMode.HALF_UP);
			
			if (isConsumerEqualToBillsPayer(b, person)) {
				int numberOfConsumersWhoShouldRepay = b.getConsumers().size() - 1;
				owedAmount = owedAmount.add(amountProPerson.multiply(BigDecimal.valueOf(numberOfConsumersWhoShouldRepay)));
			} else if (!isPersonOneOfConsumers(b, person) && isPersonEqualToPayer(b, person))  {
				owedAmount = owedAmount.add(b.getExpenses());
			} else if (isPersonOneOfConsumers(b, person) && !isPersonEqualToPayer(b, person))  {
				owedAmount = owedAmount.subtract(amountProPerson);
			} else {
				owedAmount = BigDecimal.ZERO;
			}
		}
		return owedAmount;
	}

	public List<Bill> getBillsFor(Person person, List<Bill> allBills) {
		List<Bill> personBills = new ArrayList<>();
		for(Bill bill : allBills) {
			if (bill.getConsumers().contains(person) || bill.getPayer().equals(person)) {
				personBills.add(bill);
			}
		}
		return personBills;
	}
}