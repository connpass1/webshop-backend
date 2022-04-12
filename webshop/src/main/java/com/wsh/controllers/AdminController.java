package com.wsh.controllers;

import com.wsh.model.Category;
import com.wsh.repo.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("admin")
@RestController
public class AdminController {
    @Autowired
    private CategoryRepository repo;

    @Autowired
    private CategoryRepository repoCategory;

    @GetMapping("/catalog")
    @ResponseBody
    public List<Category> catalogList() {
        return repoCategory.findAll();
    }

}
