package com.exdriven.shopping.struts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.exdriven.shopping.Basket;
import com.exdriven.shopping.Order;
import com.exdriven.shopping.Product;
import com.exdriven.shopping.ProductRepository;
import com.exdriven.shopping.bootstrap.WebApplicationContext;

public class HomeAction extends BaseShoppingAction {
	private ProductRepository productRepository = WebApplicationContext.instance().productRepository();

	@Override
	public String execute() {
		return SUCCESS;
	}
	
	public Collection<Product> getProducts() {
		return productRepository.allProducts();
	}
	
	public Basket getBasket() {
		return getShoppingSession().getBasket();
	}
	
	public Collection<Order> getOrders() {
		List<Order> orders = new ArrayList<Order>(getUser().getOrders());
		Collections.sort(orders, new Comparator<Order>() {
			public int compare(Order order1, Order order2) {
				return order1.getTimeOfOrder().compareTo(order2.getTimeOfOrder());
			}
		});
		return orders;
	}
}
