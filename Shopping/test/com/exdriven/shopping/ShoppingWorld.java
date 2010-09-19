package com.exdriven.shopping;

import com.exdriven.shopping.bootstrap.ShoppingServer;
import com.exdriven.shopping.bootstrap.WebApplicationContext;
import com.exdriven.shopping.hibernate.SessionFactoryWrapper;


public class ShoppingWorld {
	private static final int PORT = 9012;
	private static ShoppingWorld shoppingWorld;
	private SessionFactoryWrapper sessionFactoryWrapper = new SessionFactoryWrapper(WebApplicationContext.instance().sessionFactory());
	private final StubOrderService stubOrderService;

	public ShoppingWorld(StubOrderService stubOrderService) {
		this.stubOrderService = stubOrderService;
	}

	public static ShoppingWorld theWorld() {
		if (shoppingWorld == null) {
			shoppingWorld = createWorld();
		}
		return shoppingWorld;
	}

	private static ShoppingWorld createWorld() {
		StubOrderService stubOrderService = new StubOrderService();
		WebApplicationContext.instance().setOrderService(stubOrderService);
		ShoppingServer server = new ShoppingServer(PORT);
		server.start();
		return new ShoppingWorld(stubOrderService);
	}

	public Shopper aShopper() {
		return new HttpClientShopper(PORT);
	}

	public void assuming(Object... objects) {
		sessionFactoryWrapper.save(objects);
	}

	public StubOrderService stubOrderService() {
		return stubOrderService;
	}
}