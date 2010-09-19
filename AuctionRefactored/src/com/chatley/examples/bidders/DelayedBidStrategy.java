package com.chatley.examples.bidders;

public class DelayedBidStrategy implements BidStrategy {

	private final int numberOfBidsToWaitFor;
	private final BidStrategy underlying;

	public DelayedBidStrategy(BidStrategy underlying, int numberOfBidsToWaitFor) {
		this.underlying = underlying;
		this.numberOfBidsToWaitFor = numberOfBidsToWaitFor;
	}

	public int nextBid(BidHistory bidHistory) {
		if (bidHistory.numberOfBids() >= numberOfBidsToWaitFor) {
			return underlying.nextBid(bidHistory);
		}
		return 0;
	}

}
