package com.exdriven.date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class BusinessDayTest {

	@Test
	public void twoBusinessDaysForTheSameYearAndMonthAndDayAreEqual() {
		assertThat(new BusinessDay(2009, 3, 4), is(equalTo(new BusinessDay(2009, 3, 4))));
	}
	
	@Test
	public void twoBusinessDaysForTheSameYearAndMonthAndDayHaveEqualHashcodes() {
		assertThat(new BusinessDay(2009, 3, 4).hashCode(), is(equalTo(new BusinessDay(2009, 3, 4).hashCode())));
	}
	
	@Test
	public void twoBusinessDaysForTheSameMonthAndDayButDifferentYearsAreNotEqual() {
		assertThat(new BusinessDay(2009, 3, 4), is(not(equalTo(new BusinessDay(2008, 3, 4)))));
	}

	@Test
	public void twoBusinessDaysForTheSameYearAndDayButDifferentMonthsAreNotEqual() {
		assertThat(new BusinessDay(2009, 3, 4), is(not(equalTo(new BusinessDay(2009, 2, 4)))));
	}
	
	@Test
	public void twoBusinessDaysForTheSameYearAndMonthButDifferentDaysAreNotEqual() {
		assertThat(new BusinessDay(2009, 3, 4), is(not(equalTo(new BusinessDay(2009, 3, 5)))));
	}

	@Test (expected = NotAValidBusinessDayException.class)
	public void blowsUpWhenTryingToCreateABusinessDayForASaturday() {
		new BusinessDay(2009, 1, 31);
	}

	@Test (expected = NotAValidBusinessDayException.class)
	public void blowsUpWhenTryingToCreateABusinessDayForASunday() {
		new BusinessDay(2009, 2, 1);
	}
	
	@Test
	public void theNextBusinessDayAfterMondayIsTuesday() throws Exception {
		assertThat(new BusinessDay(2009, 2, 2).next(), is(new BusinessDay(2009, 2, 3)));
	}

	@Test
	public void theNextBusinessDayAfterFridayIsTheSubsequentMonday() throws Exception {
		assertThat(new BusinessDay(2009, 2, 6).next(), is(new BusinessDay(2009, 2, 9)));
	}

	@Test
	public void thePreviousBusinessDayBeforeFridayIsThePreviousThursday() throws Exception {
		assertThat(new BusinessDay(2009, 2, 6).previous(), is(new BusinessDay(2009, 2, 5)));
	}
}
