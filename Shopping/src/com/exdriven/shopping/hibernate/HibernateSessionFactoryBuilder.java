package com.exdriven.shopping.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryBuilder {

	public SessionFactory build() {
		Configuration configuration = new Configuration();
		configuration.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
		configuration.setProperty("hibernate.connection.url", "jdbc:hsqldb:hsql://localhost");
		configuration.setProperty("hibernate.connection.username", "sa");
		configuration.setProperty("hibernate.connection.password", "");
		configuration.setProperty("hibernate.connection.pool_size", "1");
	    configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
	    configuration.setProperty("hibernate.current_session_context_class", "thread");
	    configuration.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
	    configuration.setProperty("hibernate.show_sql", "false");
	    configuration.setProperty("hibernate.hbm2ddl.auto", "create");
	    configuration.addResource("com/exdriven/shopping/hibernate/Order.hbm.xml");
	    configuration.addResource("com/exdriven/shopping/hibernate/Product.hbm.xml");
	    configuration.addResource("com/exdriven/shopping/hibernate/User.hbm.xml");
	    configuration.addResource("com/exdriven/shopping/hibernate/Basket.hbm.xml");
		return configuration.buildSessionFactory();
	}
}
