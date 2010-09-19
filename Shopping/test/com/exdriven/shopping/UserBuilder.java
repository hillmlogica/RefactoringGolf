package com.exdriven.shopping;


public class UserBuilder {

	private String firstname = BuilderUtil.random("firstname");
	private String password = BuilderUtil.random("username");
	private String username = BuilderUtil.random("some username");

	public User build() {
		User user = new User();
		user.setFirstname(firstname);
		user.setPassword(password);
		user.setUsername(username);
		user.setBasket(new Basket());
		return user;
	}

	public UserBuilder withFirstname(String firstname) {
		this.firstname = firstname;
		return this;
	}

	public UserBuilder withUsername(String username) {
		this.username = username;
		return this;
	}

}
