package com.wsh.controllers;

import com.wsh.model.*;
import com.wsh.model.ifaces.Slug;
import com.wsh.repo.*;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.*;

@Slf4j
@RequestMapping("admin")
@RestController
public class AdminController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ProfileRepository repoProfile;
    @Autowired
    private CategoryRepository repoCategory;
    @Autowired
    private ItemDetailRepository  repoItemDetail;
    @Autowired
    private ItemRepository  itemRepository;
    @Autowired
    private UserRepository repoUser;

    @GetMapping("/pages")
    @ResponseBody
    public List<Slug>  pages() {
        return articleRepository.findShortList();
    }
    @GetMapping("/item/{id}")
    @ResponseBody
    public  ItemDetail  getItem(@PathVariable long id) {
        ItemDetail  itemDetail =null;
          if(id>0)   itemDetail  = repoItemDetail.findById(id);
        if (itemDetail!=null)return itemDetail;
        itemDetail = ItemDetail.builder().build();
        return  itemDetail;
    }

    @GetMapping("/page/{id}")
    @ResponseBody
    public  Article  getPage(@PathVariable long id) {
        Article article=null;
       if(id>0)   article  = articleRepository.findById(id);
       if (article!=null)return article;
        return   Article.builder().name("").content("").icon("").position(0).title("").build();
    }
    @GetMapping("/profile/{id}")
    @ResponseBody
    public  Profile  getProfile(@PathVariable long id) {
        Optional<Profile> profile = repoProfile.findByUser_IdEquals(id);
        if (profile.isEmpty()){
          User user =  repoUser.findById(id);
            if(user==null)user=User.builder().name("не найден").id(0l).build();
            return Profile.builder().email("не известен").user(user).phone(0l).address("не известен").id(id).build();
        }
        return   profile.get();
    }
    @GetMapping("/profiles/{page}")
    @ResponseBody
    public Page<Profile> getProfiles(@PathVariable int page) {
        Pageable pageable = PageRequest.of(  (page-1), 20);;
        return  repoProfile.findByUser_IdIsNotNullOrderByUser_NameAsc(pageable);
    }
    @GetMapping("/items/{page}")
    @ResponseBody
    public Page<Item> getItems(@PathVariable int page) {
        Pageable pageable = PageRequest.of(  (page-1),  20);;
        log.debug("getItems");
        return  itemRepository.findByIdIsNotNullOrderByParent_IdAsc( pageable);
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public  User  getUser (@PathVariable long id) {
       User user= repoUser.findById(id);
        if (user==null)user= User.builder().id(0L).name("не найден").build();
        return  user;
    }
    @Transactional
    @PostMapping("/delete/page")
    @ResponseBody
    public ResponseEntity delPage (@RequestBody JSONObject ob) {
        log.debug(ob.toJSONString());
        Long id=Long.valueOf((Integer)ob.get("id"));

        if (!articleRepository.existsByIdEquals(id))
            return new ResponseEntity("ok", HttpStatus.ACCEPTED);
        articleRepository.deleteById(id);
        return  new ResponseEntity("статья удалена", HttpStatus.ACCEPTED);

    }
    @Transactional
    @PostMapping("/delete/item")
    @ResponseBody
    public ResponseEntity delItem (@RequestBody JSONObject ob) {
        log.debug(ob.toJSONString());
        Long id=Long.valueOf((Integer)ob.get("id"));
        itemRepository.deleteById(id);

        return  new ResponseEntity("товар удален"+id, HttpStatus.ACCEPTED);

    }


    @PostMapping("/page")
    @ResponseBody
    public Article makeArticle(Principal principal, @RequestBody Article article) {

        try {
            return   articleRepository.save(article);
        }
   catch (Exception w){log.warn(w.getMessage());
      return article;

   }
}}
