package com.chatley.examples.bidders;

import com.chatley.examples.auction.Participant;

public class BidderBuilder {
	private final BidStrategy strategy;

	public BidderBuilder(BidStrategy strategy) {
		this.strategy = strategy;
	}

	public Participant called(String name) {
		return new Bidder(name, strategy);
	}
	
	public static BidderBuilder bidding(BidStrategyBuilder strategy) {
		return new BidderBuilder(strategy.build());
	}

	public static BidderBuilder bidding(BidStrategy strategy) {
		return new BidderBuilder(strategy);
	}
}
