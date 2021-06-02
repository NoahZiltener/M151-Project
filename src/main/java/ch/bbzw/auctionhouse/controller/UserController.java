package ch.bbzw.auctionhouse.controller;

import ch.bbzw.auctionhouse.model.User;
import ch.bbzw.auctionhouse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
@PreAuthorize("hasAuthority('PURCHASER') or hasAuthority('ADMIN') or hasAuthority('AUCTIONEER')")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
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
        return userService.getAll(); }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User add(@RequestBody final User user) {
        return userService.add(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable final long id) {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User update(@PathVariable final long id, @RequestBody final User user) {
        return userService.update(id, user).orElse(null);
    }
}
