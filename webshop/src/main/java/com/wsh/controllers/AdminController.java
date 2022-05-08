package com.wsh.controllers;

import com.wsh.model.*;
import com.wsh.model.ifaces.ItemFull;
import com.wsh.model.ifaces.Nav;
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


    @GetMapping("/pages")
    @ResponseBody
    public List<ArticleInfo>  pages( ) {
        return articleRepository.findByIdIsNotNullOrderByNavAscPositionAsc( );
    }
    @GetMapping("/item/{id}/{cat}")
    @ResponseBody
    public  ItemDetail   createItem(@PathVariable long id,@PathVariable long cat) {

        log.debug("ids"+id+"cat"+cat);
          if(id>0)   return itemDetailRepository.findByItem_IdEquals(id);
        Category parent = categoryRepository.findById(cat);
          Item item=Item.builder().parent(parent).quantity(Quantity.UNLIMITED).id(0l).build();
        return     ItemDetail.builder().item(item).build();
    }
    @GetMapping("/item/{id}")
    @ResponseBody
    public  ItemDetail  getItem(@PathVariable long id ) {
   return itemDetailRepository.findByItem_IdEquals(id);

    }
    @GetMapping("/page/{id}")
    @ResponseBody
    public ResponseEntity  getPage(@PathVariable long id) {
        if (id==0)
            return   new ResponseEntity(Article.builder().title("").name("").nav(Nav.OTHER).icon("").id(0l).content("").build(), HttpStatus.OK);
        Optional<Article> optional = articleRepository.findById(id);
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

    @GetMapping("/catalog")
    @ResponseBody
    public  Category  catalogRoot( ) {
        return categoryRepository.findRoot() ;
    }

    @GetMapping("/catalog/{id}")
    @ResponseBody
    public  Category  catalog(@PathVariable long  id ) {
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
    public ResponseEntity makeArticle(Principal principal, @RequestBody Article   article  ) {
        try {
              log.debug(article.toString());
            return  new ResponseEntity(articleRepository.save(article) , HttpStatus.CREATED);
        }
   catch (Exception w){log.debug(w.getMessage());
       return  new ResponseEntity( article  , HttpStatus.IM_USED);
   }
}
    @PostMapping("/category")
    @ResponseBody
    public Category makeCategory(Principal principal, @RequestBody JSONObject jsonObject) {  log.debug(jsonObject.toJSONString());
       Long id= Long.valueOf((Integer) jsonObject.get("id")) ;
        Long parentId= Long.valueOf((Integer) jsonObject.get("parentId")) ;

       String name= (String) jsonObject.get("name");
        String icon= (String) jsonObject.get("icon");
       Integer position =(Integer) jsonObject.get("position");
        Category category =null;
        Optional<Category> optional=categoryRepository.findById(id) ;
      if(optional!=null&optional.isPresent()){
          category=optional.get();
      }
       else category=new Category();
        category.setName(name);
        category.setIcon(icon);
        category.setPosition(position);
        Category categoryParent =null;
        if (parentId==0)categoryParent=categoryRepository.findRoot() ;
        else  categoryParent=categoryRepository.findById(parentId).get();
        categoryParent.addChild(category);
    return   categoryRepository.save(category);
    }

    @Transactional
    @PostMapping("/item")
    @ResponseBody
    public ItemDetail makeItem(Principal principal, @RequestBody ItemFull itemFull) {
        log.debug(itemFull.toString());
        ItemDetail itemDetail;
        Item item;
        if(itemFull.getId()>0){
            itemDetail=itemDetailRepository.findById(itemFull.getId()).get();
            item=itemDetail.getItem();
            log.debug(itemDetail.toString());
        }
        else{
            item= new Item ( ) ;
            itemDetail=new ItemDetail();
            Category category=categoryRepository.findById(itemFull.getParent().getId()).get();
            category.addChild(item);
            itemDetail.setItem(item);
            categoryRepository.save(category);
        }
        item.setPrice(itemFull.getPrice());
        item.setIcon(itemFull.getIcon());
        item.setName(itemFull.getName());
        item.setQuantity(itemFull.getQuantity());
        itemDetail.setDescription(itemFull.getDescription());
        itemDetail.setPhoto( itemFull.getPhoto() );
        itemDetail.setComposition( itemFull.getComposition());
    itemDetail= itemDetailRepository .save(itemDetail);

            return  itemDetail ;
    }
}
