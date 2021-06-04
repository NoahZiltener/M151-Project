package ch.bbzw.auctionhouse.service;

import ch.bbzw.auctionhouse.dto.AuctionWithBids;
import ch.bbzw.auctionhouse.dto.AuctionWithHighestBid;
import ch.bbzw.auctionhouse.dto.AuctionWithPriceAndCar;
import ch.bbzw.auctionhouse.model.*;
import ch.bbzw.auctionhouse.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Auction add(final AuctionWithPriceAndCar auctionWithPriceAndCar) {
        final Optional<User> optionalUser = userService.getCurrentUser();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Car car = carRepo.save(auctionWithPriceAndCar.getCar());
            Price price = priceRepo.save(auctionWithPriceAndCar.getPrice());

            Auction auction = new Auction(price, car, user, null, false, LocalDateTime.now());

            return auctionRepo.save(auction);
        }
        return null;
    }

    @Transactional
    public void delete(final long id) {
        auctionRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
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

    public Optional<Bid> getHighestBid(List<Bid> bids) {
        return bids.stream()
                .max(Comparator.comparing(Bid::getBid));
    }
}
