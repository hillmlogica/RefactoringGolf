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
        poo(firstname, "firstname");
        poo(username, "username");
        poo(password, "password");
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

    private void poo(String s, String s2) {
        if (StringUtils.isBlank(s)) {
            addActionError("Please enter a "+s2);
		}
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
