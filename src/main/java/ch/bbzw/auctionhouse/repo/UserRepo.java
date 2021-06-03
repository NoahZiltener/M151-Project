package ch.bbzw.auctionhouse.repo;

import ch.bbzw.auctionhouse.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User checkPassword(String username, String password);

    Optional<User> findByUsername(String username);
}
