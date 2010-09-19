package com.chatley.examples.bidders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

public class LimitBidStrategyTest extends MockObjectTestCase {
	private LimitBidStrategy limitBidStrategy = new LimitBidStrategy(30);
	private BidHistory bidHistory = mock(BidHistory.class);
	
	public void testBidsImmediatelyAtHalfHisLimitIfNoOneElseHasBid() {
		checking(new Expectations() {{
			allowing(bidHistory).leadingBid(); will(returnValue(0));
		}});
		assertThat(limitBidStrategy.nextBid(bidHistory), is(15));
	}

	public void testBidsHalfwayBetweenCurrentBidAndLimit() {
		checking(new Expectations() {{
			allowing(bidHistory).leadingBid(); will(returnValue(20));
		}});
		assertThat(limitBidStrategy.nextBid(bidHistory), is(25));
	}

	public void testBidsAtLimitWhenCurrentBidReachesLimitMinusOne() {
		checking(new Expectations() {{
			allowing(bidHistory).leadingBid(); will(returnValue(29));
		}});
		assertThat(limitBidStrategy.nextBid(bidHistory), is(30));
	}

	public void testBidsAtLimitWhenCurrentBidReachesLimit() {
		checking(new Expectations() {{
			allowing(bidHistory).leadingBid(); will(returnValue(30));
		}});
		assertThat(limitBidStrategy.nextBid(bidHistory), is(30));
	}

	public void testBidsAtLimitWhenCurrentBidExceedsLimit() {
		checking(new Expectations() {{
			allowing(bidHistory).leadingBid(); will(returnValue(40));
		}});
		assertThat(limitBidStrategy.nextBid(bidHistory), is(30));
	}

	

}
