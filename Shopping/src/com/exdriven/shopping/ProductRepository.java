package com.exdriven.shopping;

import java.util.Collection;

public interface ProductRepository {

	Collection<Product> allProducts();

	Product loadProduct(int productId);

	void save(Product product);

}
