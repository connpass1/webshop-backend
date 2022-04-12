package com.wsh.settings.config;

import com.wsh.repo.UserRepository;
import com.wsh.settings.config.dto.CustomUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CustomAuthProvider implements AuthenticationManager {
    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (authentication.getName() == null || authentication.getCredentials() == null) {
            throw new BadCredentialsException("Credentials can not null.");
        }

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        com.wsh.model.User userDb = repo.findByNameEquals(username);

        if (userDb == null) {
            log.debug("no user");
            throw new BadCredentialsException("Bad credentials.");
        }
        log.debug(userDb.getName());


        List<GrantedAuthority> granAuth = new ArrayList<>();

        if (username.equals(userDb.getName()) && userDb.validate(password, passwordEncoder)) {

            CustomUser user = CustomUser.builder().name(userDb.getName()).id(userDb.getId())
                    .role(userDb.getRole()).build();

            UsernamePasswordAuthenticationToken dd = new UsernamePasswordAuthenticationToken(user, "", granAuth);

            return new UsernamePasswordAuthenticationToken(user, "", granAuth);
        }
        throw new BadCredentialsException("Bad credentials.");


    }
}
