package com.wsh.controllers;

import com.wsh.model.Article;
import com.wsh.model.Category;
import com.wsh.model.ifaces.Nav;
import com.wsh.repo.*;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RequestMapping("settings")
@RestController
public class SettingsController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ArticleContentRepository articleContentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity idSettings(Principal principal) {
        Category root = categoryRepository.findRoot().get();
        JSONArray categoryLinks= new JSONArray ();



        for(Category cat:   root.getChildrenCategory()){
            JSONObject c=new JSONObject();
            c.put("name",cat.getName());
            c.put("id",cat.getId());
            c.put("icon",cat.getIcon());
            categoryLinks.appendElement(c);
        }


        List<Article>appBarList = articleRepository.findByNav(Nav.MENU);
        JSONArray appBarLinks= toJSON( appBarList);

        List<Article> footerList= articleRepository.findByNav(Nav.FOOTER);
        JSONArray footerLinks= toJSON(footerList);

        List<Article> navList  = articleRepository.findByNav(Nav.NAV);
        JSONArray navLinks= toJSON(navList);


        JSONObject j=new JSONObject() ;
        j.put("appBarLinks",appBarLinks);
        j.put("footerLinks",footerLinks);
        j.put("categoryLinks",categoryLinks);
        j.put("navLinks",navLinks);
        return new ResponseEntity( j.toJSONString(), HttpStatus.OK);
    }

    private  JSONArray toJSON (List<Article> list){
        JSONArray array=new JSONArray();
        for(Article article: list){
            JSONObject j=new JSONObject();
            j.put("name",article.getName());
            j.put("id",article.getId());
            j.put("icon",article.getIcon());
            array.appendElement(j);
        }
        return array;
    }
}
