package ch.bbzw.auctionhouse.service;

import ch.bbzw.auctionhouse.dto.AuctionWithHighestBid;
import ch.bbzw.auctionhouse.dto.AuctionWithPriceAndCar;
import ch.bbzw.auctionhouse.model.*;
import ch.bbzw.auctionhouse.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@CacheConfig(cacheNames = {"auctions"})
public class AuctionService {
    private final AuctionRepo auctionRepo;
    private final CarRepo carRepo;
    private final PriceRepo priceRepo;
    private final UserService userService;
    private final BidService bidService;


    @Autowired
    public AuctionService(final AuctionRepo auctionRepo, final CarRepo carRepo, final PriceRepo priceRepo, final UserService userService, final BidService bidService) {
        this.auctionRepo = auctionRepo;
        this.carRepo = carRepo;
        this.priceRepo = priceRepo;
        this.userService = userService;
        this.bidService = bidService;
    }

    @Transactional
    @CachePut(key = "#result.id")
    @Caching(evict = {@CacheEvict(key = "0"), @CacheEvict(key = "1"), @CacheEvict(key = "2"), @CacheEvict(key = "3"), @CacheEvict(key = "4")})
    public Auction add(final AuctionWithPriceAndCar auctionWithPriceAndCar) {
        final Optional<User> optionalUser = userService.getCurrentUser();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Car car = carRepo.save(auctionWithPriceAndCar.getCar());
            Price price = priceRepo.save(auctionWithPriceAndCar.getPrice());

            Auction auction = new Auction(price, car, user, null, false, LocalDateTime.now().plusMinutes(5));

            return auctionRepo.save(auction);
        }
        return null;
    }

    @Transactional
    @Caching(evict = {@CacheEvict(key = "#id"), @CacheEvict(key = "0"), @CacheEvict(key = "1"), @CacheEvict(key = "2"), @CacheEvict(key = "3")})
    public void delete(final long id) {
        auctionRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "0")
    public List<AuctionWithHighestBid> getAllOpen() {
        final Iterable<Auction> auctions = auctionRepo.getOpenAuctions();
        List<AuctionWithHighestBid> auctionsWithHighestBid = new ArrayList<>();
        for (Auction auction: auctions) {
            final Optional<Bid> highestBid = bidService.getHighestBid(auction);
            AuctionWithHighestBid auctionWithBid;
            if(highestBid.isPresent()){
                auctionWithBid = new AuctionWithHighestBid(auction,highestBid.get());
            }
            else {
                auctionWithBid = new AuctionWithHighestBid(auction, null);
            }
            auctionsWithHighestBid.add(auctionWithBid);
        }
        return StreamSupport
                .stream(auctionsWithHighestBid.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "-1")
    public List<Auction> getAllClosed() {
        final Iterable<Auction> auctions = auctionRepo.getClosedAuctions();
        return StreamSupport
                .stream(auctions.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "-2")
    public List<Auction> getMyAuctions() {
        final User auctioneer = userService.getCurrentUser().get();
        final Iterable<Auction> auctions = auctionRepo.findByAuctioneer(auctioneer);
        return StreamSupport
                .stream(auctions.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "-3")
    public List<Auction> getWonAuctions() {
        final User winner = userService.getCurrentUser().get();
        final Iterable<Auction> auctions = auctionRepo.findByWinner(winner);
        return StreamSupport
                .stream(auctions.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "#id", unless = "#result == null")
    public Optional<Auction> getAuctionById(final long id) {
        return auctionRepo.findById(id);    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    @Caching(evict = {@CacheEvict(key = "0"), @CacheEvict(key = "1"), @CacheEvict(key = "2"), @CacheEvict(key = "3")})
    public void closeAuctions(){
        final List<Auction> auctions = auctionRepo.getAllExpiredAuctions();
        final List<Auction> closedAuctions = auctions.stream().map(auction -> {
            auction.setClosed(true);
            Optional<Bid> optionalHighestBid = bidService.getHighestBid(auction);
            if(optionalHighestBid.isPresent()){
                Bid highestBid = optionalHighestBid.get();
                auction.setWinner(highestBid.getBidder());
            }
            return auction;
        }).collect(Collectors.toList());
        auctionRepo.saveAll(closedAuctions);
    }
}
