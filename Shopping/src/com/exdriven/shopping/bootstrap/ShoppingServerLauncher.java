package com.exdriven.shopping.bootstrap;

import com.exdriven.shopping.Product;
import com.exdriven.shopping.hibernate.SessionFactoryWrapper;

public class ShoppingServerLauncher {
	
	public static void main(String... args) {
		new ShoppingServer(8080).start();
		createSomeProducts();
	}

	private static void createSomeProducts() {
		SessionFactoryWrapper hibernateHelper = new SessionFactoryWrapper(WebApplicationContext.instance().sessionFactory());
		hibernateHelper.save(
				product("Objects First with Java", 3442), 
				product("The Damned Utd", 799),
				product("The Spirit Level", 2000),
				product("The Book Thief", 799),
				product("Head First Java", 4495),
				product("The Secret Scripture", 799)
		);
	}

	private static Product product(String name, int price) {
        Product product = new Product();
        product.setPrice(price);
        product.setName(name);
		return product;
	}

}
