package com.wsh.settings;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wsh.repo.RepositoryUser;

 @Service
public class userDetailsService implements UserDetailsService {
    @Autowired
    private  Logger log;
    @Autowired
    private  RepositoryUser userRepo;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
         com.wsh.model.User myUser= userRepo.findByName(userName);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown userfffffffff: "+userName);
     }
        log.debug("-------------------------------userName");

        UserDetails user = User.builder()
        		.username(myUser.getName())

                .password(myUser.getPassword())
                .roles(myUser.getRole())
                .build();
        return user;
    }
}