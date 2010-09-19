package com.chatley.examples.auction;

import static com.chatley.examples.bidders.BidStrategyBuilder.fixed;
import static com.chatley.examples.bidders.BidStrategyBuilder.from;
import static com.chatley.examples.bidders.BidderBuilder.bidding;
import static com.chatley.examples.bidders.LimitBidStrategy.towardsLimit;

import org.jmock.integration.junit3.MockObjectTestCase;

public class BidderScenariosTest extends MockObjectTestCase {

	private AuctionTestContext auctionContext = new AuctionTestContext();
	
	/**
	 * B(10, 0, 10000)
	 */
	public void testSoleFixedBidderWins() {
		auctionContext
			.withBidder(bidding(fixed(10)).called("fred"))
			.whenAuctionRuns()
			.expectWinFor("fred", 10);
	}
    
	/**
	 * B(10, 25, 10000)
	 */
	public void testSoleReactionaryBidderWins() {
		auctionContext
			.withBidder(bidding(from(10).inStepsOf(25).cappedAt(1000)).called("george"))
			.whenAuctionRuns()
			.expectWinFor("george", 10);
	}

	/** 
	 * B(10, 0, 10000) B(20, 0, 10000)
	 */
	public void testHighBidBeatsLowBid() throws Exception {
		auctionContext
			.withBidder(bidding(fixed(10)).called("fred"))
			.withBidder(bidding(fixed(20)).called("george"))
			.whenAuctionRuns()
			.expectWinFor("george", 20);
	}
	
	/**
	 * B(10, 10, 10000) B(25, 0, 10000)
	 */
	public void testReactionaryBidderBeatsFixedBidder() throws Exception {
		auctionContext
			.withBidder(bidding(from(10).inStepsOf(10).cappedAt(10000)).called("fred"))
			.withBidder(bidding(fixed(25)).called("george"))
			.whenAuctionRuns()
			.expectWinFor("fred", 35);
	}
	
	/**
	 * B(10, 10, 100) B(30, 25, 100)
	 */
	public void testBiddersKeepBiddingUntilTheirFinancialConstraintsStopThem() throws Exception {
		auctionContext
			.withBidder(bidding(from(10).inStepsOf(10).cappedAt(100)).called("fred"))
			.withBidder(bidding(from(30).inStepsOf(25).cappedAt(100)).called("george"))
			.whenAuctionRuns()
			.expectWinFor("fred", 80);
	}
	
	/**
	 * B(30, 25, 100) B(10, 10, 100) 
	 */
	public void testOrderOfEntryAffectsTheResult() throws Exception {
		auctionContext
			.withBidder(bidding(from(30).inStepsOf(25).cappedAt(100)).called("george"))
			.withBidder(bidding(from(10).inStepsOf(10).cappedAt(100)).called("fred"))
			.whenAuctionRuns()
			.expectWinFor("george", 100);
	}
	
	public void testADelayedBidderMightMissOutWhenOnlyOneOtherBidder() {
		auctionContext
			.withBidder(bidding(from(5).usingPrimeNumbers().cappedAt(20)).called("prime"))
			.withBidder(bidding(fixed(100).onlyBidWhenNumberOfBidsReaches(2)).called("slowcoach"))
			.whenAuctionRuns()
			.expectWinFor("prime", 5);
	}
	
	public void testADelayedBidderJoinsInAndGazumpsOtherSmallFryWhenThereHaveBeenEnoughBids() {
		auctionContext
			.withBidder(bidding(from(5).usingPrimeNumbers().cappedAt(20)).called("prime1"))
			.withBidder(bidding(from(5).usingPrimeNumbers().cappedAt(20)).called("prime2"))
			.withBidder(bidding(fixed(100).onlyBidWhenNumberOfBidsReaches(4)).called("slowcoach"))
			.whenAuctionRuns()
			.expectWinFor("slowcoach", 100);
	}
	
	public void testAPairOfPrimeBiddersBidEachOtherUpTheSequenceOfPrimesUntilOneGoesBust() {
		auctionContext
			.withBidder(bidding(from(180).usingPrimeNumbers().cappedAt(200)).called("prime1"))
			.withBidder(bidding(from(180).usingPrimeNumbers().cappedAt(196)).called("prime2"))
			.whenAuctionRuns()
			.expectWinFor("prime1", 197);
	}
	
	/*
	 * adam bids 10
	 * ben bids 55
	 * adam bids 85
	 * ben bids 92 -> winner
	 */
	public void testALimitBidderOutbidsAnIncrementalBidderByBeingMoreFlexibleWithTheBidIncrement() {
		auctionContext
			.withBidder(bidding(from(10).inStepsOf(30).cappedAt(100)).called("ben"))
			.withBidder(bidding(towardsLimit(100)).called("ben"))
			.whenAuctionRuns()
			.expectWinFor("ben", 92);
	}
	
	public void testWhenTwoLimitBiddersFightThenTheOneWithTheHigherLimitWins() {
		auctionContext
			.withBidder(bidding(towardsLimit(100)).called("charles"))
			.withBidder(bidding(towardsLimit(150)).called("david"))
			.whenAuctionRuns()
			.expectWinFor("david", 100);
	}
}


