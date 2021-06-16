package ch.bbzw.auctionhouse.repo;

import ch.bbzw.auctionhouse.model.Auction;
import ch.bbzw.auctionhouse.model.Bid;
import ch.bbzw.auctionhouse.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepo extends CrudRepository<Bid, Long> {
    List<Bid> findByBidder(User bidder);

    List<Bid> findByAuction(Auction auction);

    Optional<Bid> findFirstByAuctionOrderByBidDesc(Auction auction);
}
