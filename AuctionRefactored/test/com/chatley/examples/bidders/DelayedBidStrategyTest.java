package com.chatley.examples.bidders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

public class DelayedBidStrategyTest extends MockObjectTestCase {

	private BidStrategy underlying = mock(BidStrategy.class);
	private BidHistory bidHistory = mock(BidHistory.class);
	private DelayedBidStrategy delayedBidStrategy = new DelayedBidStrategy(underlying, 2);
	
	public void testWillBidZeroIfWaitingForSomeBids() {
		checking(new Expectations() {{
			allowing(bidHistory).numberOfBids(); will(returnValue(1));
		}});
		assertThat(delayedBidStrategy.nextBid(bidHistory), is(0));
	}

	public void testWillBidUnderlyingBidAfterNBidsIfWaitingForNBids() {
		checking(new Expectations() {{
			allowing(bidHistory).numberOfBids(); will(returnValue(2));
			allowing(underlying).nextBid(bidHistory); will(returnValue(100));
		}});
		assertThat(delayedBidStrategy.nextBid(bidHistory), is(100));
	}

	public void testWillBidUnderlyingBidAfterGreaterThanNBidsIfWaitingForNBids() {
		checking(new Expectations() {{
			allowing(bidHistory).numberOfBids(); will(returnValue(4));
			allowing(underlying).nextBid(bidHistory); will(returnValue(100));
		}});
		assertThat(delayedBidStrategy.nextBid(bidHistory), is(100));
	}
}
