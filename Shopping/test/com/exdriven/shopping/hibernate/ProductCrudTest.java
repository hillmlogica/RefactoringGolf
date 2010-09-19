package com.exdriven.shopping.hibernate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.hibernate.Session;
import org.junit.Test;

import com.exdriven.shopping.Product;
import com.exdriven.shopping.ProductBuilder;
import com.exdriven.shopping.hibernate.SessionFactoryWrapper.SessionClosure;

public class ProductCrudTest extends CrudTestCase {
	
	@Test
	public void canPersistAProduct() {
		final Product product = new ProductBuilder().build();

		save(product);

		inASession(new SessionClosure() {
			public void run(Session session) {
				Product persistedProduct = (Product) session.load(Product.class, product.getId());
				assertThat(persistedProduct.getName(), is(product.getName()));
				assertThat(persistedProduct.getPrice(), is(product.getPrice()));
				assertThat(persistedProduct.getProductRef(), is(product.getProductRef()));
			}
		});
	}
}
