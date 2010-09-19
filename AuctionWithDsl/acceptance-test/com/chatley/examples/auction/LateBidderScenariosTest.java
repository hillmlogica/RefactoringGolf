package com.chatley.examples.auction;

import static com.chatley.examples.bidders.BidderBuilder.*;
import static com.chatley.examples.bidders.BidStrategyBuilder.*;
import junit.framework.TestCase;

public class LateBidderScenariosTest extends TestCase {

	private AuctionTestContext auctionContext = new AuctionTestContext();
	
	public void testLateBidderOnOwnDoesntWin() throws Exception {
		auctionContext
			.withBidder(bidding(atLeadingBidTimes(10).cappedAt(1000).onlyBidWhenNumberOfBidsReaches(2)).called("lisa"))
			.whenAuctionRuns()
			.expectNoWinner();
	}
	
	/*
	 * charlie bids 20
	 * diana counters with 40
	 * charlie ups to 60
	 * diana counters with 80
	 * lisa jumps in with 160 and wins
	 */
	public void testLateBidderGazumpsTwoLowerReactionaryBidders() throws Exception {
		auctionContext
			.withBidder(bidding(atLeadingBidTimes(2).cappedAt(1000).onlyBidWhenNumberOfBidsReaches(4)).called("lisa"))
			.withBidder(bidding(from(20).inStepsOf(20).cappedAt(100)).called("charlie"))
			.withBidder(bidding(from(20).inStepsOf(20).cappedAt(100)).called("diana"))
			.whenAuctionRuns()
			.expectWinFor("lisa", 160);
	}

}
