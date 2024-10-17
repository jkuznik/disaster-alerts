package pl.ateam.disasteralerts.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomDaoAuthenticationProvider customDaoAuthenticationProvider;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/signup").permitAll()
//                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(loginConfigurer -> loginConfigurer
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/signup"))
                .logout(logout -> logout.logoutSuccessUrl("/login"))
                .authenticationProvider(customDaoAuthenticationProvider);
        return http.build();
    }
}
