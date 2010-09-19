/**
 * 
 */
package com.chatley.examples.bidders;


public class IncrementalBidStrategy implements BidStrategy {
	private final int start;
	private final int incr;
	
	public IncrementalBidStrategy(int start, int incr) {
		this.start = start;
		this.incr = incr;
	}

	public int nextBid(BidHistory bidHistory) {
		if (bidHistory.numberOfBids() == 0) {
			return start;
		} else {
			return bidHistory.leadingBid() + incr;
		}
	}
	
	public String toString() {
		return "from " + start + " to " + incr;
	}
}