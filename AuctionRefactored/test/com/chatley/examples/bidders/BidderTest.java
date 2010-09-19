package com.chatley.examples.bidders;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

import com.chatley.examples.auction.BidReceiver;

public class BidderTest extends MockObjectTestCase {

	private BidReceiver receiver = mock(BidReceiver.class);
	private BidStrategy nextBidStrategy = mock(BidStrategy.class);

	public void testBidsImmediatelyWithStartingBidIfNooneHasBid() {
		checking(new Expectations() {{
			allowing(nextBidStrategy).nextBid(with(any(BidHistory.class))); will(returnValue(30));
			one(receiver).bid(30);
		}});
		Bidder bidder = new Bidder("bert", nextBidStrategy);
		bidder.onYourTurnToBid(receiver);
	}

	public void testRelaysNextBidToTheReceiver() {
		checking(new Expectations() {{
			one(nextBidStrategy).nextBid(with(any(BidHistory.class))); will(returnValue(37));
			one(receiver).bid(37);
		}});
		Bidder bidder = new Bidder("bert", nextBidStrategy);
		bidder.onBidAccepted(32);
		bidder.onYourTurnToBid(receiver);
	}

	public void testStartsWithAnEmptyBidHistory() {
		checking(new Expectations() {{
			one(nextBidStrategy).nextBid(with(emptyBidHistory())); will(returnValue(0));
			ignoring(receiver);
		}});
		Bidder bidder = new Bidder("bert", nextBidStrategy);
		bidder.onYourTurnToBid(receiver);
	}

	public void testUpdatesBidHistoryAfterBidsAreAccepted() {
		checking(new Expectations() {{
			one(nextBidStrategy).nextBid(with(bidHistory(3, 30))); will(returnValue(0));
			ignoring(receiver);
		}});
		Bidder bidder = new Bidder("bert", nextBidStrategy);
		bidder.onBidAccepted(10);
		bidder.onBidAccepted(20);
		bidder.onBidAccepted(30);
		bidder.onYourTurnToBid(receiver);
	}

	protected Matcher<BidHistory> emptyBidHistory() {
		return bidHistory(0, 0);
	}

	protected Matcher<BidHistory> bidHistory(final int numberOfBids, final int leadingBid) {
		return new TypeSafeMatcher<BidHistory> () {

			public boolean matchesSafely(BidHistory item) {
				return item.numberOfBids() == numberOfBids && item.leadingBid() == leadingBid;
			}

			public void describeTo(Description description) {
				description.appendText("leading Bid: " + leadingBid + ", numberOfBids: " + numberOfBids);
			}
			
		};
	}

	public void testName() {
		Bidder bidder = new Bidder("bert", nextBidStrategy);
		assertThat(bidder.getName(), is("bert"));
	}
}
