package ch.bbzw.auctionhouse.model;

import javax.persistence.*;

@Entity
public class DirectBuy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(allocationSize = 1, name = "user_sequence")
    @Column(nullable = false, updatable = false)
    private long id;

    @ManyToOne
    private User buyer;

    protected DirectBuy() {
    }

    public DirectBuy(final User buyer) {
        this.buyer = buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public long getId() {
        return id;
    }

    public User getBuyer() {
        return buyer;
    }
}
