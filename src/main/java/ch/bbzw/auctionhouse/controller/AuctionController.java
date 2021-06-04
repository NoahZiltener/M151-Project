package ch.bbzw.auctionhouse.controller;

import ch.bbzw.auctionhouse.dto.AuctionWithPriceAndCar;
import ch.bbzw.auctionhouse.model.Auction;
import ch.bbzw.auctionhouse.model.User;
import ch.bbzw.auctionhouse.service.AuctionService;
import ch.bbzw.auctionhouse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/auction")
@PreAuthorize("hasAuthority('PURCHASER') or hasAuthority('ADMIN') or hasAuthority('AUCTIONEER')")
public class AuctionController {
    private final AuctionService auctionService;

    @Autowired
    public AuctionController(final AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/")
    public List<Auction> getAll() {
        return auctionService.getAll(); }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('AUCTIONEER')")
    public Auction add(@RequestBody final AuctionWithPriceAndCar auctionWithPriceAndCar) {
        return auctionService.add(auctionWithPriceAndCar);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable final long id) {
        auctionService.delete(id);
    }
}
