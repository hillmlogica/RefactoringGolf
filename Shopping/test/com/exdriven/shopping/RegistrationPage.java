package com.exdriven.shopping;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class RegistrationPage extends ShoppingPage {

	private final Logger log = LoggerFactory.getLogger(getClass());

	public RegistrationPage(HtmlPage page) {
		super(page);
		Assert.assertThat(page.getTitleText(), CoreMatchers.is("Please register below"));
	}

	public HomePage registerAs(String username, String firstname, String password) {
		HtmlForm registrationForm = page.getFormByName("register");
		HtmlInput usernameField = registrationForm.getInputByName("username");
		usernameField.setValueAttribute(username);
		HtmlInput passwordField = registrationForm.getInputByName("password");
		passwordField.setValueAttribute(password);
		HtmlInput firstNameField = registrationForm.getInputByName("firstname");
		firstNameField.setValueAttribute(firstname);
		HtmlPage pageAfterSubmittingForm = click(registrationForm.getInputByName("registerButton"));
		List<HtmlElement> errors = pageAfterSubmittingForm.getElementsByName("error");
		for (HtmlElement htmlElement : errors) {
			log.info("Error from server: " + htmlElement.asText());
		}
		return new HomePage(pageAfterSubmittingForm);
	}
}
