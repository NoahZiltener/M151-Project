package ch.bbzw.auctionhouse.dto;

import ch.bbzw.auctionhouse.model.Auction;
import ch.bbzw.auctionhouse.model.Bid;

import java.io.Serializable;

public class AuctionWithHighestBid implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Auction auction;
    private final Bid highesBid;

    public Auction getAuction() {
        return auction;
    }

    public Bid getHighesBid() {
        return highesBid;
    }

    public AuctionWithHighestBid(final Auction auction, final Bid highesBid) {
        this.auction = auction;
        this.highesBid = highesBid;
    }
}
