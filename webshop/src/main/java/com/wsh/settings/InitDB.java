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
    private ArticleContentRepository articleContentRepository;
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
        Article article = Article.builder().nav(Nav.MENU).name("Контакты").icon("contacts").position(2).build();
        articleRepository.save(article);
        ArticleContent articleContent=
                ArticleContent.builder().title("Контакты").content("<div>Контакты</div>").article(article) .id(article.getId()).build();
        articleContentRepository.save(articleContent);
        article = Article.builder().nav(Nav.MENU).position(1).name("Доставка").icon("delivery").build();
        articleRepository.save(article);
        articleContent=
                ArticleContent.builder().title("Доставка").content("<div>Доставка</div>").article(article).id(article.getId()).build();
        articleContentRepository.save(articleContent);

        article = Article.builder().nav(Nav.MENU).position(0).name("Акции").icon("actions").build();
        articleRepository.save(article);
        articleContent=
                ArticleContent.builder().title("Акции").content("<div>Акции</div>").article(article).id(article.getId()).build();
        articleContentRepository.save(articleContent);


        article = Article.builder().nav(Nav.FOOTER).position(0).name("Вакансии").icon("vacancy").build();
        articleRepository.save(article);
        articleContent=
                ArticleContent.builder().title("Вакансии").content("<div>Вакансии</div>").article(article).id(article.getId()).build();
        articleContentRepository.save(articleContent);

        article = Article.builder().nav(Nav.FOOTER).position(1).name("Новости").icon("news").build();
        articleRepository.save(article);
        articleContent=
                ArticleContent.builder().title("Новости").content("<div>Новости</div>").article(article).id(article.getId()).build();
        articleContentRepository.save(articleContent);

        article = Article.builder().nav(Nav.FOOTER).position(2).name("Сотрудничество").icon("coop").build();
        articleRepository.save(article);
        articleContent=
                ArticleContent.builder().title("Сотрудничество").content("<div>Сотрудничество</div>").article(article).id(article.getId()).build();
        articleContentRepository.save(articleContent);


    }
    private void initUser() {
        User user = User.builder().name("user").id(1L).role(Role.ADMIN).password(passwordEncoder.encode("password")).build();
        user = userRepository.save(user);
    }
    private void initCat() {
        Category root = categoryRepository.findFirstByName("Меню");
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
