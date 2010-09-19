package com.exdriven.shopping;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HomePage extends ShoppingPage {

	public HomePage(HtmlPage page) {
		super(page);
		Assert.assertThat(page.getTitleText(), CoreMatchers.is("Welcome to Shopping!"));
	}

	public HomePage loginAs(User aUser) {
		HtmlForm loginForm = page.getFormByName("login");
		HtmlInput username = loginForm.getInputByName("username");
		username.setValueAttribute(aUser.getUsername());
		HtmlInput password = loginForm.getInputByName("password");
		password.setValueAttribute(aUser.getPassword());
		HtmlPage pageAfterSubmittingForm = click(loginForm.getInputByName("loginButton"));
		List<HtmlElement> errors = pageAfterSubmittingForm.getElementsByName("error");
		for (HtmlElement htmlElement : errors) {
			log.info("Error from server: " + htmlElement.asText());
		}
		return new HomePage(pageAfterSubmittingForm);
	}

	public String getWelcomeMessage() {
		return textIn("welcomeMessage");
	}

	public RegistrationPage followRegistrationLink() {
		return new RegistrationPage(click("registrationLink"));
	}

	public HomePage addToBasket(String productName, int quantity) {
		HtmlForm form = page.getFormByName("product_" + productName);
		HtmlInput quantityField = form.getInputByName("quantity");
		quantityField.setValueAttribute(String.valueOf(quantity));
		return new HomePage(click(form.getInputByName("add")));
	}

	public String basketPrice() {
		return textIn("basketPrice");
	}

	public HomePage placeTheOrder() {
		HtmlForm form = page.getFormByName("placeOrder");
		return new HomePage(click(form.getInputByName("submitOrder")));
	}

	public List<String> getOrderHistoryHeadings() {
		List<String> headings = new ArrayList<String>();
		List<HtmlElement> elements = page.getElementsByName("orderSummary");
		for (HtmlElement htmlElement : elements) {
			headings.add(htmlElement.asText());
		}
		return headings;
	}
}
