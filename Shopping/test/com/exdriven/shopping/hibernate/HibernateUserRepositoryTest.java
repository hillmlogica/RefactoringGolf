package com.exdriven.shopping.hibernate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.Session;
import org.junit.Test;

import com.exdriven.shopping.User;
import com.exdriven.shopping.UserBuilder;
import com.exdriven.shopping.hibernate.SessionFactoryWrapper.SessionClosure;

public class HibernateUserRepositoryTest extends CrudTestCase {
	private HibernateUserRepository userRepository = new HibernateUserRepository(sessionFactory());
	private final String username1 = RandomStringUtils.random(10);
	private final String username2 = RandomStringUtils.random(10);

	@Test
	public void canFindAUserByUsername() {
		final User user1 = new UserBuilder().withUsername(username1).build();
		final User user2 = new UserBuilder().withUsername(username2).build();
		
		save(user1, user2);
		
		inASession(new SessionClosure() {
			public void run(@SuppressWarnings("unused") Session session) {
				User persistedUser1 = userRepository.findUserByUsername(username1);
				assertThat(persistedUser1, is(not(nullValue())));
				assertThat(persistedUser1.getUsername(), is(user1.getUsername()));

				User persistedUser2 = userRepository.findUserByUsername(username2);
				assertThat(persistedUser2, is(not(nullValue())));
				assertThat(persistedUser2.getUsername(), is(user2.getUsername()));
			}
		});
	}
	
	@Test
	public void findByUsernameReturnsNullWhenNoUserWithTheUsernameExists() {
		inASession(new SessionClosure() {
			public void run(@SuppressWarnings("unused") Session session) {
				assertThat(userRepository.findUserByUsername("a username that doesn't exist"), is(nullValue()));
			}
		});
	}
}
