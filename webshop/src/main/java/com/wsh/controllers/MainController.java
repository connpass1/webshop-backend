package com.wsh.controllers;

import com.wsh.helper.UserPass;
import com.wsh.model.*;
import com.wsh.model.ifaces.Nav;
import com.wsh.repo.*;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class MainController {
    @Autowired
    private UserRepository repoUser;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ArticleContentRepository articleContentRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @GetMapping(path = "/")
    @ResponseBody
    public ModelAndView main() {
        log.debug("main controller 2");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        log.debug("main controller 2");
        return modelAndView;
    }
    @GetMapping("/page/{id}")
    @ResponseBody
    public ResponseEntity  getPage(@PathVariable long id) {

        if (id<1){

            return   new ResponseEntity(null, HttpStatus.NOT_FOUND);}
        Optional<ArticleContent> optional = articleContentRepository.findById(id);
        if(optional .isEmpty())return   new ResponseEntity(null, HttpStatus.NOT_FOUND);
        return   new ResponseEntity(optional.get(), HttpStatus.OK);
    }
    @Autowired
    private CategoryRepository categoryRepository;
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity add(@RequestBody UserPass userPass) {
        log.debug(userPass.getName());
        log.debug(userPass.getPassword());
        boolean isUserInDB = repoUser.existsByNameEquals(userPass.getName(  ));
        log.debug(";;"+isUserInDB );
        if (isUserInDB )
            return new ResponseEntity("uncorrected password ", HttpStatus.IM_USED);
         User user=new User();
         user.setName(userPass.getName());
        user.setPassword(passwordEncoder.encode(userPass.getPassword()));
        return new ResponseEntity(repoUser.save(user), HttpStatus.OK);
    }

    @PostMapping(value = "/enter", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity enter(@RequestBody User user) {
        User userDb = repoUser.findByNameEquals(user.getName());
        if (userDb == null) return new ResponseEntity("no userName", HttpStatus.PERMANENT_REDIRECT);
        if (passwordEncoder.matches(user.getPassword(), userDb.getPassword()))
            return new ResponseEntity(userDb, HttpStatus.OK);
        return new ResponseEntity("uncorrected password ", HttpStatus.NOT_EXTENDED);
    }

    @GetMapping("/profile")
    @ResponseBody
    public ResponseEntity idProfile(Principal principal) {
        Long id = Long.parseLong(principal.getName());
        Optional<Profile> p =  profileRepository.findByUser_IdEquals(id);
        if (p.isPresent()) return new ResponseEntity(p.get(), HttpStatus.OK);
        Optional<User> op = repoUser.findById(id);
        if (op.isEmpty()) return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        profileRepository.findByUser_IdEquals(id);
        return new ResponseEntity((Profile.builder().email("") .user(op.get()).id(op.get().getId()).build()), HttpStatus.OK);
    }


    @PostMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity makeProfile(Principal principal, @RequestBody JSONObject profile) {
        log.debug("profile");
        log.debug(profile.toJSONString()) ;
        Long id = Long.parseLong(principal.getName());
        String email= (String) profile.get("email");
        String address = (String)profile.get("address");
        Long phone=    (Long)profile.get("phone")  ;
        Optional<Profile> p = profileRepository.findByUser_IdEquals(id);
        Profile pr;
        if (p.isPresent()) {
            pr = p.get();
            pr.setAddress( address );
            pr.setEmail( email );
            pr.setPhone( phone );
            return new ResponseEntity(profileRepository.save(pr), HttpStatus.ACCEPTED);
        }
        Optional<User> optional = repoUser.findById(id);
        if (!optional.isPresent()  ) return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);


        pr=Profile.builder().user(optional.get() ).id(optional.get().getId()).email(email).phone(phone).address(address).build();

        return new ResponseEntity(profileRepository.save(pr ), HttpStatus.CREATED);
        //  return new ResponseEntity(pr , HttpStatus.CREATED);
    }

}