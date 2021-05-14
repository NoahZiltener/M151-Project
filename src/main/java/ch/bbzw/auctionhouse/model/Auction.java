package ch.bbzw.auctionhouse.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auction_sequence")
    @SequenceGenerator(allocationSize = 1, name = "auction_sequence")
    @Column(nullable = false, updatable = false)
    private long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Price price;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Car car;

    @ManyToOne
    private User auctioneer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DirectBuy directBuy;

    @Column(nullable = false, unique = true)
    private boolean closed;

    @Column(nullable = false)
    private LocalDateTime auctionTime;

    protected Auction() {
    }

    public Auction(final Price price, final Car car, final User auctioneer, final DirectBuy directBuy, final boolean closed, final LocalDateTime auctionTime) {
        this.price = price;
        this.car = car;
        this.auctioneer = auctioneer;
        this.directBuy = directBuy;
        this.closed = closed;
        this.auctionTime = auctionTime;
    }

    public long getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }

    public Car getCar() {
        return car;
    }

    public User getAuctioneer() {
        return auctioneer;
    }

    public DirectBuy getDirectBuy() {
        return directBuy;
    }

    public boolean isClosed() {
        return closed;
    }

    public LocalDateTime getAuctionTime() {
        return auctionTime;
    }
}
