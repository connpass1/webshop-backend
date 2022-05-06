package com.wsh.settings;
import com.github.javafaker.Faker;
import com.wsh.model.*;
import com.wsh.model.ifaces.Nav;
import com.wsh.model.ifaces.Quantity;
import com.wsh.model.ifaces.Role;
import com.wsh.repo.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
public class InitDB {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository repoOrder;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemDetailRepository itemDetailRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
   private Faker faker = new Faker();
    @PostConstruct
    private void postConstruct() {
        initArticle();
        initUser();
        initCat();
    }
    private void initArticle() {


        Article article=
                Article.builder().title("Акции").content("<div>Акции</div>").name("Акции").icon("actions").position(1).nav(Nav.MENU).build();

        article= articleRepository.save( article);

        article= Article.builder().title("Контакты").content("<div>Контакты</div>").name("контакты").icon("contacts").position(2).nav(Nav.MENU).build();

        articleRepository.save( article);
        article= Article.builder().title("Доставка").content("<div>Доставка</div>").name("Доставка").icon("cargo").position(3).nav(Nav.MENU).build();

        articleRepository.save( article);

        article = Article.builder().nav(Nav.FOOTER).position(0).name("Вакансии").title("Вакансии").position(2).content("<div>hhh</div>").icon("vacancy").build();
        articleRepository.save( article);

        article = Article.builder().nav(Nav.FOOTER).position(1).name("Новости").title("Новости").icon("news").position(3).content("<div>hhh</div>").build();
        articleRepository.save( article);

        article = Article.builder().nav(Nav.FOOTER).position(2).name("Сотрудничество").content("<div>hhh</div>").position(4).title("Сотрудничество").icon("coop").build();
        articleRepository.save( article);



    }
    private void initUser() {
        User user = User.builder().name("user").id(1L).role(Role.ADMIN).password(passwordEncoder.encode("password")).build();
        user = userRepository.save(user);
    }
    private void initCat() {
        Category root = categoryRepository.findRoot() ;
        if (root != null) return;
        root = Category.builder().name("Меню").icon("home").build();
        Category soup =addChild(root,"Первые блюда",0,"soup") ;
        addChild(soup,"На мясном бульоне",0,"meat");
        addChild(soup,"На рыбном бульоне",1,"fish");
        addChild(soup,"На овощном бульоне",2,"vegetables");

        Category seconds = addChild(root,"Вторые блюда" ,1,"meat" ) ;
        addChild(seconds,"Мясные",0,"meat");
        addChild(seconds,"Рыбные" ,1,"fish");
        addChild(seconds, "Овощные",2,"vegetables");

        Category garnish = addChild(root,"Гарниры"  ,2,"garnish" ) ;
        Category snack = addChild(root,"Закуски"  ,3,"snack" ) ;
        Category salads = addChild(root,"Салаты"  ,4,"salad" ) ;
        Category sauce = addChild(root,"Соусы"  ,5,"sauce" ) ;
        Category bakery = addChild(root,"Выпечка" ,6,"bakery" ) ;
        Category desserts = addChild(root,"Дессерты"  ,7,"desserts" ) ;
        Category drinks = addChild(root,"Напитки"   ,8,"drinks" ) ;


        categoryRepository.save(root);
//        for (int l = 0; l < 1000; l++) {
//            Item it = Item.builder().name(faker.food().fruit() + l)
//                    .price(faker.random().nextInt(1,100000) + l)
//                    .icon(faker.avatar().image()) .quantity(Quantity.UNLIMITED)
//                    .build();
//
//            ItemDetail det = ItemDetail.builder()
//                    .amount(faker.random().nextInt(0,10000))
//                            .item(it).caption(faker.book().title()).
//                            description(faker.lorem().sentence(5))
//
//                   .amount(faker.random().nextInt(0,4000))
//
//                    .build();
//
//            )).addChild(it);
//
//             itemRepository.save(it);
//            itemDetailRepository.save(det);
//        }
  //      categoryRepository.save(root);
    }
    private Category addChild(Category parent,String name,int position,String icon){
        Category cat = Category.builder().name(name).position(position).parent(parent).icon(icon).build();
        parent.addChild(cat);
return cat;}

}
