package com.exdriven.shopping;

import java.util.Map;

public class ShoppingSession {
	private final static String SESSION_KEY = "ShoppingSession";
	private String loggedInUsername;
	private UserRepository userRepository;
	
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static ShoppingSession get(Map<String, Object> strutsSession) {
		if (!strutsSession.containsKey(SESSION_KEY)) {
			strutsSession.put(SESSION_KEY, new ShoppingSession());
		}
		return (ShoppingSession) strutsSession.get(SESSION_KEY);
	}

	public User getLoggedInUser() {
		return userRepository.findUserByUsername(loggedInUsername);
	}
	
	public void setLoggedInUser(User user) {
		this.loggedInUsername = user.getUsername();
	}
	
	public boolean isLoggedIn() {
		return loggedInUsername != null;
	}
	
	public void logout() {
		loggedInUsername = null;
	}

	public Basket getBasket() {
		return getLoggedInUser().getBasket();
	}
}
