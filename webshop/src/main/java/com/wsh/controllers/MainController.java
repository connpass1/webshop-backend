package com.wsh.controllers;

import com.wsh.model.User;
import com.wsh.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
public class MainController {
    @Autowired
    private UserRepository repoUser;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping(path = "/")
    @ResponseBody
    public ModelAndView main() {
        log.debug("main controller 2");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        log.debug("main controller 2");
        return modelAndView;
    }


    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity add(@RequestBody User user) {
        if (repoUser.existsByNameEquals(user.getName()))
            return new ResponseEntity("uncorrected password ", HttpStatus.IM_USED);
        //User dbUser=new User()
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new ResponseEntity(repoUser.save(user), HttpStatus.OK);
    }


    @PostMapping(value = "/enter", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity enter(@RequestBody User user) {
        User userDb = repoUser.findByNameEquals(user.getName());
        if (userDb == null) return new ResponseEntity("no userName", HttpStatus.PERMANENT_REDIRECT);
        if (passwordEncoder.matches(user.getPassword(), userDb.getPassword()))
            return new ResponseEntity(userDb, HttpStatus.OK);
        return new ResponseEntity("uncorrected password ", HttpStatus.NOT_EXTENDED);
    }
}
