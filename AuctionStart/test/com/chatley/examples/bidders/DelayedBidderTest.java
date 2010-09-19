package com.chatley.examples.bidders;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

import com.chatley.examples.auction.BidReceiver;

public class DelayedBidderTest extends MockObjectTestCase {

	private BidReceiver receiver = mock(BidReceiver.class);

	public void testDoesNotBidIfThereHaveBeenNoBids() {
		DelayedBidder bidder = new DelayedBidder("sue", 1, 50);
		bidder.onYourTurnToBid(receiver);
	}

	public void testBidsImmediatelyIfThereAreNoBidsToWaitFor() {
		checking(new Expectations() {{
			one(receiver).bid(50);
		}});
		DelayedBidder bidder = new DelayedBidder("sue", 0, 50);
		bidder.onYourTurnToBid(receiver);
	}

	public void testBidsAfterOneBidAcceptedIfConfiguredToWaitForOneBid() {
		DelayedBidder bidder = new DelayedBidder("sue", 1, 50);
		bidder.onYourTurnToBid(receiver);
		bidder.onBidAccepted(10);
		
		checking(new Expectations() {{
			one(receiver).bid(50);
		}});
		
		bidder.onYourTurnToBid(receiver);
	}

	public void testStopsBiddingWhenCurrentBidMatchesOwnBid() {
		DelayedBidder bidder = new DelayedBidder("sue", 0, 20);
		bidder.onBidAccepted(20);
		bidder.onYourTurnToBid(receiver);
	}

	public void testStopsBiddingWhenCurrentBidExceedsOwnBid() {
		DelayedBidder bidder = new DelayedBidder("sue", 0, 20);
		bidder.onBidAccepted(30);
		bidder.onYourTurnToBid(receiver);
	}
	
	public void testName() {
		DelayedBidder bidder = new DelayedBidder("fred", 0, 0);
		assertThat(bidder.getName(), is("fred"));
	}
}
