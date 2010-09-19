package com.exdriven.shopping;

import java.util.ArrayList;
import java.util.List;

public class StubOrderService implements XmlOrderService {
	List<String> messages = new ArrayList<String>();

	public void send(String orderAsXml) {
		messages.add(orderAsXml);
	}
	
	public void reset() {
		messages.clear();
	}
	
	public String lastMessage() {
		return messages.get(messages.size()-1);
	}

}
