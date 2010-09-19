package com.exdriven.shopping;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.html.ClickableElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ShoppingPage {

	protected final HtmlPage page;
	protected final Logger log = LoggerFactory.getLogger(getClass());

	
	protected ShoppingPage(HtmlPage page) {
		this.page = page;
	}

	protected HtmlPage click(ClickableElement element) {
		try {
			return (HtmlPage) element.click();
		} catch (IOException e) {
			throw new ShopperException("Couldn't click on " + element, e);
		}
	}
	
	protected HtmlPage click(String id) {
		return click((ClickableElement) page.getElementById(id));
	}
	
	protected String textIn(String id) {
		return page.getElementById(id).asText();
	}

}
