package com.wsh.controllers;

import com.wsh.model.*;
import com.wsh.model.ifaces.Quantity;
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
    private ProfileRepository profileRepository;
    @Autowired
    private ItemRepository  itemRepository;
    @Autowired
    private ItemDetailRepository  itemDetailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ArticleContentRepository articleContentRepository;
    @GetMapping("/pages/{page}")
    @ResponseBody

    public Page<Article>  pages(@PathVariable int page) {

        Pageable pageable = PageRequest.of(  (page-1), 20);
        return articleRepository.findByIdIsNotNullOrderByNavAscPositionAsc(pageable);
    }
    @GetMapping("/item/{id}")
    @ResponseBody
    public  ItemDetail  getItem(@PathVariable long id) {
          if(id>0)   return itemDetailRepository.findById(id);
        return     ItemDetail.builder().build();
    }

    @GetMapping("/page/{id}")
    @ResponseBody
    public ResponseEntity  getPage(@PathVariable long id) {

        if (id==0){

            return   new ResponseEntity(ArticleContent.builder().build(), HttpStatus.OK);}
        Optional<ArticleContent> optional = articleContentRepository.findById(id);
           if(optional .isEmpty())return   new ResponseEntity(null, HttpStatus.NOT_FOUND);
        return   new ResponseEntity(optional.get(), HttpStatus.OK);
    }
    @GetMapping("/profile/{id}")
    @ResponseBody
    public  Profile  getProfile(@PathVariable long id) {
        Optional<Profile> profile = profileRepository.findByUser_IdEquals(id);
        if (profile.isEmpty()){
          User user =  userRepository.findById(id);
            if(user==null)user=User.builder().name("не найден").id(0l).build();
            return Profile.builder().email("не известен").user(user).phone(0l).address("не известен").id(id).build();
        }
        return   profile.get();
    }
    @GetMapping("/profiles/{page}")
    @ResponseBody
    public Page<Profile> getProfiles(@PathVariable int page) {
        Pageable pageable = PageRequest.of(  (page-1), 20);;
        return  profileRepository.findByUser_IdIsNotNullOrderByUser_NameAsc(pageable);
    }
    @GetMapping("/items/{page}")
    @ResponseBody
    public Page<Item> getItems(@PathVariable int page) {
        Pageable pageable = PageRequest.of(  (page-1),  20);;
        log.debug("getItems");
        return  itemRepository.findByIdIsNotNullOrderByParent_IdAsc( pageable);
    }

    @GetMapping("/group/{id}")
    @ResponseBody
    public  Category group(@PathVariable long id) {
         return categoryRepository.findById(id);
    }
    @GetMapping("/catalog/{id}")
    @ResponseBody
    public  Category  catalog(@PathVariable long  id ) {
        if(id==0)return categoryRepository.findFirstByNameEquals("menu").get();

        return categoryRepository.findById(id);
    }

    @GetMapping("/orders/{page}")
    @ResponseBody
    public Page<Order> list(@PathVariable int page) {
        Pageable pageable = PageRequest.of(   page-1 , 20);;
        return orderRepository.findByIdIsNotNullOrderByStatusAscLastUpdateStatusAsc(pageable);
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public  User  getUser (@PathVariable long id) {
       User user= userRepository.findById(id);
        if (user==null)user= User.builder().id(0L).name("не найден").build();
        return  user;
    }




    @PostMapping("/page")
    @ResponseBody
    public ArticleContent makeArticle(Principal principal, @RequestBody ArticleContent articleContent) {
        try {
            Article article = articleRepository.save(articleContent.getArticle());
            articleContent.setArticle(article);

            return   articleContentRepository.save(articleContent);
        }
   catch (Exception w){log.warn(w.getMessage());
      return articleContent;
   }
}
    @PostMapping("/category")
    @ResponseBody
    public Category makeCategory(Principal principal, @RequestBody JSONObject jsonObject) {
       Long id= Long.valueOf((Integer) jsonObject.get("id")) ;
        Long parentId= Long.valueOf((Integer) jsonObject.get("parentId")) ;
       log.debug(jsonObject.toJSONString());
       String name= (String) jsonObject.get("name");
        String icon= (String) jsonObject.get("icon");
       Integer position =(Integer) jsonObject.get("position");
        Category category =null;
        Optional<Category> optional=categoryRepository.findById(id) ;
      if(optional!=null&&optional.isPresent()){
          category=optional.get();
      }
       else category=new Category();
        category.setName(name);
        category.setIcon(icon);
        category.setPosition(position);
        if (category.getParent()!=null){
            if ( category.getParent().getId()==parentId)return  categoryRepository.save(category);
            category.getParent().getChildrenCategory().remove(category);
            categoryRepository.save(category.getParent());
        }



        Category categoryParent =null;

        if (parentId==0)categoryParent=categoryRepository.findRoot().get();
        else  categoryParent=categoryRepository.findById(parentId).get();
        categoryParent.addChild(category);
        categoryRepository.save(categoryParent);

        return  categoryRepository.save(category);
    }


    @Transactional
    @PostMapping("/item")
    @ResponseBody
    public ItemDetail makeItem(Principal principal, @RequestBody JSONObject json) {
    log.debug(json.toString());
    int amount= (int) json.get("amount");
        int quantity= (int) json.get("quantity");
        int price= (int) json.get("price");
        String description=(String) json.get("description");
        String icon=(String) json.get("icon");
        String caption =(String) json.get("caption");
        String name =(String) json.get("name ");
        Long detailId= Long.valueOf((int) json.get("detailId"));
        ItemDetail detail = null;
        Item item = null;
        if (detailId>0){
            Optional<ItemDetail> optional = itemDetailRepository.findById(detailId);
            if(!optional.isEmpty()){
                detail=optional.get();
            item= detail.getItem();}
        }
       else{
            item = Item.builder() .build();
           detail = ItemDetail.builder() .build();
       }
       detail.setDescription(description);
       detail.setAmount(amount);
       item.setQuantity(Quantity.values()[quantity]);
        detail.setCaption(caption);
        item.setName(name);
        item.setIcon(icon);
        Category root = categoryRepository.findFirstByName("Меню");
        item.setParent(root);
        item.setPrice(price);
        item.setItemDetail(detail);
        item=itemRepository.save(item);
        detail.setId(item.getId());
        detail.setItem(item);
        root.addChild(item);
        detail =  itemDetailRepository.save(detail);
        log.debug("----------------- "+detail.toString());
        categoryRepository.save(root);
            return  detail ;
    }

}
