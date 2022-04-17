package com.wsh.controllers;

import com.wsh.model.Order;
import com.wsh.model.Profile;
import com.wsh.model.User;
import com.wsh.repo.OrderRepository;
import com.wsh.repo.ProfileRepository;
import com.wsh.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
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
    private ProfileRepository repoProfile;

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

    @GetMapping("/profile")
    @ResponseBody
    public ResponseEntity idProfile(Principal principal) {
        Long id = Long.parseLong(principal.getName());
        Optional<Profile> p = repoProfile.findByUser_IdEquals(id);
        if (p.isPresent()) return new ResponseEntity(p.get(), HttpStatus.OK);
        User user = repo.findById(id).get();
        if (user == null) return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        repoProfile.findByUser_IdEquals(id);

        return new ResponseEntity((Profile.builder().email("") .user(user).build()), HttpStatus.OK);
    }

    @PostMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity makeProfile(Principal principal, @RequestBody Profile profile) {
     log.debug("profile");
        Long id = Long.parseLong(principal.getName());
        User user = repo.findById(id).get();
        Optional<Profile> p = repoProfile.findByUser_IdEquals(id);
        if (p.isPresent()) {
            Profile pr = p.get();
            pr.setAddress(profile.getAddress());
            pr.setEmail(profile.getEmail());
            pr.setPhone(profile.getPhone());
            return new ResponseEntity(repoProfile.save(pr), HttpStatus.OK);
        }

        if (user == null) return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        profile.setUser(user);
        return new ResponseEntity(repoProfile.save(profile), HttpStatus.OK);
    }


    @GetMapping("order")
    @ResponseBody
    public List<Order> listById(Principal principal) {
        Long id = Long.parseLong(principal.getName());
        log.debug("  @GetMapping(\"order\")" + id);
        return repoOrder.findByUser_IdEquals(id);
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
