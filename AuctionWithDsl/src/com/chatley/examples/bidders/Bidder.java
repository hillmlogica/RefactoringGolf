package com.chatley.examples.bidders;

import java.util.Stack;

import com.chatley.examples.auction.BidReceiver;
import com.chatley.examples.auction.Participant;

public class Bidder implements Participant {
	private final String name;
	private final BidStrategy nextBidStrategy;
	private final SimpleBidHistory bidHistory = new SimpleBidHistory();
	
	public Bidder(String name, BidStrategy nextBidStrategy) {
		this.name = name;
		this.nextBidStrategy = nextBidStrategy;
	}
	
	public void onBidAccepted(int price) {
		bidHistory.onBid(price);
	}

	public void onYourTurnToBid(BidReceiver receiver) {
		receiver.bid(nextBid());
	}

	private int nextBid() {
		return nextBidStrategy.nextBid(bidHistory);
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return "IncrementingBidder: " + name + ", " + nextBidStrategy;
	}
	
	public static class SimpleBidHistory implements BidHistory {
		private Stack<Integer> bids = new Stack<Integer>();

		public int leadingBid() {
			return numberOfBids() == 0 ? 0 : bids.peek();
		}

		public int numberOfBids() {
			return bids.size();
		}
		
		public void onBid(int bid) {
			bids.add(bid);
		}
	}

}