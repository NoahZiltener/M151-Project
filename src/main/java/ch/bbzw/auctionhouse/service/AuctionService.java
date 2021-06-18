package ch.bbzw.auctionhouse.service;

import ch.bbzw.auctionhouse.dto.AuctionWithHighestBid;
import ch.bbzw.auctionhouse.dto.AuctionWithPriceAndCar;
import ch.bbzw.auctionhouse.model.*;
import ch.bbzw.auctionhouse.repo.AuctionRepo;
import ch.bbzw.auctionhouse.repo.CarRepo;
import ch.bbzw.auctionhouse.repo.PriceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {"auctions"})
public class AuctionService {
    private final AuctionRepo auctionRepo;
    private final CarRepo carRepo;
    private final PriceRepo priceRepo;
    private final UserService userService;
    private final BidService bidService;

    @Autowired
    public AuctionService(final AuctionRepo auctionRepo,
                          final CarRepo carRepo,
                          final PriceRepo priceRepo,
                          final UserService userService,
                          final BidService bidService) {
        this.auctionRepo = auctionRepo;
        this.carRepo = carRepo;
        this.priceRepo = priceRepo;
        this.userService = userService;
        this.bidService = bidService;
    }

    @Transactional
    @CachePut(key = "#result.id")
    @Caching(evict = {
            @CacheEvict(key = "0"),
            @CacheEvict(key = "-1"),
            @CacheEvict(key = "-2"),
            @CacheEvict(key = "-3")})
    public Auction add(final AuctionWithPriceAndCar auctionWithPriceAndCar) {
        final User user = userService.getCurrentUser();
        Car car = carRepo.save(auctionWithPriceAndCar.getCar());
        Price price = priceRepo.save(auctionWithPriceAndCar.getPrice());
        Auction auction = new Auction(price, car, user, null, false, LocalDateTime.now().plusMinutes(5));
        return auctionRepo.save(auction);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "0")
    public List<AuctionWithHighestBid> getAllOpen() {
        final List<Auction> auctions = auctionRepo.getOpenAuctions();
        return getAuctionWithHighestBid(auctions);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "-1")
    public List<AuctionWithHighestBid> getAllClosed() {
        final List<Auction> auctions = auctionRepo.getClosedAuctions();
        return getAuctionWithHighestBid(auctions);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "-2")
    public List<AuctionWithHighestBid> getMyAuctions() {
        final User auctioneer = userService.getCurrentUser();
        final List<Auction> auctions = auctionRepo.findByAuctioneer(auctioneer);
        return getAuctionWithHighestBid(auctions);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "-3")
    public List<AuctionWithHighestBid> getWonAuctions() {
        final User winner = userService.getCurrentUser();
        final List<Auction> auctions = auctionRepo.findByWinner(winner);
        return getAuctionWithHighestBid(auctions);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "#id", unless = "#result == null")
    public Optional<Auction> getAuctionById(final long id) {
        return auctionRepo.findById(id);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    @CacheEvict(allEntries = true)
    public void closeAuctions() {
        final List<Auction> auctions = auctionRepo.getAllExpiredAuctions();
        final List<Auction> closedAuctions = auctions.stream().peek(auction -> {
            auction.setClosed(true);
            Optional<Bid> optionalHighestBid = bidService.getHighestBid(auction);
            if (optionalHighestBid.isPresent()) {
                Bid highestBid = optionalHighestBid.get();
                auction.setWinner(highestBid.getBidder());
            }
        }).collect(Collectors.toList());
        auctionRepo.saveAll(closedAuctions);
    }

    @Transactional
    public List<AuctionWithHighestBid> getAuctionWithHighestBid(List<Auction> auctions){
        return auctions.stream().map(auction -> {
            double highestBid = 0;
            final Optional<Bid> optionalBid = bidService.getHighestBid(auction);
            if(optionalBid.isPresent()){
                final Bid bid = optionalBid.get();
                highestBid = bid.getBid();
            }
            return new AuctionWithHighestBid(auction, highestBid);
        }).collect(Collectors.toList());
    }
}
