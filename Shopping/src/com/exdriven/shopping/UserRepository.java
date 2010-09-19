package com.exdriven.shopping;

public interface UserRepository {

	User findUserByUsername(String username);

	void save(User user);

}
