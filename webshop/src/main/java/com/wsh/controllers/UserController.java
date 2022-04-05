package com.wsh.controllers;

import com.wsh.model.Profile;
import com.wsh.model.User;
import com.wsh.repo.ProfileRepository;
import com.wsh.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping("user")
@RestController
public class UserController {
    @Autowired
    private ProfileRepository repoProfile;
    @Autowired
    private UserRepository repo;

    @GetMapping("/add/{name}/{password}")
    @ResponseBody
    public User add(@PathVariable String name, @PathVariable String password) {
        return repo.save(User.builder().name(name).password(password).build());
    }

    @GetMapping("/profile/{id}")
    @ResponseBody
    public ResponseEntity idProfile(@PathVariable long id) {
        Optional<Profile> p = repoProfile.findByUser_IdEquals(id);
        if(p.isPresent())new ResponseEntity (p.get(), HttpStatus.OK);
       User user= repo.findById(id);
       if(user ==null)return new ResponseEntity (null, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity (Profile.builder().user(user).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User id(@PathVariable long id) {
        return repo.findById(id);
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
    public  User  name(@PathVariable String name) {
        return repo.findByNameEquals(  name);
    }

    @PostMapping(
            value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity findByNameAndPassword(@RequestBody User user) {
        user=  repo.findByNameEqualsAndPasswordEquals(user.getName(), user.getPassword())  ;

        return new ResponseEntity (user ,user== null?HttpStatus.UNAUTHORIZED: HttpStatus.OK);
    }
    @PostMapping(
            value = "/reg", consumes = "application/json", produces = "application/json")
    public ResponseEntity< User> reg(@RequestBody User user) {

       if( repo.existsByNameEquals(user.getName()))
           return new ResponseEntity (user, HttpStatus.LOCKED);
       user= repo.save(user);
        return new ResponseEntity (user , HttpStatus.OK);
    }
    @GetMapping("/remove/{id}")
    @ResponseBody
    public String remove(@PathVariable long id) {
        repo.deleteById(id);
        return "user deleted" + id;
    }
}
