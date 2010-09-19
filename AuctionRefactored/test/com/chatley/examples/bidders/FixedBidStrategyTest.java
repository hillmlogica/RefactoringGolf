package com.chatley.examples.bidders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.jmock.integration.junit3.MockObjectTestCase;

public class FixedBidStrategyTest extends MockObjectTestCase {

	private FixedBidStrategy fixedBidStrategy = new FixedBidStrategy(10);
	private BidHistory bidHistory = mock(BidHistory.class);

	public void testBidsWithTheFixedBidWhenCurrentBidIsLess() {
		assertThat(fixedBidStrategy.nextBid(bidHistory), is(10));
	}

	public void testBidsWithTheFixedBidWhenCurrentBidIsMore() {
		assertThat(fixedBidStrategy.nextBid(bidHistory), is(10));
	}

}
