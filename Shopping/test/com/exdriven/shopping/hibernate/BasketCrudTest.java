package com.exdriven.shopping.hibernate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.hibernate.Session;
import org.junit.Test;

import com.exdriven.shopping.Basket;
import com.exdriven.shopping.Product;
import com.exdriven.shopping.ProductBuilder;
import com.exdriven.shopping.hibernate.SessionFactoryWrapper.SessionClosure;

public class BasketCrudTest extends CrudTestCase {

	@Test
	public void canPersistABasketWithItsAssociationToSomeProducts() {
		final Basket basket = new Basket();
		final Product product1 = new ProductBuilder().withPrice(1).build();
		final Product product2 = new ProductBuilder().withPrice(1).build();
		basket.add(product1);
		basket.add(product2);

		save(product1, product2, basket);

		inASession(new SessionClosure() {
			public void run(Session session) {
				Basket persistedBasket = (Basket) session.load(Basket.class, basket.getId());
				assertThat(persistedBasket.getEntries().size(), is(2));

				Product persistedProduct2 = (Product) session.load(Product.class, product2.getId());
				persistedBasket.add(persistedProduct2);
				persistedBasket.add(persistedProduct2);
				persistedBasket.add(persistedProduct2);
			}
		});

		inASession(new SessionClosure() {
			public void run(Session session) {
				Basket persistedBasket = (Basket) session.load(Basket.class, basket.getId());
				assertThat(persistedBasket.getEntries().size(), is(5));
			}
		});
}

}
