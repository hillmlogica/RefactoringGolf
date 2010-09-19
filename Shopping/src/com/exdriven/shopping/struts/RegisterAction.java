package com.exdriven.shopping.struts;

import org.apache.commons.lang.StringUtils;

import com.exdriven.shopping.Basket;
import com.exdriven.shopping.User;
import com.exdriven.shopping.UserRepository;
import com.exdriven.shopping.bootstrap.WebApplicationContext;

public class RegisterAction extends BaseShoppingAction {
	private String firstname;
	private String username;
	private String password;
	
	private UserRepository userRepository = WebApplicationContext.instance().userRepository();

	public String doNew() {
		return INPUT;
	}
	
	@Override
	public String execute() {
		if (StringUtils.isBlank(firstname)) {
			addActionError("Please enter a firstname");
		}
		if (StringUtils.isBlank(username)) {
			addActionError("Please enter a username");
		}
		if (StringUtils.isBlank(password)) {
			addActionError("Please enter a password");
		}
		if (userRepository.findUserByUsername(username) != null) {
			addActionError("Please choose a different username");
		}
		if (hasErrors()) {
			return INPUT;
		}
		User user = new User();
		user.setFirstname(firstname);
		user.setPassword(password);
		user.setUsername(username);
		user.setBasket(new Basket());
		userRepository.save(user);
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
}
