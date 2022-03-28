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
            Category сat1 = Category.builder().name("Категория a " + i).parent(root).build().changeParent(root);
            for (int j = 0; j < 10; j++) {
                {
                    long num = (i * 10 + j);
                    Category с1 = Category.builder().name("Категория b " + num).number(num).parent(сat1).build();
                    сat1.addChild(с1);
                }
            }
        }
        categoryRepository.save(root);
        for (int l = 0; l < 100; l++) {
            ItemDetail det = ItemDetail.builder().amount(120).caption("ups").description("yyyy").build();
            itemDetailRepository.save(det);
            Item it = Item.builder().name("Продукт №" + l)
                    .price(100 + l)
                    .itemDetail(det).category(root)
                    .build();
            itemRepository.save(it);

        }
        User user = User.builder().name("user").id(1l).password("ADMIN").name("password").build();
        userRepository.save(user);
    }
}
