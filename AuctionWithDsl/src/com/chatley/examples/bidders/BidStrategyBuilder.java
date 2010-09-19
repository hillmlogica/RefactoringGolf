/**
 * 
 */
package com.chatley.examples.bidders;


public class BidStrategyBuilder {
	private int start;
	private BidStrategy strategy;

	public static BidStrategyBuilder from(int start) {
		BidStrategyBuilder bidStrategyBuilder = new BidStrategyBuilder();
		bidStrategyBuilder.start = start;
		return bidStrategyBuilder;
	}
	
	public static BidStrategyBuilder fixed(int bid) {
		BidStrategyBuilder bidStrategyBuilder = new BidStrategyBuilder();
		bidStrategyBuilder.strategy = new FixedBidStrategy(bid);
		return bidStrategyBuilder;
	}
	
	public static BidStrategyBuilder atLeadingBidTimes(int multiplier) {
		BidStrategyBuilder bidStrategyBuilder = new BidStrategyBuilder();
		bidStrategyBuilder.strategy = new MultiplierBidStrategy(multiplier);
		return bidStrategyBuilder;
	}

	public BidStrategyBuilder inStepsOf(int increment) {
		this.strategy = new IncrementalBidStrategy(start, increment);
		return this;
	}

	public BidStrategyBuilder usingPrimeNumbers() {
		this.strategy = new PrimeBidStrategy(start);
		return this;
	}

	public BidStrategyBuilder cappedAt(int limit) {
		this.strategy = new CappedBidStrategy(strategy, limit);
		return this;
	}
	
	public BidStrategyBuilder onlyBidWhenNumberOfBidsReaches(int numberOfBids) {
		this.strategy = new DelayedBidStrategy(strategy, numberOfBids);
		return this;
	}
	


	public BidStrategy build() {
		return strategy;
	}
	
}