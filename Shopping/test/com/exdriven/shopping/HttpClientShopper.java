package com.exdriven.shopping;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HttpClientShopper implements Shopper {
	private final WebClient webClient;
	private String homeUrl;

	public HttpClientShopper(int port) {
		webClient = new WebClient();
		homeUrl = "http://localhost:" + port;
	}

	public HomePage gotoHome() {
		return new HomePage(visit(homeUrl));
	}

	private HtmlPage visit(String url) {
		try {
			return (HtmlPage) webClient.getPage(url);
		} catch (Exception e) {
			throw new ShopperException("Couldn't visit page: " + url, e);
		}
	}
}
