package ch.bbzw.auctionhouse.service;

import ch.bbzw.auctionhouse.dto.AuctionWithPriceAndCar;
import ch.bbzw.auctionhouse.model.*;
import ch.bbzw.auctionhouse.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
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
    public Bid add(final Bid bid, final long auctionId) {
        final Optional<Auction> auction = auctionRepo.findById(auctionId);
        if(auction.isPresent()){
            final User user = userService.getCurrentUser().get();
            bid.setAuction(auction.get());
            bid.setBidder(user);
            return bidRepo.save(bid);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Bid> getAll() {
        final User user = userService.getCurrentUser().get();
        final Iterable<Bid> bids = bidRepo.findByBidder(user);
        return StreamSupport
                .stream(bids.spliterator(), false)
                .collect(Collectors.toList());
    }
}
