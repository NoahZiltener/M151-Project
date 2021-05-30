package ch.bbzw.auctionhouse.controller;

import ch.bbzw.auctionhouse.model.User;
import ch.bbzw.auctionhouse.repo.UserRepo;
import ch.bbzw.auctionhouse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/user")
@PreAuthorize("hasAuthority('PURCHASER') or hasAuthority('ADMIN') or hasAuthority('AUCTIONEER')")
public class UserController {
    private final UserRepo userService;

    @Autowired
    public UserController(final UserRepo userService){
        this.userService = userService;
    }

    @GetMapping("/info")
    public Authentication getInfo() {
        final SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication();
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAll() {
        final Iterable<User> users = userService.findAll();
        return StreamSupport
                .stream(users.spliterator(), false)
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User add(@RequestBody final User user) {
        return userService.save(user);
    }
}
