package com.chatley.examples.bidders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

public class MultiplierBidStrategyTest extends MockObjectTestCase {

	private MultiplierBidStrategy bidStrategy = new MultiplierBidStrategy(5);
	private BidHistory bidHistory = mock(BidHistory.class);

	public void testReturnsZeroIfNoBidsHaveBeenMade() {
		checking(new Expectations() {{
			allowing(bidHistory).numberOfBids(); will(returnValue(0));
		}});
		assertThat(bidStrategy.nextBid(bidHistory), is(0));
	}

	public void testBidsAtCurrentBidTimesMultiplierIfSomeoneHasBid() {
		checking(new Expectations() {{
			allowing(bidHistory).numberOfBids(); will(returnValue(3));
			allowing(bidHistory).leadingBid(); will(returnValue(32));
		}});
		assertThat(bidStrategy.nextBid(bidHistory), is(160));
	}

}
