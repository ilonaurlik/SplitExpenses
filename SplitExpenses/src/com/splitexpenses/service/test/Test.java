package com.splitexpenses.service.test;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

import com.splitexpenses.domain.Bill;
import com.splitexpenses.domain.Payment;
import com.splitexpenses.domain.Person;
import com.splitexpenses.service.BillService;
import com.splitexpenses.service.PersonService;

class Test {
	private static final String KASIA = "Kasia";
	private static final String ASIA = "Asia";
	private static final String BASIA = "Basia";
	private static final PersonService personService = new PersonService();
	private static final BillService billService = new BillService();
	
	@org.junit.jupiter.api.Test
	public void testIsConsumerEqualToBillsPayer1() {
		Person basia = new Person(BASIA);	
		Person asia = new Person(ASIA);
		Bill bill1 = new Bill(asia, new BigDecimal("500.00"), Arrays.asList(basia, asia));
		
		Assert.assertTrue(billService.isConsumerEqualToBillsPayer(bill1, asia));
	}
	
	@org.junit.jupiter.api.Test
	public void testIsConsumerEqualToBillsPayer2() {
		Person basia = new Person(BASIA);	
		Person asia = new Person(ASIA);
		Bill bill1 = new Bill(asia, new BigDecimal("500.00"), Arrays.asList(basia, asia));
		
		Assert.assertFalse(billService.isConsumerEqualToBillsPayer(bill1, basia));
	}
	
	@org.junit.jupiter.api.Test
	public void testIsPayerOneOfConsumers1() {
		Person basia = new Person(BASIA);	
		Person asia = new Person(ASIA);
		Bill bill1 = new Bill(asia, new BigDecimal("500.00"), Arrays.asList(basia, asia));
		
		Assert.assertTrue(billService.isPersonOneOfConsumers(bill1, asia));
	}
	
	@org.junit.jupiter.api.Test
	public void testIsPayerOneOfConsumers2() {
		Person basia = new Person(BASIA);	
		Person asia = new Person(ASIA);
		Person kasia = new Person(KASIA);
		Bill bill1 = new Bill(asia, new BigDecimal("500.00"), Arrays.asList(basia, asia));
		
		Assert.assertFalse(billService.isPersonOneOfConsumers(bill1, kasia));
	}
	
	public void testIsPersonEqualToPayer1() {
		Person basia = new Person(BASIA);	
		Person asia = new Person(ASIA);
		Bill bill1 = new Bill(asia, new BigDecimal("500.00"), Arrays.asList(basia, asia));
		
		Assert.assertTrue(billService.isPersonEqualToPayer(bill1, asia));
	}
	
	@org.junit.jupiter.api.Test
	public void testIsPersonEqualToPayer2() {
		Person basia = new Person(BASIA);	
		Person asia = new Person(ASIA);
		Bill bill1 = new Bill(asia, new BigDecimal("500.00"), Arrays.asList(basia, asia));
		
		Assert.assertFalse(billService.isPersonEqualToPayer(bill1, basia));
	}
	
	@org.junit.jupiter.api.Test
	public void testCalculateAmountOwedPerPerson1() {
		Person basia = new Person(BASIA);	
		Person asia = new Person(ASIA);
		Bill bill1 = new Bill(asia, new BigDecimal("500.00"), Arrays.asList(basia, asia));
		List<Bill> allBills = Arrays.asList(bill1);	
		
		Assert.assertEquals(new BigDecimal("-250.00"), billService.calculateAmountOwedPerPerson(basia, allBills));
	}
	
	@org.junit.jupiter.api.Test
	public void testCalculateAmountOwedPerPerson2() {
		Person basia = new Person(BASIA);	
		Person asia = new Person(ASIA);
		Bill bill1 = new Bill(asia, new BigDecimal("500.00"), Arrays.asList(basia, asia));
		List<Bill> allBills = Arrays.asList(bill1);	
		
		Assert.assertEquals(new BigDecimal("250.00"), billService.calculateAmountOwedPerPerson(asia, allBills));
	}
		
