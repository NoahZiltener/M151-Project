package ch.bbzw.auctionhouse.service;

import ch.bbzw.auctionhouse.model.User;
import ch.bbzw.auctionhouse.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@CacheConfig(cacheNames = {"users"})
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(final UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Transactional
    @CachePut(key = "#user.id")
    @CacheEvict(key = "0")
    public User add(final User user) {
        return userRepo.save(user);
    }

    @Transactional
    @Caching(evict = {@CacheEvict(key = "#id"), @CacheEvict(key = "0")})
    public void delete(final long id) {
        final Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();
            foundUser.setDeleted(true);
            Optional.of(userRepo.save(foundUser));
        }
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "0")
    public List<User> getAll() {
        final Iterable<User> users = userRepo.getallNotDeletedUser();
        return StreamSupport
                .stream(users.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional
    @Caching(evict = {@CacheEvict(key = "#id"), @CacheEvict(key = "0")})
    public Optional<User> update(final long id, final User user) {
        final Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User foundUser = optionalUser.get();
            foundUser.setFirstname(user.getFirstname());
            foundUser.setLastname(user.getLastname());
            foundUser.setUsername(user.getUsername());
            foundUser.setUserGroup(user.getUserGroup());
            return Optional.of(userRepo.save(foundUser));
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<User> getCurrentUser(){
        final SecurityContext context = SecurityContextHolder.getContext();
        final Optional<User> optionalUser = userRepo.findByUsername(context.getAuthentication().getName());
        return optionalUser;
    }
}
