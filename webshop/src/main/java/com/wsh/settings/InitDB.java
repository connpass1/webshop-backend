package com.wsh.settings;
import com.wsh.model.*;
import com.wsh.model.ifaces.Nav;
import com.wsh.model.ifaces.Role;
import com.wsh.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

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

    @PostConstruct
    private void postConstruct() {
        initArticle();
        initUser();
        initCaloge();
    }

    private void initArticle() {
        Article article = Article.builder().title("Доставка").content("<div>Контент карго</div>").nav(Nav.OTHER).name("cargo").build();
        articleRepository.save(article);
    }


    private void initUser() {
        User user = User.builder().name("user").id(1L).role(Role.ADMIN).password(passwordEncoder.encode("password")).build();
        user = userRepository.save(user);
    }

    private void initCaloge() {
        Category root = categoryRepository.findFirstByName("Каталог");
        if (root != null) return;
        root = Category.builder().name("Каталог").icon("home").build();
        for (int i = 0; i < 10; i++) {
            Category cat1 = Category.builder().name("Категория a " + i).parent(root).icon("chevron-right").build();
            root.addChild(cat1);
            for (int j = 0; j < 10; j++) {
                long num = (i * 10 + j);
                Category c1 = Category.builder().name("Категория b " + num).icon("droplet").position(j ).parent(cat1).build();
                cat1.addChild(c1);
            }
        }

        categoryRepository.save(root);
        for (int l = 0; l < 50; l++) {
            Item it = Item.builder().name("Продукт №" + l)
                    .price(100 + l)
                    .icon("task")
                    .build();

            ItemDetail det = ItemDetail.builder().amount(120).item(it).caption("ups").description("yyyy").build();
            root.addChild(it);
            itemRepository.save(it);
            itemDetailRepository.save(det);
        }
        categoryRepository.save(root);
    }
}
