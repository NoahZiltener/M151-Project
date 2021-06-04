package ch.bbzw.auctionhouse.repo;

import ch.bbzw.auctionhouse.model.Bid;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepo extends CrudRepository<Bid, Long> {

}
