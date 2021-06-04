package ch.bbzw.auctionhouse.dto;

import ch.bbzw.auctionhouse.model.Auction;
import ch.bbzw.auctionhouse.model.Bid;

import java.util.List;

public class AuctionWithBids {
    private Auction auction;
    private List<Bid> bids;

    public AuctionWithBids(final Auction auction, final List<Bid> bids) {
        this.auction = auction;
        this.bids = bids;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }
}
