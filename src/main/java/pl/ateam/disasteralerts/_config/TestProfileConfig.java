package pl.ateam.disasteralerts._config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import pl.ateam.disasteralerts.security.AppUser;
import pl.ateam.disasteralerts.user.dto.UserDTO;

import java.util.UUID;

@Profile("test")
@Configuration
public class TestProfileConfig {

    @Bean
    @Order(2)
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/disasters").hasRole("USER")
                                .anyRequest().authenticated())
                .headers(headers ->
                        headers.frameOptions(Customizer.withDefaults()).disable())
                .formLogin(Customizer.withDefaults())
                .authenticationProvider(testDaoAuthenticationProvider());

        return http.build();
    }

    @Bean
    public UserDTO testUserDTO() {
        return new UserDTO(
                UUID.randomUUID(),
                "username",
                "email@email.emial",
                "password",
                "+481233456789",
                "location",
                "ROLE_USER"
        );
    }

    @Bean
    public AppUser testAppUser() {
        return AppUser.builder()
                .userDTO(testUserDTO())
                .build();
    }

    @Bean
    public UserDetailsService testUserDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        var user = User
                .withUserDetails(testAppUser())
                .build();

        manager.createUser(user);
        return manager;
    }

    @Bean
    public AuthenticationProvider testDaoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(testUserDetailsService());
        return provider;
    }
}
