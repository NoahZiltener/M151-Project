package ch.bbzw.auctionhouse.service;

import ch.bbzw.auctionhouse.dto.AuctionWithPriceAndCar;
import ch.bbzw.auctionhouse.model.Auction;
import ch.bbzw.auctionhouse.model.Car;
import ch.bbzw.auctionhouse.model.Price;
import ch.bbzw.auctionhouse.model.User;
import ch.bbzw.auctionhouse.repo.AuctionRepo;
import ch.bbzw.auctionhouse.repo.CarRepo;
import ch.bbzw.auctionhouse.repo.PriceRepo;
import ch.bbzw.auctionhouse.repo.UserRepo;
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
public class AuctionService {
    private final AuctionRepo auctionRepo;
    private final UserRepo userRepo;
    private final CarRepo carRepo;
    private final PriceRepo priceRepo;
    private final UserService userService;

    @Autowired
    public AuctionService(final AuctionRepo auctionRepo, final UserRepo userRepo, final CarRepo carRepo, final PriceRepo priceRepo, final UserService userService) {
        this.auctionRepo = auctionRepo;
        this.userRepo = userRepo;
        this.carRepo = carRepo;
        this.priceRepo = priceRepo;
        this.userService = userService;
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
    public List<Auction> getAll() {
        final Iterable<Auction> auctions = auctionRepo.findAll();
        return StreamSupport
                .stream(auctions.spliterator(), false)
                .collect(Collectors.toList());
    }


}
