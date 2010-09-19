package com.exdriven.shopping;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

public class EndToEndTest {

	private Shopper shopper;
	private ShoppingWorld shoppingWorld;
	private StubOrderService orderService;

	@Test
	public void aNewUserRegistersThemselves() {
		HomePage homePage = shopper.gotoHome();
		assertThat(homePage.getWelcomeMessage(), is("Hi there! Please login below or register"));
		RegistrationPage registrationPage = homePage.followRegistrationLink();
		HomePage homePageAgain = registrationPage.registerAs("smithj", "John", "secret");
		assertThat(homePageAgain.getWelcomeMessage(), is("Hi John!"));
	}

	@Test
	public void anExistingUserPlacesANewOrder() {
		User anArbitraryUser = new UserBuilder().withFirstname("Fred").build();
		Product product1 = new ProductBuilder().withPrice(5).withProductRef("ref1").build();
		
		shoppingWorld.assuming(product1);
		shoppingWorld.assuming(anArbitraryUser);
		
		HomePage homePage = shopper.gotoHome().loginAs(anArbitraryUser);
		homePage = homePage.addToBasket(product1.getName(), 13);
		assertThat(homePage.basketPrice(), is("65"));
		
		homePage = homePage.placeTheOrder();
		assertThat(homePage.basketPrice(), is("0"));

		String expectedOrderXml = 
			"<Order>\n" +
			"  <orderLines>\n" +
			"    <Line>\n" +
			"      <product>ref1</product>\n" +
			"      <quantity>13</quantity>\n" +
			"    </Line>\n" +
			"  </orderLines>\n" +
			"</Order>";
		
		assertThat(orderService.lastMessage(), is(expectedOrderXml));
	}
	
	@Test
	public void anExistingUserCanLogInAndSeeTheirOrderHistory() {
		User anArbitraryUser = new UserBuilder().withFirstname("Fred").build();
		Product product1 = new ProductBuilder().build();
		Product product2 = new ProductBuilder().build();
		Order order1 = new OrderBuilder()
			.forUser(anArbitraryUser)
			.withTime(new DateTime(2009, 4, 1, 13, 3, 50, 999))
			.withLine(product1, 1).withLine(product2, 2).build();
		Order order2 = new OrderBuilder()
			.forUser(anArbitraryUser)
			.withTime(new DateTime(2009, 3, 26, 1, 2, 1, 0))
			.withLine(product2, 100).build();
		
		shoppingWorld.assuming(anArbitraryUser);
		HomePage homePage = shopper.gotoHome().loginAs(anArbitraryUser);
		assertThat(homePage.getWelcomeMessage(), is("Hi Fred!"));
		String order1Summary = "Order " + order1.getId() + " (13:03 on 01/Apr/2009)";
		String order2Summary = "Order " + order2.getId() + " (01:02 on 26/Mar/2009)";
		assertThat(homePage.getOrderHistoryHeadings(), is(Arrays.asList(order2Summary, order1Summary)));
	}

	@Before
	public void setUp() {
		shoppingWorld = ShoppingWorld.theWorld();
		shopper = shoppingWorld.aShopper();
		orderService = shoppingWorld.stubOrderService();
	}
}
