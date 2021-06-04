package ch.bbzw.auctionhouse.model;

import javax.persistence.*;

@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bid_sequence")
    @SequenceGenerator(allocationSize = 1, name = "bid_sequence")
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(nullable = false, unique = false)
    private float bid;

    @ManyToOne
    private User bidder;

    @ManyToOne
    private Auction auction;

    protected Bid() {
    }

    public Bid(final float bid, final User bidder, final Auction auction) {
        this.bid = bid;
        this.bidder = bidder;
        this.auction = auction;
    }

    public void setBid(float bid) {
        this.bid = bid;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public long getId() {
        return id;
    }

    public float getBid() {
        return bid;
    }

    public User getBidder() {
        return bidder;
    }

    public Auction getAuction() {
        return auction;
    }
}
