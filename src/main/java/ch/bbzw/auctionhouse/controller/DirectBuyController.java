package ch.bbzw.auctionhouse.controller;

import ch.bbzw.auctionhouse.model.Bid;
import ch.bbzw.auctionhouse.model.DirectBuy;
import ch.bbzw.auctionhouse.service.BidService;
import ch.bbzw.auctionhouse.service.DirectBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auction/directbuy/")
@PreAuthorize("hasAuthority('PURCHASER') or hasAuthority('ADMIN') or hasAuthority('AUCTIONEER')")
public class DirectBuyController {
    private final DirectBuyService directBuyService;

    @Autowired
    public DirectBuyController(final DirectBuyService directBuyService) {
        this.directBuyService = directBuyService;
    }

    @PostMapping("/{id}")
    public ResponseEntity add(@PathVariable final long id) {
        try {
            final DirectBuy savedDirectBuy = directBuyService.add(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedDirectBuy);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

}
