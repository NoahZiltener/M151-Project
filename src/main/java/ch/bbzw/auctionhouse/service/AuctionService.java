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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@CacheConfig(cacheNames = {"auction"})
public class AuctionService {
    private final AuctionRepo auctionRepo;
    private final UserRepo userRepo;
    private final CarRepo carRepo;
    private final PriceRepo priceRepo;
    private final UserService userService;
    private final BidRepo bidRepo;


    @Autowired
    public AuctionService(final AuctionRepo auctionRepo, final UserRepo userRepo, final CarRepo carRepo, final PriceRepo priceRepo, final UserService userService, final BidRepo bidRepo) {
        this.auctionRepo = auctionRepo;
        this.userRepo = userRepo;
        this.carRepo = carRepo;
        this.priceRepo = priceRepo;
        this.userService = userService;
        this.bidRepo = bidRepo;
    }

    @Transactional
    //@CachePut(key = "#auctionWithPriceAndCar.")
    //@CacheEvict(key = "0")
    public Auction add(final AuctionWithPriceAndCar auctionWithPriceAndCar) {
        final Optional<User> optionalUser = userService.getCurrentUser();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Car car = carRepo.save(auctionWithPriceAndCar.getCar());
            Price price = priceRepo.save(auctionWithPriceAndCar.getPrice());

            Auction auction = new Auction(price, car, user, null, false, LocalDateTime.now().plusMinutes(1));

            return auctionRepo.save(auction);
        }
        return null;
    }

    @Transactional
    @Caching(evict = {@CacheEvict(key = "#id"), @CacheEvict(key = "0")})
    public void delete(final long id) {
        auctionRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "0")
    public List<AuctionWithHighestBid> getAllOpen() {
        final Iterable<Auction> auctions = auctionRepo.getOpenAuctions();
        List<AuctionWithHighestBid> auctionsWithHighestBid = new ArrayList<>();
        for (Auction auction: auctions) {
            final List<Bid> bids = bidRepo.findByAuction(auction);
            final Optional<Bid> highestBid = getHighestBid(bids);
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
    public List<Auction> getAllClosed() {
        final Iterable<Auction> auctions = auctionRepo.getClosedAuctions();
        return StreamSupport
                .stream(auctions.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Auction> getMyAuctions() {
        final User auctioneer = userService.getCurrentUser().get();
        final Iterable<Auction> auctions = auctionRepo.findByAuctioneer(auctioneer);
        return StreamSupport
                .stream(auctions.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Auction> getAuctionById(final long id) {
        return auctionRepo.findById(id);    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void closeAuctions(){
        final List<Auction> auctions = auctionRepo.getAllExpiredAuctions();
        final List<Auction> closedAuctions = auctions.stream().map(auction -> {
            auction.setClosed(true);
            return auction;
        }).collect(Collectors.toList());
        auctionRepo.saveAll(closedAuctions);
    }

    public Optional<Bid> getHighestBid(List<Bid> bids) {
        return bids.stream()
                .max(Comparator.comparing(Bid::getBid));
    }
}
