package com.chatley.examples.auction;

import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import org.jmock.integration.junit3.MockObjectTestCase;
import org.jmock.lib.action.VoidAction;

public class EnglishAuctionTest extends MockObjectTestCase {
	
	private final AuctionMouthpiece mouthpiece = mock(AuctionMouthpiece.class);
	private final EnglishAuction auction = new EnglishAuction(mouthpiece);
	
	public void testRegistersBiddersToListenToAuctionMouthpiece() throws Exception {
		
		final Participant bidder = mock(Participant.class);

		checking(new Expectations() {{
			one(mouthpiece).addListener(bidder);
		}});

		auction.accept(bidder);
	}
	
	public void testRunsARoundWhenTheAuctionStarts() throws Exception {
	
		checking(new Expectations() {{
			one(mouthpiece).runARound(with(any(Auctioneer.class)));
		}});

		auction.run();
	}
	
	public void testRunsRoundsUntilTheBidsStop() throws Exception {
	
		checking(new Expectations() {{
			one(mouthpiece).onBidAccepted(10);
			one(mouthpiece).onBidAccepted(20);
			exactly(3).of(mouthpiece).runARound(with(any(Auctioneer.class))); 
				will(onConsecutiveCalls(bid(10), bid(20), nobid()));
		}});

		auction.run();
	}

	public void testIsWonByHighestBidder() throws Exception {
		
		final Ledger result = mock(Ledger.class);
		final Participant highBidder = mock(Participant.class);
		final Participant lowBidder = mock(Participant.class);
		final int HIGH_BID = 50;
		
		checking(new Expectations() {{
			ignoring(mouthpiece);
			one(result).win(highBidder, HIGH_BID);
		}});
		
		auction.submitBid(lowBidder, 25);
		auction.submitBid(highBidder, HIGH_BID);
		
		auction.close(result);
	}
	
	
	public void testAllowsBiddersToUpdateTheirBidsUntilAuctionCloses() throws Exception {

		final Ledger result = mock(Ledger.class);
		final Participant bidder1 = mock(Participant.class);
		final Participant bidder2 = mock(Participant.class);
		final int HIGHEST_BID = 75;
		
		checking(new Expectations() {{ 
			ignoring(mouthpiece);
			one(result).win(bidder1, HIGHEST_BID);
		}});
		
		auction.submitBid(bidder1, 25);
		auction.submitBid(bidder2, 50);
		auction.submitBid(bidder1, HIGHEST_BID);
		
		auction.close(result);
	}
	
	public void testHasNoWinnerIfReservePriceNotMet() throws Exception {
		
		auction.withReservePrice(100);
		
		final Ledger result = mock(Ledger.class);
		final Participant lowBidder = mock(Participant.class);
		final Participant highBidder = mock(Participant.class);
		
		checking(new Expectations() {{
			ignoring(mouthpiece);
			one(result).noWinner();
		}});
		
		auction.submitBid(lowBidder, 25);
		auction.submitBid(highBidder, 50);
		
		auction.close(result);
	}
	
	public void testSignalsWhetherBidsWereAcceptedOrNot() throws Exception {
		
		final Participant lowBidder = mock(Participant.class);
		final Participant highBidder = mock(Participant.class);
		
		checking(new Expectations() {{ 
			ignoring(mouthpiece);
		}});
		
		auction.accept(lowBidder);
		auction.accept(highBidder);
		
		assertTrue(auction.submitBid(lowBidder, 25));
		assertTrue(auction.submitBid(highBidder, 50));
		assertFalse(auction.submitBid(lowBidder, 30));
	}
	
	public void testUpdatesAllBiddersWithNewLeadingBids() throws Exception {
		
		final Participant lowBidder = mock(Participant.class);
		final Participant highBidder = mock(Participant.class);
		
		checking(new Expectations() {{ 
			one(mouthpiece).onBidAccepted(25);
			one(mouthpiece).onBidAccepted(50);
		}});
		
		auction.submitBid(lowBidder, 25);
		auction.submitBid(highBidder, 50);
		auction.submitBid(lowBidder, 30);
	}

	private Action bid(final int bid) {
		return new Action() {

			public Object invoke(Invocation invocation) throws Throwable {
				((Auctioneer) invocation.getParameter(0)).submitBid(dummyBidder(), bid);
				return null;
			}

			public void describeTo(Description description) {
				description.appendText("dummy bidder bidding ").appendValue(bid);
			}
			
		};
	}

	private Action nobid() {
		return VoidAction.INSTANCE;
	}

	private Participant dummyBidder() {
		return mock(Participant.class);
	}

}
