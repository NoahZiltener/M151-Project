package ch.bbzw.auctionhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuctionhouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuctionhouseApplication.class, args);
    }

}
