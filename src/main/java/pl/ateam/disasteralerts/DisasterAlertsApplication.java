package pl.ateam.disasteralerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.time.LocalDateTime;

@SpringBootApplication
public class DisasterAlertsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DisasterAlertsApplication.class, args);
    }

}
