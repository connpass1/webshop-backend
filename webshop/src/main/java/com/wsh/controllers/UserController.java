package com.wsh.controllers;

import com.wsh.model.Order;
import com.wsh.model.Profile;
import com.wsh.model.User;
import com.wsh.repo.OrderRepository;
import com.wsh.repo.ProfileRepository;
import com.wsh.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping("user")
@RestController
public class UserController {




    @Autowired
    private OrderRepository repoOrder;
    @Autowired
    private UserRepository repo;

    @GetMapping("")
    @ResponseBody
    public User idb(Principal principal) {
        Long id = Long.parseLong(principal.getName());
        return repo.findById(id).get();
    }

    @GetMapping("/enter")
    @ResponseBody
    public ResponseEntity enter(Principal principal) {
        Long id = Long.parseLong(principal.getName());

        return new ResponseEntity(repo.findById(id).get(), HttpStatus.OK);
    }






    @GetMapping("/list")
    @ResponseBody
    public List<User> list() {
        return repo.findAll();
    }

//    @ResponseBody
//    @GetMapping("/list/{page}")
//    public List<User> findByIdOrderByNameAsc(@PathVariable int page) {
//        return repo.findByIdOrderByNameAsc(PageRequest.of(page, 20));
//    }


    @GetMapping("/name/{name}")
    @ResponseBody
    public User name(@PathVariable String name) {
        return repo.findByNameEquals(name);
    }

}
