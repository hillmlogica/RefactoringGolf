package com.exdriven.shopping.struts;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exdriven.shopping.User;
import com.exdriven.shopping.UserRepository;
import com.exdriven.shopping.bootstrap.WebApplicationContext;

public class LoginAction extends BaseShoppingAction {
	private String username;
	private String password;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private UserRepository userRepository = WebApplicationContext.instance().userRepository();

	@Override
	public String execute() {
		if (StringUtils.isBlank(username)) {
			addActionError("Please enter a username");
		}
		if (StringUtils.isBlank(password)) {
			addActionError("Please enter a password");
		}
		if (hasErrors()) {
			return INPUT;
		}
		logger.info("Login attempted for username: " + username);
		User user = userRepository.findUserByUsername(username);
		if (user == null) {
			addActionError("Unknown user");
			return INPUT;
		}
		if (!user.getPassword().equals(password)) {
			addActionError("Wrong password");
			return INPUT;
		}
		logger.info("Login successful for username: " + username);
		getShoppingSession().setLoggedInUser(user);
		return SUCCESS;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
