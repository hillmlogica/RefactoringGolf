package com.exdriven.shopping.struts;

import com.exdriven.shopping.Product;
import com.exdriven.shopping.ProductRepository;
import com.exdriven.shopping.bootstrap.WebApplicationContext;

public class ProductAction extends BaseShoppingAction {
	private ProductRepository productRepository = WebApplicationContext.instance().productRepository();
	private String name;
	private int price;
	
	public String execute() {
		Product product = new Product();
		product.setName(name);
		product.setPrice(price);
		productRepository.save(product);
		return SUCCESS;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
