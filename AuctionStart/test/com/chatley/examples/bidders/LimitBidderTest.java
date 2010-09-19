package com.chatley.examples.bidders;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

import com.chatley.examples.auction.BidReceiver;

public class LimitBidderTest extends MockObjectTestCase {

	private BidReceiver receiver = mock(BidReceiver.class);

	public void testBidsImmediatelyAtHalfHisLimitIfNoOneElseHasBid() {
		checking(new Expectations() {{
			one(receiver).bid(15);
		}});
		LimitBidder bidder = new LimitBidder("andy", 30);
		bidder.onYourTurnToBid(receiver);
	}

	public void testBidsHalfwayBetweenCurrentBidAndLimit() {
		checking(new Expectations() {{
			one(receiver).bid(25);
		}});
		LimitBidder bidder = new LimitBidder("andy", 30);
		bidder.onBidAccepted(20);
		bidder.onYourTurnToBid(receiver);
	}

	public void testBidsAtLimitWhenCurrentBidReachesLimitMinusOne() {
		checking(new Expectations() {{
			one(receiver).bid(30);
		}});
		LimitBidder bidder = new LimitBidder("andy", 30);
		bidder.onBidAccepted(29);
		bidder.onYourTurnToBid(receiver);
	}

	public void testDoesNotBidWhenCurrentBidReachesLimit() {
		LimitBidder bidder = new LimitBidder("andy", 30);
		bidder.onBidAccepted(30);
		bidder.onYourTurnToBid(receiver);
	}

	public void testDoesNotBidWhenCurrentBidExceedsLimit() {
		LimitBidder bidder = new LimitBidder("andy", 30);
		bidder.onBidAccepted(31);
		bidder.onYourTurnToBid(receiver);
	}

	public void testName() {
		LimitBidder bidder = new LimitBidder("george", 30);
		assertThat(bidder.getName(), is("george"));
	}
}
