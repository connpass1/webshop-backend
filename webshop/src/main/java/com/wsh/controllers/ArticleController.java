package com.wsh.controllers;

import com.wsh.model.Article;
import com.wsh.model.ArticleIFace;
import com.wsh.repo.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("page")
@RestController
public class ArticleController {
    @Autowired
    ArticleRepository repo;

    @PostMapping("")
    @ResponseBody
    public Article save(@RequestBody Article article) {
        return repo.save(article);
    }

    @GetMapping("/list")
    @ResponseBody
    public List<ArticleIFace> list() {
        return repo.findShortList();
    }

    @GetMapping("/list1")
    @ResponseBody
    public List<Article> list1() {
        return repo.findAll();
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public Article id(@PathVariable Long id) {
        return repo.findById(id).get();
    }

    @GetMapping("/{name}")
    @ResponseBody
    public Article stringId(@PathVariable String name) {
        return repo.findByNameEqualsIgnoreCase(name);
    }

}
