package ch.bbzw.auctionhouse.dto;

import ch.bbzw.auctionhouse.model.Auction;

import java.io.Serializable;

public class AuctionWithHighestBid implements Serializable {
    private static final long serialVersionUID = 1L;
    private Auction auction;
    private double highesBid;

    public AuctionWithHighestBid(final Auction auction, final double highesBid) {
        this.auction = auction;
        this.highesBid = highesBid;
    }

    public void setHighesBid(double highesBid) {
        this.highesBid = highesBid;
    }

    public Auction getAuction() {
        return auction;
    }

    public double getHighesBid() {
        return highesBid;
    }

}
