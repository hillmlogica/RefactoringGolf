package com.exdriven.shopping.hibernate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;

import org.hibernate.Session;
import org.junit.Test;

import com.exdriven.shopping.Order;
import com.exdriven.shopping.OrderBuilder;
import com.exdriven.shopping.Product;
import com.exdriven.shopping.ProductBuilder;
import com.exdriven.shopping.User;
import com.exdriven.shopping.UserBuilder;
import com.exdriven.shopping.hibernate.SessionFactoryWrapper.SessionClosure;

public class OrderCrudTest extends CrudTestCase {
	
	@Test
	public void canPersistAnOrderWithItsAssociationToAUser() {
		final User user = new UserBuilder().build();
		final Product product1 = new ProductBuilder().build();
		final Product product2 = new ProductBuilder().build();
		final Order order = new OrderBuilder()
				.forUser(user)
				.withLine(product1, 3)
				.withLine(product2, 5)
				.build();

		save(order, user);

		inASession(new SessionClosure() {
			public void run(Session session) {
				User persistedUser = (User) session.load(User.class, user.getId());
				Order persistedOrder = (Order) session.load(Order.class, order.getId());
				assertThat(persistedOrder.getTimeOfOrder(), is(order.getTimeOfOrder()));
				assertThat(persistedUser.getOrders(), hasItem(persistedOrder));
				assertThat(persistedOrder.getLines().size(), is(2));
				assertThat(persistedUser.getOrders().size(), is(1));
			}
		});
	}

}
