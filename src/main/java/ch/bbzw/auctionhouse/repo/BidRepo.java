package ch.bbzw.auctionhouse.repo;

import ch.bbzw.auctionhouse.model.Auction;
import ch.bbzw.auctionhouse.model.Bid;
import ch.bbzw.auctionhouse.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepo extends CrudRepository<Bid, Long> {
    List<Bid> findByBidder(User bidder);
    List<Bid> findByAuction(Auction auction);
}
