package com.chatley.examples.bidders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

public class PrimeBidStrategyTest extends MockObjectTestCase {
	
	public void testStartsAtStartingBidWhenNooneHasBid() {
		PrimeBidStrategy primeBidStrategy = new PrimeBidStrategy(5);
		final BidHistory bidHistory = mock(BidHistory.class);
		
		checking(new Expectations() {{
			allowing(bidHistory).numberOfBids(); will(returnValue(0));
		}});
		assertThat(primeBidStrategy.nextBid(bidHistory), is(5));
	}
	
	public void testGivesNextPrimeNumberAboveLeadingBid() {
		assertThat(nextBidGivenLeadingBid(2), is(3));
		assertThat(nextBidGivenLeadingBid(3), is(5));
		assertThat(nextBidGivenLeadingBid(4), is(5));
		assertThat(nextBidGivenLeadingBid(5), is(7));
		assertThat(nextBidGivenLeadingBid(6), is(7));
		assertThat(nextBidGivenLeadingBid(24), is(29));
	}

	private int nextBidGivenLeadingBid(final int leadingBid) {
		PrimeBidStrategy primeBidStrategy = new PrimeBidStrategy(1);
		final BidHistory bidHistory = mock(BidHistory.class);
		checking(new Expectations() {{
			allowing(bidHistory).numberOfBids(); will(returnValue(1));
			allowing(bidHistory).leadingBid(); will(returnValue(leadingBid));
		}});
		return primeBidStrategy.nextBid(bidHistory);
	}


}
