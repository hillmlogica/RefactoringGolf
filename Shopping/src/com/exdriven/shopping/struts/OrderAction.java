package com.exdriven.shopping.struts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.exdriven.shopping.BasketEntry;
import com.exdriven.shopping.Order;
import com.exdriven.shopping.OrderLine;
import com.exdriven.shopping.Product;
import com.exdriven.shopping.XmlOrderRequest;
import com.exdriven.shopping.XmlOrderService;
import com.exdriven.shopping.XmlOrderRequest.XmlOrderLine;
import com.exdriven.shopping.bootstrap.WebApplicationContext;
import com.thoughtworks.xstream.XStream;

public class OrderAction extends BaseShoppingAction {
	private XmlOrderService orderService = WebApplicationContext.instance().orderService();

	public String execute() {
		Map<Product, Integer> productCounts = new HashMap<Product, Integer>();
		Set<BasketEntry> entries = getUser().getBasket().getEntries();
		for (BasketEntry basketEntry : entries) {
			Integer count = productCounts.get(basketEntry.getProduct());
			if (count == null) {
				count = 0;
			}
			productCounts.put(basketEntry.getProduct(), ++count);
		}
		Order order = new Order();
		List<OrderLine> orderLines = new ArrayList<OrderLine>();
		for (Map.Entry<Product, Integer> productCount : productCounts.entrySet()) {
			OrderLine orderLine = new OrderLine();
			orderLine.setProduct(productCount.getKey());
			orderLine.setQuantity(productCount.getValue());
			orderLines.add(orderLine);
			orderLine.setOrder(order);
		}
		order.setTimeOfOrder(new DateTime());
		order.setLines(orderLines);
		getUser().addOrder(order);
		getUser().getBasket().clear();
		sendOrderToXmlService(order);
		return SUCCESS;
	}

	private void sendOrderToXmlService(Order order) {
		XmlOrderRequest orderRequest = new XmlOrderRequest();
		for(OrderLine line : order.getLines()) {
			orderRequest.addLine(line.getProduct().getProductRef(), line.getQuantity());
		}
		XStream xstream = new XStream();
		xstream.alias("Order", XmlOrderRequest.class);
		xstream.alias("Line", XmlOrderLine.class);
		orderService.send(xstream.toXML(orderRequest));
	}
}
