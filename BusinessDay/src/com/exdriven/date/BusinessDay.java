package com.exdriven.date;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class BusinessDay {

	private final LocalDate date;

	public BusinessDay(int year, int month, int day) {
		this(new LocalDate(year, month, day));
	}

	private BusinessDay(LocalDate date) {
		this.date = date;
		if(isWeekendDay(date)) {
			throw new NotAValidBusinessDayException(date + " is not a weekday");
		}
	}

	public BusinessDay previous() {
		return new BusinessDay(previousFridayIfWeekend(date.minusDays(1)));
	}	

	public BusinessDay next() {
		return new BusinessDay(nextMondayIfWeekend(date.plusDays(1)));
	}

	private LocalDate previousFridayIfWeekend(LocalDate date) {
		while(isWeekendDay(date)) {
			date = date.minusDays(1);
		}
		return date;
	}

	private LocalDate nextMondayIfWeekend(LocalDate date) {
		while(isWeekendDay(date)) {
			date = date.plusDays(1);
		}
		return date;
	}

	private boolean isWeekendDay(LocalDate date) {
		int dayOfWeek = date.getDayOfWeek();
		if (dayOfWeek == DateTimeConstants.SATURDAY
			|| dayOfWeek == DateTimeConstants.SUNDAY) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object other) {
		BusinessDay otherBusinessDay = (BusinessDay) other;
		return this.date.equals(otherBusinessDay.date);
	}

	@Override
	public int hashCode() {
		return this.date.hashCode();
	}
}
