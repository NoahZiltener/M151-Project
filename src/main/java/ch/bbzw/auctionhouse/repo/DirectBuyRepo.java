package ch.bbzw.auctionhouse.repo;

import ch.bbzw.auctionhouse.model.DirectBuy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectBuyRepo extends CrudRepository<DirectBuy, Long> {
}
