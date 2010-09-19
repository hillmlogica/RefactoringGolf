package com.chatley.examples.bidders;

public class FixedBidStrategy implements BidStrategy {

	private final int bid;

	public FixedBidStrategy(int bid) {
		this.bid = bid;
	}

	public int nextBid(BidHistory bidHistory) {
		return bid;
	}
	
	public String toString() {
		return "fixed: " + bid;
	}

}
