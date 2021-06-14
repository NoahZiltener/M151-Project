package ch.bbzw.auctionhouse.controller;

import ch.bbzw.auctionhouse.dto.AuctionWithBids;
import ch.bbzw.auctionhouse.dto.AuctionWithHighestBid;
import ch.bbzw.auctionhouse.dto.AuctionWithPriceAndCar;
import ch.bbzw.auctionhouse.model.Auction;
import ch.bbzw.auctionhouse.model.User;
import ch.bbzw.auctionhouse.service.AuctionService;
import ch.bbzw.auctionhouse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public List<AuctionWithHighestBid> getAllOpen() {
        return auctionService.getAllOpen(); }

    @GetMapping("/{id}")
    public Optional<Auction> getAuctionById(@PathVariable final long id) {
        return auctionService.getAuctionById(id); }

    @GetMapping("/myAuctions")
    public List<Auction> getMyAuction() {
        return auctionService.getMyAuctions(); }

    @GetMapping("/history")
    public List<Auction> getAllClosed() {
        return auctionService.getAllClosed(); }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('AUCTIONEER') or hasAuthority('ADMIN')")
    public Auction add(@RequestBody final AuctionWithPriceAndCar auctionWithPriceAndCar) {
        return auctionService.add(auctionWithPriceAndCar);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable final long id) {
        auctionService.delete(id);
    }
}
