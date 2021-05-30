package ch.bbzw.auctionhouse.service;

import ch.bbzw.auctionhouse.model.User;
import ch.bbzw.auctionhouse.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(final UserRepo userRepo){
        this.userRepo = userRepo;
    }


}
