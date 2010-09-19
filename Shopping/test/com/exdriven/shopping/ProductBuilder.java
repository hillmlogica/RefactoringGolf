package com.exdriven.shopping;

import org.apache.commons.lang.math.RandomUtils;

public class ProductBuilder {
	
	private String name = BuilderUtil.random("product");
	private int price = RandomUtils.nextInt(100000);
	private String ref = BuilderUtil.random("productRef");

	public Product build() {
		Product product = new Product();
		product.setName(name);
		product.setPrice(price);
		product.setProductRef(ref);
		return product;
	}
	
	public ProductBuilder withPrice(int price) {
		this.price = price;
		return this;
	}
	
	public ProductBuilder withName(String name) {
		this.name = name;
		return this;
	}
	
	public ProductBuilder withProductRef(String ref) {
		this.ref = ref;
		return this;
	}

}
