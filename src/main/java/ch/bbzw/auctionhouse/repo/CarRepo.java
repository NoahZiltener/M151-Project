package ch.bbzw.auctionhouse.repo;

import ch.bbzw.auctionhouse.model.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepo extends CrudRepository<Car, Long> {
}
