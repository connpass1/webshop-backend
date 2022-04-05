package com.wsh.settings;

import com.wsh.model.*;
import com.wsh.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class InitDB {

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
        user = userRepository.save(user);
        categoryRepository.save(root);


//        Map<Item, Integer> map =new HashMap<>();
//        Iterable<Item> all = itemRepository.findAll();
//        for(Item it :all)  map.put(it,23);
//
//        Order o=Order.builder().items(map).build();
//       //o.setUser(userRepository.findAll().get(0));
//        repoOrder.save(o);

      ;
    }
}
