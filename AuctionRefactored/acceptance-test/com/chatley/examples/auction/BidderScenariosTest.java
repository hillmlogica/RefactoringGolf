package com.chatley.examples.auction;

import org.jmock.integration.junit3.MockObjectTestCase;

import com.chatley.examples.bidders.Bidder;
import com.chatley.examples.bidders.CappedBidStrategy;
import com.chatley.examples.bidders.DelayedBidStrategy;
import com.chatley.examples.bidders.FixedBidStrategy;
import com.chatley.examples.bidders.IncrementalBidStrategy;
import com.chatley.examples.bidders.LimitBidStrategy;
import com.chatley.examples.bidders.PrimeBidStrategy;

public class BidderScenariosTest extends MockObjectTestCase {

	private AuctionTestContext auctionContext = new AuctionTestContext();
	
	/*
	 * fred bids 10 -> winner
	 */
	public void testSoleFixedBidderWins() {
		auctionContext
			.withBidder(new Bidder("fred", new FixedBidStrategy(10)))
			.whenAuctionRuns()
			.expectWinFor("fred", 10);
	}
    
	/*
	 * george bids 25 -> winner
	 */
	public void testSoleReactionaryBidderWins() {
		auctionContext
			.withBidder(new Bidder("george", new CappedBidStrategy(new IncrementalBidStrategy(10, 25), 1000)))
			.whenAuctionRuns()
			.expectWinFor("george", 10);
	}

	/*
	 * fred bids 10
	 * george bids 20 -> winner
	 */
	public void testHighBidBeatsLowBid() throws Exception {
		auctionContext
			.withBidder(new Bidder("fred", new FixedBidStrategy(10)))
			.withBidder(new Bidder("george", new FixedBidStrategy(20)))
			.whenAuctionRuns()
			.expectWinFor("george", 20);
	}
	
	/*
	 * fred bids 10
	 * george bids 10 + 25 = 35 -> winner
	 */
	public void testReactionaryBidderBeatsFixedBidder() throws Exception {
		auctionContext
			.withBidder(new Bidder("fred", new CappedBidStrategy(new IncrementalBidStrategy(10, 10), 10000)))
			.withBidder(new Bidder("george", new FixedBidStrategy(25)))
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
			.withBidder(new Bidder("fred", new CappedBidStrategy(new IncrementalBidStrategy(10, 10), 100)))
			.withBidder(new Bidder("george", new CappedBidStrategy(new IncrementalBidStrategy(30, 25), 100)))
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
	public void testOrderOfEntryAffectsTheResult() throws Exception {
		auctionContext
			.withBidder(new Bidder("george", new CappedBidStrategy(new IncrementalBidStrategy(30, 25), 100)))
			.withBidder(new Bidder("fred", new CappedBidStrategy(new IncrementalBidStrategy(10, 10), 100)))
			.whenAuctionRuns()
			.expectWinFor("george", 100);
	}
	
	/*
	 * prime     bids 5 -> winner
	 * slowcoach waits
	 */
	public void testADelayedBidderMightMissOutWhenOnlyOneOtherBidder() {
		auctionContext
			.withBidder(new Bidder("prime", new CappedBidStrategy(new PrimeBidStrategy(5), 20)))
			.withBidder(new Bidder("slowcoach", new DelayedBidStrategy(new FixedBidStrategy(100), 2)))
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
			.withBidder(new Bidder("prime1", new CappedBidStrategy(new PrimeBidStrategy(5), 13)))
			.withBidder(new Bidder("prime2", new CappedBidStrategy(new PrimeBidStrategy(5), 13)))
			.withBidder(new Bidder("slowcoach", new DelayedBidStrategy(new FixedBidStrategy(100), 4)))
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
			.withBidder(new Bidder("prime1", new CappedBidStrategy(new PrimeBidStrategy(180), 200)))
			.withBidder(new Bidder("prime2", new CappedBidStrategy(new PrimeBidStrategy(180), 196)))
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
			.withBidder(new Bidder("ben", new CappedBidStrategy(new IncrementalBidStrategy(10, 30), 100)))
			.withBidder(new Bidder("ben", new LimitBidStrategy(100)))
			.whenAuctionRuns()
			.expectWinFor("ben", 92);
	}
	
	/*
	 * charles bids 50
	 * david bids 100 -> winner
	 */
	public void testWhenTwoLimitBiddersFightThenTheOneWithTheHigherLimitWins() {
		auctionContext
			.withBidder(new Bidder("charles", new LimitBidStrategy(100)))
			.withBidder(new Bidder("david", new LimitBidStrategy(150)))
			.whenAuctionRuns()
			.expectWinFor("david", 100);
	}
}


