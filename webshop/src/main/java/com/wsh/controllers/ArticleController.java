package com.wsh.controllers;

import com.wsh.model.Article;
import com.wsh.model.ifaces.Slug;
import com.wsh.repo.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public List<Slug> list() {
        return repo.findShortList();
    }

    @GetMapping("/list1")
    @ResponseBody
    public List<Article> list1() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity id(@PathVariable Long id) {
        Optional<Article> a = repo.findById(id) ;
        if (a.isEmpty())return  new ResponseEntity(null, HttpStatus.NOT_FOUND);
      return  new ResponseEntity(a, HttpStatus.OK);
    }

}
