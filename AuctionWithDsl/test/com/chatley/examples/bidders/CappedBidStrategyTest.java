package com.chatley.examples.bidders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

public class CappedBidStrategyTest extends MockObjectTestCase {
	private BidStrategy underlying = mock(BidStrategy.class);
	private BidHistory bidHistory = mock(BidHistory.class);
	private CappedBidStrategy cappedBidStrategy = new CappedBidStrategy(underlying, 1000);
	
	public void testUsesNextBidFromUnderlyingStrategyIfLessThanCap() {
		checking(new Expectations() {{
			allowing(underlying).nextBid(bidHistory); will(returnValue(500));
		}});
		
		assertThat(cappedBidStrategy.nextBid(bidHistory), is(500));
	}

	public void testBidsZeroWhenNextBidIsTooHigh() {
		checking(new Expectations() {{
			allowing(underlying).nextBid(bidHistory); will(returnValue(1001));
		}});
		
		assertThat(cappedBidStrategy.nextBid(bidHistory), is(0));
	}

}
