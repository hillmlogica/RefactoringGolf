package com.chatley.examples.bidders;

public class PrimeBidStrategy implements BidStrategy {

	private final int startingBid;

	public PrimeBidStrategy(int startingBid) {
		this.startingBid = startingBid;
	}

	public int nextBid(BidHistory bidHistory) {
		return bidHistory.numberOfBids() == 0 ? startingBid : nextPrime(bidHistory.leadingBid());
	}

	private int nextPrime(int n) {
		boolean[] primeOrNotFlags = new boolean[2 * n + 1];
		assumeEveryNumberIsPrime(primeOrNotFlags);
		for(int i=2; i < primeOrNotFlags.length; i++) {
			if (primeOrNotFlags[i]) {
				// i is prime
				if (i > n) {
					return i;
				} else {
					// flag multiples of i as non-prime
					for (int j = i * 2; j < primeOrNotFlags.length; j += i) {
						primeOrNotFlags[j] = false;
					}
				}
			}
		}
		return -1;  // no prime found
	}

	private void assumeEveryNumberIsPrime(boolean[] primeOrNotFlags) {
		for(int i=1; i < primeOrNotFlags.length; i++) {
			primeOrNotFlags[i] = true;
		}
	}
	
	public String toString() {
		return "primes from " + startingBid;
	}
}
