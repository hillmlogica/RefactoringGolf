package com.chatley.examples.bidders;

import com.chatley.examples.auction.BidReceiver;
import com.chatley.examples.auction.Participant;

public class DelayedBidder implements Participant {
	private final String name;
	private final int numberOfBidsToWaitFor;
	private final int bid;
	private int numberOfBidsSoFar;
	private int leadingBid;

	public DelayedBidder(String name, int numberOfBidsToWaitFor, int bid) {
		this.name = name;
		this.numberOfBidsToWaitFor = numberOfBidsToWaitFor;
		this.bid = bid;
	}

	public String getName() {
		return name;
	}

	public void onBidAccepted(int price) {
		leadingBid = price;
		numberOfBidsSoFar++;
	}

	public void onYourTurnToBid(BidReceiver receiver) {
		if (numberOfBidsSoFar >= numberOfBidsToWaitFor && bid > leadingBid) {
			receiver.bid(bid);
		}
	}

	public String toString() {
		return "DelayedBidder: " + name + ", " + numberOfBidsSoFar + ", " + bid;
	}

}
