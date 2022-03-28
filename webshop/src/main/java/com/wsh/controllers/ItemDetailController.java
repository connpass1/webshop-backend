package com.wsh.controllers;

import com.wsh.model.ItemDetail;
import com.wsh.repo.ItemDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("itemDetail")
@RestController
@Slf4j
public class ItemDetailController {


    @Autowired
    private ItemDetailRepository repo;


    @GetMapping("/{id}")
    @ResponseBody
    public ItemDetail id(@PathVariable long id) {
        return repo.findById(id);
    }


    @ResponseBody
    @GetMapping("list")
    public List<ItemDetail> list() {
        return repo.findAll();

    }

    @GetMapping("/remove/{id}")
    @ResponseBody
    public String remove(@PathVariable long id) {
        repo.deleteById(id);
        return "ItemController" + id;
    }


}
