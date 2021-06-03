package ch.bbzw.auctionhouse.repo;

import ch.bbzw.auctionhouse.model.Price;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepo extends CrudRepository<Price, Long> {
}
