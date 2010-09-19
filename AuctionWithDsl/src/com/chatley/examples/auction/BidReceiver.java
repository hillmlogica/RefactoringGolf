/**
 * 
 */
package com.chatley.examples.auction;

public interface BidReceiver {
	// true: bid accepted
	// false: bid ignored
	boolean bid(int price);
}