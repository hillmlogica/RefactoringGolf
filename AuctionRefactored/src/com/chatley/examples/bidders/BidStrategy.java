/**
 * 
 */
package com.chatley.examples.bidders;

public interface BidStrategy {
	int nextBid(BidHistory history);
}