package com.chatley.examples.bidders;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

import com.chatley.examples.auction.BidReceiver;

public class PrimeBidderTest extends MockObjectTestCase {

	private BidReceiver receiver = mock(BidReceiver.class);

	public void testStartsBiddingAtStart() {
		checking(new Expectations() {{
			one(receiver).bid(2);
		}});
		PrimeBidder bidder = new PrimeBidder("sally", 2, 47);
		bidder.onYourTurnToBid(receiver);
	}

	public void testBidsAtNextHighestPrimeNumberAboveCurrentBid() {
		checking(new Expectations() {{
			one(receiver).bid(23);
		}});
		PrimeBidder bidder = new PrimeBidder("sally", 2, 47);
		bidder.onBidAccepted(20);
		bidder.onYourTurnToBid(receiver);
	}

	public void testBidsAtLastPrimeNumberBeforeLimitWhenCurrentBidIsJustBelow() {
		checking(new Expectations() {{
			one(receiver).bid(47);
		}});
		PrimeBidder bidder = new PrimeBidder("sally", 2, 50);
		bidder.onBidAccepted(43);
		bidder.onYourTurnToBid(receiver);
	}

	public void testBidsAtLimitWhenNoMorePrimeNumbersBetweenCurrentBidAndLimit() {
		checking(new Expectations() {{
			one(receiver).bid(50);
		}});
		PrimeBidder bidder = new PrimeBidder("sally", 2, 50);
		bidder.onBidAccepted(47);
		bidder.onYourTurnToBid(receiver);
	}

	public void testName() {
		PrimeBidder bidder = new PrimeBidder("sally", 2, 47);
		assertThat(bidder.getName(), is("sally"));
	}
}
