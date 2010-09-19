package com.chatley.examples.auction;

import org.jmock.integration.junit3.MockObjectTestCase;

import com.chatley.examples.bidders.DelayedBidder;
import com.chatley.examples.bidders.IncrementingBidder;
import com.chatley.examples.bidders.LimitBidder;
import com.chatley.examples.bidders.PrimeBidder;

public class BidderScenariosTest extends MockObjectTestCase {

	private AuctionTestContext auctionContext = new AuctionTestContext();
	
	/*
	 * fred bids 10 -> winner
	 */
	public void testSoleFixedBidderWins() {
		auctionContext
			.withBidder(bidder("fred", 10, 0, 10000))
			.whenAuctionRuns()
			.expectWinFor("fred", 10);
	}
    
	/*
	 * george bids 25 -> winner
	 */
	public void testSoleReactionaryBidderWins() {
		auctionContext
			.withBidder(bidder("george", 10, 25, 10000))
			.whenAuctionRuns()
			.expectWinFor("george", 10);
	}

	/*
	 * fred bids 10
	 * george bids 20 -> winner
	 */
	public void testHighBidBeatsLowBid() throws Exception {
		auctionContext
			.withBidder(bidder("fred", 10, 0, 10000))
			.withBidder(bidder("george", 20, 0, 10000))
			.whenAuctionRuns()
			.expectWinFor("george", 20);
	}
	
	/*
	 * fred bids 10
	 * george bids 10 + 25 = 35 -> winner
	 */
	public void testReactionaryBidderBeatsFixedBidder() throws Exception {
		auctionContext
			.withBidder(bidder("fred", 10, 10, 10000))
			.withBidder(bidder("george", 25, 0, 10000))
			.whenAuctionRuns()
			.expectWinFor("fred", 35);
	}
	
	/*
	 * fred bids   10
	 * george bids 10 + 25 = 35
	 * fred bids   35 + 10 = 45
	 * george bids 45 + 25 = 70
	 * fred bids   70 + 10 = 80 -> winner
	 */
	public void testBiddersKeepBiddingUntilTheirFinancialConstraintsStopThem() throws Exception {
		auctionContext
			.withBidder(bidder("fred", 10, 10, 100))
			.withBidder(bidder("george", 30, 25, 100))
			.whenAuctionRuns()
			.expectWinFor("fred", 80);
	}
	
	/*
	 * george bids 30
	 * fred bids   30 + 10 = 40
	 * george bids 40 + 25 = 65
	 * fred bids   65 + 10 = 75
	 * george bids 75 + 25 = 100 -> winner
	 */
	public void testOrderOfEntryAffectsTheResult() {
		auctionContext
			.withBidder(bidder("george", 30, 25, 100))
			.withBidder(bidder("fred", 10, 10, 100))
			.whenAuctionRuns()
			.expectWinFor("george", 100);
	}
	
	/*
	 * prime     bids 5 -> winner
	 * slowcoach waits
	 */
	public void testADelayedBidderMightMissOutWhenOnlyOneOtherBidder() {
		auctionContext
			.withBidder(primeBidder("prime", 5, 20))
			.withBidder(delayedBidder("slowcoach", 2, 100))
			.whenAuctionRuns()
			.expectWinFor("prime", 5);
	}
	
	/*
	 * prime1    bids 5
	 * prime2    bids 7
	 * slowcoach waits
	 * prime1    bids 11
	 * prime2    bids 13
	 * slowcoach bids 100 -> winner
	 */
	public void testADelayedBidderJoinsInAndGazumpsOtherSmallFryWhenThereHaveBeenEnoughBids() {
		auctionContext
			.withBidder(primeBidder("prime1", 5, 13))
			.withBidder(primeBidder("prime2", 5, 13))
			.withBidder(delayedBidder("slowcoach", 4, 100))
			.whenAuctionRuns()
			.expectWinFor("slowcoach", 100);
	}
	
	/*
	 * prime1   bids 180
	 * prime2   bids 181
	 * prime1   bids 191
	 * prime2   bids 193
	 * prime1   bids 197 -> winner
	 */
	public void testAPairOfPrimeBiddersBidEachOtherUpTheSequenceOfPrimesUntilOneGoesBust() {
		auctionContext
			.withBidder(primeBidder("prime1", 180, 200))
			.withBidder(primeBidder("prime2", 180, 196))
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
			.withBidder(bidder("adam", 10, 30, 100))
			.withBidder(limitBidder("ben", 100))
			.whenAuctionRuns()
			.expectWinFor("ben", 92);
	}
	
	/*
	 * charles bids 50
	 * david bids 100 -> winner
	 */
	public void testWhenTwoLimitBiddersFightThenTheOneWithTheHigherLimitWins() {
		auctionContext
			.withBidder(limitBidder("charles", 100))
			.withBidder(limitBidder("david", 150))
			.whenAuctionRuns()
			.expectWinFor("david", 100);
	}
	
	private Participant bidder(String name, int start, int incr, int limit) {
    	return new IncrementingBidder(name, start, incr, limit);
    }
	
	private Participant limitBidder(String name, int limit) {
		return new LimitBidder(name, limit);
	}

	private Participant primeBidder(String name, int start, int max) {
		return new PrimeBidder(name, start, max);
	}

	private Participant delayedBidder(String name, int numberOfBidsToWaitFor, int bid) {
		return new DelayedBidder(name, numberOfBidsToWaitFor, bid);
	}
}


