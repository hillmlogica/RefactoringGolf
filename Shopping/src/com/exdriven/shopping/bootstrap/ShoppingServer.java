package com.exdriven.shopping.bootstrap;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class ShoppingServer {
	
	private final Server server;

	public ShoppingServer(int port) {
		server = new Server(port);
        server.setHandler(new WebAppContext("Shopping/webapp", "/"));
	}

	public void start() {
        try {
			server.start();
		} catch (Exception e) {
			throw new ShoppingServerException("Could not start server", e);
		}
	}

}
