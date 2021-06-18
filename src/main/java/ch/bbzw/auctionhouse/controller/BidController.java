package ch.bbzw.auctionhouse.controller;

import ch.bbzw.auctionhouse.model.Bid;
import ch.bbzw.auctionhouse.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity add(@RequestBody final Bid bid, @PathVariable final long id) {
        try {
            final Bid savedBid = bidService.add(bid, id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedBid);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Bid>> getAll() {
        return ResponseEntity.ok(bidService.getAll());
    }
}
