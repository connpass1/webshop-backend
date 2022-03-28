package com.wsh.settings;


import com.wsh.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Slf4j
 @Service
public class UserService implements UserDetailsService  {

    @Autowired
    private UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
         com.wsh.model.User myUser= userRepo.findByName(userName);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: "+userName);
     }
        log.debug( userName );

        UserDetails user = User.builder()
        		.username(myUser.getName())

                .password(myUser.getPassword())
                .roles(myUser.getRole())
                .build();
        return user;
    }
}