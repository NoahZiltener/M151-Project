package ch.bbzw.auctionhouse.service;

import ch.bbzw.auctionhouse.exception.CustomException;
import ch.bbzw.auctionhouse.model.Auction;
import ch.bbzw.auctionhouse.model.Bid;
import ch.bbzw.auctionhouse.model.User;
import ch.bbzw.auctionhouse.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@CacheConfig(cacheNames = {"bids"})
public class BidService {
    private final AuctionRepo auctionRepo;
    private final UserRepo userRepo;
    private final CarRepo carRepo;
    private final PriceRepo priceRepo;
    private final BidRepo bidRepo;
    private final UserService userService;

    @Autowired
    public BidService(final AuctionRepo auctionRepo, final UserRepo userRepo, final CarRepo carRepo, final PriceRepo priceRepo, final BidRepo bidRepo, final UserService userService) {
        this.auctionRepo = auctionRepo;
        this.userRepo = userRepo;
        this.carRepo = carRepo;
        this.priceRepo = priceRepo;
        this.bidRepo = bidRepo;
        this.userService = userService;
    }

    @Transactional
    @Cacheable(key = "#bid.id", unless = "#result == null")
    @Caching(evict = {
            @CacheEvict(key = "0"),
            @CacheEvict(cacheNames = "auctions", key = "0"),
            @CacheEvict(cacheNames = "auctions", key = "1"),
            @CacheEvict(cacheNames = "auctions", key = "2"),
            @CacheEvict(cacheNames = "auctions", key = "3")})
    public Bid add(final Bid bid, final long auctionId) throws CustomException {
        final Optional<Auction> optionalAuction = auctionRepo.findById(auctionId);
        if (optionalAuction.isPresent()) {
            final Auction auction = optionalAuction.get();
            if (!auction.isClosed()) {
                Optional<Bid> optionalHighestBid = bidRepo.findFirstByAuctionOrderByBidDesc(auction);
                if(optionalHighestBid.isPresent()){
                    Bid highestBid = optionalHighestBid.get();
                    if(highestBid.getBid() >= bid.getBid()){
                        throw new CustomException("Bid is to lower then highest bid");
                    }
                }
                if(auction.getPrice().getStartingBid() <= bid.getBid()){
                    final User user = userService.getCurrentUser();
                    bid.setAuction(auction);
                    bid.setBidder(user);
                    return bidRepo.save(bid);
                }
                else {
                    throw new CustomException("Bid is to lower then start bid");
                }
            }
            else {
                throw new CustomException("Auction is Closed");
            }
        }
        else {
            throw new CustomException("Auction not Found");
        }
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "0")
    public List<Bid> getAll() {
        final User user = userService.getCurrentUser();
        final Iterable<Bid> bids = bidRepo.findByBidder(user);
        return StreamSupport
                .stream(bids.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<Bid> getHighestBid(Auction auction) {
        return bidRepo.findFirstByAuctionOrderByBidDesc(auction);
    }
}
