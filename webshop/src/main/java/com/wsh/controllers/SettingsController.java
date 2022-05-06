package com.wsh.controllers;

import com.wsh.model.Article;
import com.wsh.model.ArticleInfo;
import com.wsh.model.Category;
import com.wsh.model.CategoryInfo;
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
    private ArticleRepository articleRepository;
    @ResponseBody
    public ResponseEntity idSettings(Principal principal) {

        List<CategoryInfo> categoryLinks = categoryRepository.findByParent_ParentIsNotNull();
        List<ArticleInfo>appBarList = articleRepository.findByNav(Nav.MENU);
        List<ArticleInfo> footerList= articleRepository.findByNav(Nav.FOOTER);
        List<ArticleInfo> navList  = articleRepository.findByNav(Nav.NAV);
        JSONObject jsonObject=new JSONObject() ;
        jsonObject.put("appBarLinks",appBarList);
        jsonObject.put("footerLinks",footerList);
        jsonObject.put("categoryLinks",categoryLinks);
        jsonObject.put("navLinks",navList);
        return new ResponseEntity( jsonObject , HttpStatus.OK);
    }


}
