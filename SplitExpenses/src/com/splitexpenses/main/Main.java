package com.splitexpenses.main;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.splitexpenses.domain.Bill;
import com.splitexpenses.domain.Payment;
import com.splitexpenses.domain.Person;
import com.splitexpenses.service.PersonService;

/**
 * The program divides the expenses between the group of people. 
 * You can add all expenses which should be shared among the group of people and the program will calculate 
 * each person's current balance, as well as who owes what (and to whom).
 */

public class Main {
	
	private static final PersonService personService = new PersonService();

	public static void main(String[] args) {
		Person asia = new Person("Asia");	
		Person kasia = new Person("Kasia");
		Person basia = new Person("Basia");
		List<Person> allPeople = Arrays.asList(kasia, asia, basia);
		
		Bill bill1 = new Bill(kasia, new BigDecimal("500.00"), Arrays.asList(asia, kasia));
		Bill bill2 = new Bill(asia, new BigDecimal("600.00"), Arrays.asList(asia, kasia));
		Bill bill3 = new Bill(kasia, new BigDecimal("300.00"), Arrays.asList(asia, kasia, basia));
		Bill bill4 = new Bill(basia, new BigDecimal("200.00"), Arrays.asList(asia, kasia));
		
		List<Bill> allBills = Arrays.asList(bill1, bill2, bill3, bill4);	
		
		List<Payment> allPeopleWithCalculatedBalance = personService.calculateAmountsToRepay(allPeople, allBills);
		
		System.out.println(allPeopleWithCalculatedBalance.get(0).getFrom().getName() + " should pay the amount of "
				+ allPeopleWithCalculatedBalance.get(0).getAmount() + " to " 
				+ allPeopleWithCalculatedBalance.get(0).getTo().getName());	
		
		System.out.println(allPeopleWithCalculatedBalance.get(1).getFrom().getName() + " should pay the amount of "
				+ allPeopleWithCalculatedBalance.get(1).getAmount() + " to "
				+ allPeopleWithCalculatedBalance.get(1).getTo().getName());	
	}
}