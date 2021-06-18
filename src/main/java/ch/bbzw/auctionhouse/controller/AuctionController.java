package ch.bbzw.auctionhouse.controller;

import ch.bbzw.auctionhouse.dto.AuctionWithHighestBid;
import ch.bbzw.auctionhouse.dto.AuctionWithPriceAndCar;
import ch.bbzw.auctionhouse.model.Auction;
import ch.bbzw.auctionhouse.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<AuctionWithHighestBid>> getAllOpen() {
        return ResponseEntity.ok(auctionService.getAllOpen());
    }

    @GetMapping("/myAuctions")
    public ResponseEntity<List<AuctionWithHighestBid>> getMyAuction() {
        return ResponseEntity.ok(auctionService.getMyAuctions());
    }

    @GetMapping("/history")
    public ResponseEntity<List<AuctionWithHighestBid>> getAllClosed() {
        return ResponseEntity.ok(auctionService.getAllClosed());
    }

    @GetMapping("/wonAuctions")
    public ResponseEntity<List<AuctionWithHighestBid>> getAllWonAuctions() {
        return ResponseEntity.ok(auctionService.getWonAuctions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable final long id) {
        final Optional<Auction> optionalAuction = auctionService.getAuctionById(id);
        if (optionalAuction.isPresent()) {
            return ResponseEntity.ok(optionalAuction.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('AUCTIONEER') or hasAuthority('ADMIN')")
    public ResponseEntity add(@RequestBody final AuctionWithPriceAndCar auctionWithPriceAndCar) {
        try {
            final Auction auction = auctionService.add(auctionWithPriceAndCar);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(auction);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable final long id) {
        auctionService.delete(id);
        return ResponseEntity.ok("Auction deleted");
    }
}
