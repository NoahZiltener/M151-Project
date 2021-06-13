package ch.bbzw.auctionhouse.repo;

import ch.bbzw.auctionhouse.model.Auction;
import ch.bbzw.auctionhouse.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepo extends CrudRepository<Auction, Long> {
    @Query("SELECT a FROM Auction a WHERE a.closed = false")
    List<Auction> getOpenAuctions();

    @Query("SELECT a FROM Auction a WHERE a.closed = true")
    List<Auction> getClosedAuctions();

    @Query("SELECT a FROM Auction a WHERE a.closed = false AND a.auctionTime < CURRENT_TIMESTAMP")
    List<Auction> getAllExpiredAuctions();

    List<Auction> findByAuctioneer(User auctioneer);
}
