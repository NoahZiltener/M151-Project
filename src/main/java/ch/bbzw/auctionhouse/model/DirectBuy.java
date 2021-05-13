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
}
