package com.chatley.examples.bidders;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

import com.chatley.examples.auction.BidReceiver;

public class IncrementingBidderTest extends MockObjectTestCase {

	private BidReceiver receiver = mock(BidReceiver.class);

	public void testBidsImmediatelyWithStartingBidIfNooneHasBid() {
		checking(new Expectations() {{
			one(receiver).bid(30);
		}});
		IncrementingBidder bidder = new IncrementingBidder("bert", 30, 5, 40);
		bidder.onYourTurnToBid(receiver);
	}

	public void testBidsAtCurrentBidPlusIncrementIfSomeoneHasBid() {
		checking(new Expectations() {{
			one(receiver).bid(37);
		}});
		IncrementingBidder bidder = new IncrementingBidder("bert", 30, 5, 40);
		bidder.onBidAccepted(32);
		bidder.onYourTurnToBid(receiver);
	}

	public void testStopsBiddingWhenCurrentBidPlusIncrementExceedsLimit() {
		IncrementingBidder bidder = new IncrementingBidder("bert", 30, 5, 40);
		bidder.onBidAccepted(36);
		bidder.onYourTurnToBid(receiver);
	}

	public void testDoesNotBidWhenCurrentBidExceedsLimit() {
		IncrementingBidder bidder = new IncrementingBidder("bert", 30, 5, 40);
		bidder.onBidAccepted(41);
		bidder.onYourTurnToBid(receiver);
	}

	public void testName() {
		IncrementingBidder bidder = new IncrementingBidder("bert", 30, 5, 40);
		assertThat(bidder.getName(), is("bert"));
	}
}
