package com.wsh.controllers;

import com.wsh.model.User;
import com.wsh.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserRepository repo;

    @GetMapping("/add/{name}/{password}")
    @ResponseBody
    public User add(@PathVariable String name, @PathVariable String password) {
        return repo.save(User.builder().name(name).password(password).build());
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

    @GetMapping("/name/{id}")
    @ResponseBody
    public User name(@PathVariable String id) {
        return repo.findByName(id);
    }

    @GetMapping("/remove/{id}")
    @ResponseBody
		public    String   remove(@PathVariable long id ) {

		 repo.deleteById(id);
			return "user deleted"+id;
		}

}
