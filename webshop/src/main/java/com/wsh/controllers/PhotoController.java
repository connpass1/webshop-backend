package com.wsh.controllers;

import com.wsh.model.Photo;
import com.wsh.repo.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("photo")
@RestController
public class PhotoController {
    @Autowired
    private PhotoRepository repo;


    @GetMapping("/{id}")
    @ResponseBody
    public Photo id(@PathVariable long id) {
        return repo.findById(id);
    }

    @GetMapping("/add/{id}")
    @ResponseBody
    public String add(@PathVariable String id) {
        return "ItemController" + id;
    }


    @ResponseBody
    @GetMapping("list")
    public List<Photo> list() {
        List<Photo> l = repo.findAll();
        return repo.findAll();

    }

    @GetMapping("/remove/{id}")
    @ResponseBody
    public String remove(@PathVariable long id) {
        repo.deleteById(id);
        return "ItemController" + id;
    }
}
