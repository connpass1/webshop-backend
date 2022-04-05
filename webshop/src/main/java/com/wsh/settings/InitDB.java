package com.wsh.settings;

import com.wsh.model.Category;
import com.wsh.model.Item;
import com.wsh.model.ItemDetail;
import com.wsh.model.User;
import com.wsh.repo.CategoryRepository;
import com.wsh.repo.ItemDetailRepository;
import com.wsh.repo.ItemRepository;
import com.wsh.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitDB {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemDetailRepository itemDetailRepository;

    @PostConstruct
    private void postConstruct() {
        Category root = categoryRepository.findFirstByName("root");
        if (root != null) return;
        root = Category.builder().name("root").build();
        for (int i = 0; i < 10; i++) {
            Category cat1 = Category.builder().name("Категория a " + i).parent(root).build();
            root.addChild(cat1);
            for (int j = 0; j < 10; j++) {
                long num = (i * 10 + j);
                Category c1 = Category.builder().name("Категория b " + num).icon("star").number(num).parent(cat1).build();
                cat1.addChild(c1);
            }
        }

        categoryRepository.save(root);
        for (int l = 0; l < 100; l++) {
            Item it = Item.builder().name("Продукт №" + l)
                    .price(100 + l)
                     .icon("task")
                    .build();

            ItemDetail det = ItemDetail.builder().amount(120).item(it).caption("ups").description("yyyy").build();

        root.addChild(it);

            itemRepository.save(it);
            itemDetailRepository.save(det);


        }

        User user = User.builder().name("user").id(1L).role("ADMIN").password("password") .build();
        userRepository.save(user);categoryRepository.save(root);
    }
}
