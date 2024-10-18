package pl.ateam.disasteralerts._config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class TestSecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .httpBasic(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                                .requestMatchers("/disasters").hasRole("USER")
//                                .anyRequest().authenticated())
//                .headers(headers ->
//                        headers.frameOptions(Customizer.withDefaults()).disable())
//                .formLogin(Customizer.withDefaults());
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        var userDetailsManager = new InMemoryUserDetailsManager();
//
//        var user1 = User.withUsername("email")
//                .password("{noop}pass")
//                .roles("USER")
//                .build();
//
//        userDetailsManager.createUser(user1);
//
//        return userDetailsManager;
//    }
}
