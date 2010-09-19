package com.chatley.examples.bidders;

import com.chatley.examples.auction.BidReceiver;
import com.chatley.examples.auction.Participant;

public class PrimeBidder implements Participant {
	private final String name;
	private final int start;
	private final int max;
	private int latestBid;

	public PrimeBidder(String name, int start, int max) {
		this.name = name;
		this.start = start;
		this.max = max;
	}

	public String getName() {
		return name;
	}

	public void onBidAccepted(int price) {
		this.latestBid = price;
	}

	public void onYourTurnToBid(BidReceiver receiver) {
		int nextBid = nextBid();
		if (nextBid <= max) {
			receiver.bid(nextBid);
		}
	}

	private int nextBid() {
		if (latestBid == 0) {
			return start;
		} else {
			int nextPrimeWithinLimit = nextPrimeWithinLimit(latestBid);
			return nextPrimeWithinLimit == -1 ? max : nextPrimeWithinLimit;
		}
	}

	private int nextPrimeWithinLimit(int number) {
		boolean[] primeOrNotFlags = new boolean[max + 1];
		assumeEveryNumberIsPrime(primeOrNotFlags);
		for(int i=2; i < primeOrNotFlags.length; i++) {
			if (primeOrNotFlags[i]) {
				// i is prime
				if (i > number) {
					return i;
				} else {
					// flag multiples of i as non-prime
					for (int j = i * 2; j < primeOrNotFlags.length; j += i) {
						primeOrNotFlags[j] = false;
					}
				}
			}
		}
		return -1;
	}

	private void assumeEveryNumberIsPrime(boolean[] primeOrNotFlags) {
		for(int i=1; i < primeOrNotFlags.length; i++) {
			primeOrNotFlags[i] = true;
		}
	}
	
	
	public String toString() {
		return "PrimeBidder: " + name + ", " + start + ", " + max;
	}

}
