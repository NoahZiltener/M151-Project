package ch.bbzw.auctionhouse.service;

import ch.bbzw.auctionhouse.model.Auction;
import ch.bbzw.auctionhouse.model.DirectBuy;
import ch.bbzw.auctionhouse.model.User;
import ch.bbzw.auctionhouse.repo.AuctionRepo;
import ch.bbzw.auctionhouse.repo.DirectBuyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"direct"})
public class DirectBuyService {
    private final DirectBuyRepo directBuyRepo;
    private final AuctionRepo auctionRepo;
    private final UserService userService;

    @Autowired
    public DirectBuyService(final DirectBuyRepo directBuyRepo, final AuctionRepo auctionRepo, final UserService userService) {
        this.directBuyRepo = directBuyRepo;
        this.auctionRepo = auctionRepo;
        this.userService = userService;
    }

    @Transactional
    @CachePut(key = "#directBuy.id")
    @CacheEvict(key = "0")
    public DirectBuy add(final long auctionId) {
        final Optional<Auction> optionalAuction = auctionRepo.findById(auctionId);
        if (optionalAuction.isPresent()) {
            final Auction auction = optionalAuction.get();
            if (!auction.isClosed()) {
                final User user = userService.getCurrentUser().get();
                DirectBuy directBuy = new DirectBuy(user);
                final DirectBuy savedDirectBuy = directBuyRepo.save(directBuy);
                auction.setDirectBuy(savedDirectBuy);
                auction.setClosed(true);
                auction.setWinner(user);
                auctionRepo.save(auction);
                return savedDirectBuy;
            }
        }
        return null;
    }

}
