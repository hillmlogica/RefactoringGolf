package com.exdriven.shopping;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;

public class Order {

	private int id;
	private List<OrderLine> lines = new ArrayList<OrderLine>();
	private ReadableInstant timeOfOrder;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public ReadableInstant getTimeOfOrder() {
		return timeOfOrder;
	}
	
	public void setTimeOfOrder(ReadableInstant timeOfOrder) {
		this.timeOfOrder = timeOfOrder;
	}

	public List<OrderLine> getLines() {
		return lines;
	}

	public void setLines(List<OrderLine> lines) {
		this.lines = lines;
	}
	
	public int getPrice() {
		int total = 0;
		for (OrderLine line : lines) {
			total += line.getPrice();
		}
		return total;
	}
	
	public int getQuantity() {
		int quantity = 0;
		for (OrderLine line : lines) {
			quantity += line.getQuantity();
		}
		return quantity;
	}
	
	public String getDisplayableTimeOfOrder() {
		return DateTimeFormat.forPattern("HH:mm 'on' dd/MMM/yyyy").print(getTimeOfOrder());
	}

}
