package ch.bbzw.auctionhouse.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_sequence")
    @SequenceGenerator(allocationSize = 1, name = "car_sequence")
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private  String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private Date constructionYear;

    protected Car() {
    }

    public Car(final String name, final String description, final String color, final Date constructionYear) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.constructionYear = constructionYear;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }

    public Date getConstructionYear() {
        return constructionYear;
    }
}
