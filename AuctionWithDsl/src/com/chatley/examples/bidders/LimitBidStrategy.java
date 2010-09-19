package com.chatley.examples.bidders;

public class LimitBidStrategy implements BidStrategy {

	private final int limit;

	public static LimitBidStrategy towardsLimit(int limit) {
		return new LimitBidStrategy(limit);
	}
	
	public LimitBidStrategy(int limit) {
		this.limit = limit;
	}

	public int nextBid(BidHistory bidHistory) {
		if (bidHistory.leadingBid() < limit - 1) {
			return bidHistory.leadingBid() + ((limit - bidHistory.leadingBid())/ 2);
		} else {
			return limit;
		}
	}
	
	public String toString() {
		return "towards limit: " + limit;
	}

}
