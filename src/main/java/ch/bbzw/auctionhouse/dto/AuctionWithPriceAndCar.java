package ch.bbzw.auctionhouse.dto;

import ch.bbzw.auctionhouse.model.Car;
import ch.bbzw.auctionhouse.model.Price;

public class AuctionWithPriceAndCar {
    private final Car car;
    private final Price price;

    public AuctionWithPriceAndCar(final Car car, final Price price) {
        this.car = car;
        this.price = price;
    }

    public Car getCar() {
        return car;
    }

    public Price getPrice() {
        return price;
    }
}
