package ch.bbzw.auctionhouse.controller;

import ch.bbzw.auctionhouse.dto.AuctionWithPriceAndCar;
import ch.bbzw.auctionhouse.model.Auction;
import ch.bbzw.auctionhouse.model.Bid;
import ch.bbzw.auctionhouse.service.AuctionService;
import ch.bbzw.auctionhouse.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/auction/bid/")
@PreAuthorize("hasAuthority('PURCHASER') or hasAuthority('ADMIN') or hasAuthority('AUCTIONEER')")
public class BidController {
    private final BidService bidService;

    @Autowired
    public BidController(final BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping("/{id}")
    public Bid add(@RequestBody final Bid bid, @PathVariable final long id) {
        return bidService.add(bid, id);
    }

    @GetMapping("/")
    public List<Bid> getAll() {
        return bidService.getAll(); }
}
