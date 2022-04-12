package com.wsh.settings.config.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@Data
@Builder

public class CustomUser implements UserDetails, CredentialsContainer {

    private Long id;
    private String role = "USER";
    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String name;
    private String token;


    @Override
    public void eraseCredentials() {
        this.password = null;
    }


    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}