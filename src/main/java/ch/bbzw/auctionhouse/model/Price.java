package ch.bbzw.auctionhouse.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Price implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_sequence")
    @SequenceGenerator(allocationSize = 1, name = "price_sequence")
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(nullable = false)
    private float startingBid;

    @Column(nullable = false)
    private float directPrice;

    protected Price() {
    }

    public Price(final float startingBid, final float directPrice) {
        this.startingBid = startingBid;
        this.directPrice = directPrice;
    }

    public long getId() {
        return id;
    }

    public float getStartingBid() {
        return startingBid;
    }

    public float getDirectPrice() {
        return directPrice;
    }
}
