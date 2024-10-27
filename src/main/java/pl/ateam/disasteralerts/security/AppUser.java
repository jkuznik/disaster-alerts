package pl.ateam.disasteralerts.security;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.ateam.disasteralerts.user.dto.UserDTO;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
@ToString
public class AppUser implements UserDetails {

    private UserDTO userDTO;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userDTO.role()));
    }

    @Override
    public String getPassword() {
        return userDTO.password();
    }

    @Override
    public String getUsername() {
        return userDTO.email();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}