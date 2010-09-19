package com.exdriven.shopping;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

public class OrderBuilder {
	private ReadableInstant timeOfOrder = new DateTime();
	private User user = new UserBuilder().build();
	private List<OrderLine> orderLines = new ArrayList<OrderLine>();

	public Order build() {
		Order order = new Order();
		order.setTimeOfOrder(timeOfOrder);
		user.addOrder(order);
		order.setLines(orderLines);
		return order;
	}

	public OrderBuilder forUser(User user) {
		this.user = user;
		return this;
	}
	
	public OrderBuilder withLine(Product product, int quantity) {
		orderLines.add(makeLine(product, quantity));
		return this;
	}
	
	public OrderBuilder withTime(ReadableInstant time) {
		this.timeOfOrder = time;
		return this;
	}
	
	private OrderLine makeLine(Product product, int quantity) {
		OrderLine orderLine = new OrderLine();
		orderLine.setProduct(product);
		orderLine.setQuantity(quantity);
		return orderLine;
	}

}