	@org.junit.jupiter.api.Test
	public void testCalculateAmountOwedPerPerson3() {
		Person basia = new Person(BASIA);	
		Person asia = new Person(ASIA);
		Person kasia = new Person(KASIA);
		Bill bill1 = new Bill(kasia, new BigDecimal("500.00"), Arrays.asList(basia, asia));
		List<Bill> allBills = Arrays.asList(bill1);	
		
		Assert.assertEquals(new BigDecimal("500.00"), billService.calculateAmountOwedPerPerson(kasia, allBills));
	}
	
	@org.junit.jupiter.api.Test
	public void testCalculateAmountOwedPerPerson4() {
		Person basia = new Person(BASIA);	
		Person asia = new Person(ASIA);
		Person kasia = new Person(KASIA);
		Bill bill1 = new Bill(asia, new BigDecimal("500.00"), Arrays.asList(basia, asia));
		List<Bill> allBills = Arrays.asList(bill1);	
		
		Assert.assertEquals(BigDecimal.ZERO, billService.calculateAmountOwedPerPerson(kasia, allBills));
	}
	
	@org.junit.jupiter.api.Test
	public void testCalculateAmountOwedPerPerson5() {
		Person basia = new Person(BASIA);	
		Person asia = new Person(ASIA);
		Person kasia = new Person(KASIA);
		Bill bill1 = new Bill(kasia, new BigDecimal("400.00"), Arrays.asList(basia, asia, kasia));
		List<Bill> allBills = Arrays.asList(bill1);	
		
		Assert.assertEquals(new BigDecimal("266.66"), billService.calculateAmountOwedPerPerson(kasia, allBills));
	}
	
	@org.junit.jupiter.api.Test
	public void testCalculateAmountOwedPerPerson6() {
		Person basia = new Person(BASIA);	
		Person asia = new Person(ASIA);
		Person kasia = new Person(KASIA);
		Bill bill1 = new Bill(kasia, new BigDecimal("400.00"), Arrays.asList(basia, asia, kasia));
		List<Bill> allBills = Arrays.asList(bill1);	
		
		Assert.assertEquals(new BigDecimal("-133.33"), billService.calculateAmountOwedPerPerson(basia, allBills));
	}
	
	@org.junit.jupiter.api.Test
	public void testCalculateAmountsToRepay() {
		Person basia = new Person(BASIA);	
		Person asia = new Person(ASIA);
		Person kasia = new Person(KASIA);
		List<Person> allPeople = Arrays.asList(asia, basia, kasia);
		
		Bill bill1 = new Bill(asia, new BigDecimal("500.00"), Arrays.asList(basia, asia));
		Bill bill2 = new Bill(basia, new BigDecimal("600.00"), Arrays.asList(basia, asia));
		Bill bill3 = new Bill(asia, new BigDecimal("400.00"), Arrays.asList(basia, asia, kasia));
		Bill bill4 = new Bill(kasia, new BigDecimal("200.00"), Arrays.asList(basia, asia));	
		List<Bill> allBills = Arrays.asList(bill1, bill2, bill3, bill4);
		
		Payment payment1 = new Payment(basia, asia, new BigDecimal("116.66"));
		Payment payment2 = new Payment(basia, kasia, new BigDecimal("66.67"));
		List<Payment> allPeopleWithCalculatedBalance = Arrays.asList(payment1, payment2);
		
		List<Payment> result = personService.calculateAmountsToRepay(allPeople, allBills);
		Assert.assertEquals(result, allPeopleWithCalculatedBalance);
	}
	
	@org.junit.jupiter.api.Test
	public void testCalculateAmountsToRepay2() {
		Person kasia = new Person(KASIA);
		List<Person> allPeople = Arrays.asList(kasia);
		Bill bill1 = new Bill(kasia, new BigDecimal("500.00"), Arrays.asList(kasia));
		List<Bill> allBills = Arrays.asList(bill1);
		Payment payment1 = new Payment(kasia, kasia, BigDecimal.ZERO);
		List<Payment> allPeopleWithCalculatedBalance = Arrays.asList(payment1);
		
		List<Payment> result = personService.calculateAmountsToRepay(allPeople, allBills);
		Assert.assertEquals(result, allPeopleWithCalculatedBalance);
	}
	
}
