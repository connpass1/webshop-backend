package com.wsh.controllers;

import com.wsh.model.Item;
import com.wsh.model.ItemDetail;
import com.wsh.repo.ItemDetailRepository;
import com.wsh.repo.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("item")
@RestController
@Slf4j
public class ItemController {
    @Autowired
    private ItemRepository repo;
    @Autowired
    private ItemDetailRepository repoDetail;

    @GetMapping("/add/{id}")
    @ResponseBody
    public String add(@PathVariable String id) {
        return "ItemController" + id;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ItemDetail id(@PathVariable long id) {
        return repoDetail.findById(id);
    }

    @GetMapping("/list")
    @ResponseBody
    public Iterable<Item> list( ) {
        return repo.findAll();
    }

    @GetMapping("/all")
    @ResponseBody
    public List<ItemDetail> all( ) {
        return repoDetail.findAll();
    }

    @GetMapping("/remove/{id}")
    @ResponseBody
    public String remove(@PathVariable long id) {
        repo.deleteById(id);
        return "ItemController" + id;
    }
}
