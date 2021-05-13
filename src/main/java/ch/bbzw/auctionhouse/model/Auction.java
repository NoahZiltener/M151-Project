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
    private User Auctioneer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DirectBuy directBuy;

    @Column(nullable = false, unique = true)
    private boolean closed;

    @Column(nullable = false)
    private LocalDateTime auctionTime;
}
