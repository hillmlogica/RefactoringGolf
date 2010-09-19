package com.exdriven.shopping.struts;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exdriven.shopping.ShoppingSession;
import com.exdriven.shopping.User;
import com.exdriven.shopping.UserRepository;
import com.exdriven.shopping.bootstrap.WebApplicationContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseShoppingAction extends ActionSupport implements SessionAware {
	private ShoppingSession shoppingSession;
	private UserRepository userRepository = WebApplicationContext.instance().userRepository();
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	public User getUser() {
		return shoppingSession.getLoggedInUser();
	}
	
	public boolean isLoggedIn() {
		return shoppingSession.isLoggedIn();
	}

	public void setSession(Map<String, Object> strutsSession) {
		shoppingSession = ShoppingSession.get(strutsSession);
		shoppingSession.setUserRepository(userRepository);
	}
	
	protected ShoppingSession getShoppingSession() {
		return shoppingSession;
	}
}
