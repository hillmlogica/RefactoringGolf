package com.chatley.examples.bidders;

import com.chatley.examples.auction.BidReceiver;
import com.chatley.examples.auction.Participant;

public class IncrementingBidder implements Participant {
	private final String name;
	private final int incr;
	private final int max;
	private final int start;
	private int leadingBid = 0;
	
	public IncrementingBidder(String name, int start, int incr, int max) {
		this.name = name;
		this.start = start;
		this.incr = incr;
		this.max = max;
	}
	
	public void onBidAccepted(int price) {
		leadingBid = price;
	}

	public void onYourTurnToBid(BidReceiver receiver) {
		if (wantToBid()) {
			receiver.bid(nextBid());
		}
	}

	private boolean wantToBid() {
		return (nextBid() <= max);
	}

	private int nextBid() {
		if (leadingBid == 0 || incr == 0) {
			return start;
		} else {
			return leadingBid + incr;
		}
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return "IncrementingBidder: " + name + ", " + start + ", " + incr + ", " + max;
	}

}