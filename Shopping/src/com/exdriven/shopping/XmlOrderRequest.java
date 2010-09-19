package com.exdriven.shopping;

import java.util.ArrayList;
import java.util.List;

public class XmlOrderRequest {
	public List<XmlOrderLine> orderLines = new ArrayList<XmlOrderLine>();
	
	public void addLine(String productRef, int quantity) {
		XmlOrderLine orderLine = new XmlOrderLine();
		orderLine.setProduct(productRef);
		orderLine.setQuantity(quantity);
		this.orderLines.add(orderLine);
	}
	
	public List<XmlOrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<XmlOrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	public static class XmlOrderLine {
		private String product;
		private int quantity;
		
		public String getProduct() {
			return product;
		}
		
		public void setProduct(String product) {
			this.product = product;
		}
		
		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
	}
}
