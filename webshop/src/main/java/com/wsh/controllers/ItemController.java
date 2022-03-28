package com.wsh.controllers;

import com.wsh.model.Item;
import com.wsh.repo.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RequestMapping("item")
@RestController
@Slf4j
public class ItemController {
    @Autowired
    private ItemRepository repo;

    @GetMapping("/add/{id}")
    @ResponseBody
    public String add(@PathVariable String id) {
        return "ItemController" + id;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Item id(@PathVariable long id) {
        return repo.findById(id);
    }

    @ResponseBody
    @GetMapping("all")
    public Page<Item> listAll() {
        return repo.findByIdIsNotNullOrderByCategory_Parent_IdAscPriceAsc(PageRequest.of(1, 20));
    }

    @ResponseBody
    @GetMapping("all/{page}")
    public Page<Item> listAll1(@PathVariable int page) {
        return repo.findByIdIsNotNullOrderByCategory_Parent_IdAscPriceAsc(PageRequest.of(page, 20));
    }


    @ResponseBody
    @GetMapping("list/{category_id}")
    public Page<Item> listAll1(@PathVariable long category_id) {
        return repo.findByCategory_IdEqualsOrderByCategory_IdAscPriceAscNameAsc(category_id, PageRequest.of(1, 20));
    }


    @ResponseBody
    @GetMapping("cat/{category_id}/{page}")
    public Page<Item> listByCategoryName1(@PathVariable long category_id, @PathVariable int page) {
        return repo.findByCategory_IdEqualsOrderByCategory_IdAscPriceAscNameAsc(category_id, PageRequest.of(page, 10));
    }


    @GetMapping("/remove/{id}")
    @ResponseBody
    public String remove(@PathVariable long id) {
        repo.deleteById(id);
        return "ItemController" + id;
    }
}
