package ch.bbzw.auctionhouse.repo;

import ch.bbzw.auctionhouse.model.Auction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepo extends CrudRepository<Auction, Long> {

}
