package com.wsh.controllers;

import com.wsh.model.Profile;
import com.wsh.repo.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("profile")
@RestController
public class ProfileUserController {
    @Autowired
    private ProfileRepository repo;

    @GetMapping("/list")
    @ResponseBody
    public List<Profile> list() {
        return repo.findAll();
    }


}
