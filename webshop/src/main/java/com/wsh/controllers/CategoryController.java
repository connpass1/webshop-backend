package com.wsh.controllers;

import com.wsh.model.Category;
import com.wsh.model.Item;
import com.wsh.model.ItemDetail;
import com.wsh.repo.CategoryRepository;
import com.wsh.repo.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RequestMapping("catalog")
@RestController
public class CategoryController {
    @Autowired
    private CategoryRepository repo;
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/{id}")
    @ResponseBody
    public Category id(@PathVariable long id) {
        return repo.findById(id);
    }
    @GetMapping("/list")
    @ResponseBody
    public List<Category> list() {
        return repo.findAll();
    }

    @GetMapping("/name/{name}")
    @ResponseBody
    public Category name(@PathVariable String name) {
        return repo.findFirstByName("name");
    }

    @GetMapping("/remove/{id}")
    @ResponseBody
    public String remove(@PathVariable long id) {
        repo.deleteById(id);
        return "CategoryController" + id;
    }
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "CategoryController test";
    }
}
