package com.chatley.examples.bidders;

public class MultiplierBidStrategy implements BidStrategy {

	private final int multiplier;

	public MultiplierBidStrategy(int multiplier) {
		this.multiplier = multiplier;
	}

	public int nextBid(BidHistory history) {
		return history.numberOfBids() == 0 ? 0 : multiplier * history.leadingBid();
	}

}
