package com.exdriven.shopping;

import java.util.HashSet;
import java.util.Set;

public class Basket {
	private int id;
	private Set<BasketEntry> entries = new HashSet<BasketEntry>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Set<BasketEntry> getEntries() {
		return entries;
	}

	public void setEntries(Set<BasketEntry> entries) {
		this.entries = entries;
	}
	
	public void clear() {
		entries.clear();
	}

	public int getPrice() {
		int total = 0;
		for (BasketEntry basketEntry : entries) {
			total += basketEntry.getProduct().getPrice();
		}
		return total;
	}

	public void add(Product product) {
		BasketEntry basketEntry = new BasketEntry();
		basketEntry.setProduct(product);
		entries.add(basketEntry);
	}
}
