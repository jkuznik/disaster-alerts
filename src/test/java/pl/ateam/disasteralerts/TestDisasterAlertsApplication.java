package pl.ateam.disasteralerts;

import org.springframework.boot.SpringApplication;

public class TestDisasterAlertsApplication {

    public static void main(String[] args) {
        SpringApplication.from(DisasterAlertsApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
