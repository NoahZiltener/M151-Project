package ch.bbzw.auctionhouse.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Auction implements Serializable {
    private static final long serialVersionUID = 1L;

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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private DirectBuy directBuy;

    @Column(nullable = false)
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

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setAuctioneer(User auctioneer) {
        this.auctioneer = auctioneer;
    }

    public void setDirectBuy(DirectBuy directBuy) {
        this.directBuy = directBuy;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public void setAuctionTime(LocalDateTime auctionTime) {
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
