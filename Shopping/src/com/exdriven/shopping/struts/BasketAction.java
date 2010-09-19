package com.exdriven.shopping.struts;

import com.exdriven.shopping.Product;
import com.exdriven.shopping.ProductRepository;
import com.exdriven.shopping.bootstrap.WebApplicationContext;

public class BasketAction extends BaseShoppingAction {
	private ProductRepository productRepository = WebApplicationContext.instance().productRepository();
	private int productId;
	private int quantity;

	public String doAdd() {
		Product product = productRepository.loadProduct(productId);
		if (product == null) {
			log.error("Couldn't find product with id: " + productId);
			throw new IllegalStateException("Couldn't find product with id: " + productId);
		}
		for (int i = 0; i < quantity; i++) {
			getShoppingSession().getBasket().add(product);
		}
		return SUCCESS;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
