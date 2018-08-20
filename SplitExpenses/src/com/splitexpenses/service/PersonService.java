package com.splitexpenses.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.splitexpenses.domain.Bill;
import com.splitexpenses.domain.Payment;
import com.splitexpenses.domain.Person;
import com.splitexpenses.service.BillService;

public class PersonService {
	private static final BillService billService = new BillService();
	
	public List<Person> calculateBalanceForEveryPerson(List<Person> allPeople, List<Bill> allBills) {
		List<Person> allPeopleWithCalculatedBalance = new ArrayList<>();
		
		for(Person person : allPeople) {
			List<Bill> personBills = billService.getBillsFor(person, allBills);
			Person personWithBalance = calculateBalance(person, personBills);
			allPeopleWithCalculatedBalance.add(personWithBalance);
		}
		
		return allPeopleWithCalculatedBalance;
	}

	public Person calculateBalance(Person person, List<Bill> personBills) {
		person.setBalance(billService.calculateAmountOwedPerPerson(person, personBills));
		
		return person;
	}

	public List<Person> sortByBalanceDescending(List<Person> allPeople) {
		Collections.sort(allPeople, Comparator.comparing(Person::getBalance).reversed());
		
		return allPeople;
	}
	
	public List<Person> sortByBalanceAscending(List<Person> allPeople) {
		Collections.sort(allPeople, Comparator.comparing(Person::getBalance));
		
		return allPeople;
	}
	
	public List<Person> findPeopleWithPositiveBalance(List<Person> allPeople) {
		List<Person> peopleWithPositiveBalance = new ArrayList<>();
		for(Person person: allPeople) {
			if (person.getBalance().compareTo(BigDecimal.ZERO) > 0) {
				peopleWithPositiveBalance.add(person);
			}
		}
		
		return peopleWithPositiveBalance;
	}
	
	public List<Person> findPeopleWithNegativeBalance(List<Person> allPeople) {
		List<Person> peopleWithNegativeBalance = new ArrayList<>();
		for(Person person: allPeople) {
			if (person.getBalance().compareTo(BigDecimal.ZERO) < 0) {
				peopleWithNegativeBalance.add(person);
			}
		}
		
		return peopleWithNegativeBalance;
	}
	
	public List<Person> findPeopleWithZeroBalance(List<Person> allPeople) {
		List<Person> peopleWithZeroBalance = new ArrayList<>();
		for(Person person: allPeople) {
			if (person.getBalance().compareTo(BigDecimal.ZERO) == 0) {
				peopleWithZeroBalance.add(person);
			}
		}
		
		return peopleWithZeroBalance;
	}
	
	public List<Payment> calculateAmountsToRepay(List<Person> allPeople, List<Bill> allBills) {
		List<Payment> allPeopleWithAmountsToPay = new ArrayList<>();
		List<Person> allPeopleWithCalculatedBalance = calculateBalanceForEveryPerson(allPeople, allBills);
		List<Person> peopleWithPositiveBalance = sortByBalanceDescending(findPeopleWithPositiveBalance(allPeopleWithCalculatedBalance));		
		List<Person> peopleWithNegativeBalance = sortByBalanceAscending(findPeopleWithNegativeBalance(allPeopleWithCalculatedBalance));
		List<Person> peopleWithZeroBalance = findPeopleWithZeroBalance(allPeopleWithCalculatedBalance);
		
		Payment payment;
		
		for(Person person: peopleWithPositiveBalance) {
			for(Person secondPerson: peopleWithNegativeBalance) {
				if (person.getBalance().compareTo(secondPerson.getBalance().abs()) < 0) {
					BigDecimal amountToPay = person.getBalance();
					payment = new Payment(secondPerson, person, amountToPay);
					person.setBalance(BigDecimal.ZERO);
					secondPerson.setBalance(secondPerson.getBalance().subtract(amountToPay));
				} else if (person.getBalance().compareTo(secondPerson.getBalance().abs()) > 0) {
					payment = new Payment(secondPerson, person, secondPerson.getBalance().abs());
					person.setBalance(person.getBalance().subtract(secondPerson.getBalance()));
					secondPerson.setBalance(BigDecimal.ZERO);
				} else {
					payment = new Payment(secondPerson, person, person.getBalance());
					person.setBalance(BigDecimal.ZERO);
					secondPerson.setBalance(BigDecimal.ZERO);
				}

				allPeopleWithAmountsToPay.add(payment);
			}
		}
		
		for(Person person: peopleWithZeroBalance) {
			payment = new Payment(person, person, BigDecimal.ZERO);
			allPeopleWithAmountsToPay.add(payment);
		}
		
		return allPeopleWithAmountsToPay;
	}
}