package ch.bbzw.auctionhouse.model;

import javax.persistence.*;

@Entity
public class DirectBuy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(allocationSize = 1, name = "user_sequence")
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(nullable = false)
    private float buyPrice;

    @ManyToOne
    private User buyer;

    protected DirectBuy() {
    }

    public DirectBuy(final float buyPrice, final User buyer) {
        this.buyPrice = buyPrice;
        this.buyer = buyer;
    }

    public long getId() {
        return id;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public User getBuyer() {
        return buyer;
    }
}
