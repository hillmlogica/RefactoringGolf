package com.chatley.examples.bidders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

public class IncrementalBidStrategyTest extends MockObjectTestCase {

	private IncrementalBidStrategy incrementalBidStrategy = new IncrementalBidStrategy(30, 5);
	private BidHistory bidHistory = mock(BidHistory.class);

	public void testBidsImmediatelyWithStartingBidIfNooneHasBid() {
		checking(new Expectations() {{
			allowing(bidHistory).numberOfBids(); will(returnValue(0));
		}});
		assertThat(incrementalBidStrategy.nextBid(bidHistory), is(30));
	}

	public void testBidsAtCurrentBidPlusIncrementIfSomeoneHasBid() {
		checking(new Expectations() {{
			allowing(bidHistory).numberOfBids(); will(returnValue(3));
			allowing(bidHistory).leadingBid(); will(returnValue(32));
		}});
		assertThat(incrementalBidStrategy.nextBid(bidHistory), is(37));
	}

}
