package ch.bbzw.auctionhouse.service;

import ch.bbzw.auctionhouse.model.User;
import ch.bbzw.auctionhouse.model.UserGroup;
import ch.bbzw.auctionhouse.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class LoginService {
    private final UserRepo userRepo;

    @Autowired
    public LoginService(final UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Transactional(readOnly = true)
    public Optional<UserGroup> login(final String username, final  String password){
        final User user = userRepo.checkPassword(username, password);
        if(user != null) {
            return Optional.of(user.getUserGroup());
        }
        return Optional.empty();
    }
}
