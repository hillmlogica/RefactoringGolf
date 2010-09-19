package com.chatley.examples.bidders;

public class CappedBidStrategy implements BidStrategy {

	private final int limit;
	private final BidStrategy underlying;

	public CappedBidStrategy(BidStrategy underlying, int limit) {
		this.underlying = underlying;
		this.limit = limit;
	}

	public int nextBid(BidHistory bidHistory) {
		int nextBid = underlying.nextBid(bidHistory);
		return nextBid > limit ? 0 : nextBid;
	}
	
	public String toString() {
		return "Capped to : " + limit;
	}

}
