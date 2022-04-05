package com.wsh.controllers;

import com.wsh.helper.OrderHelper;
import com.wsh.model.Item;
import com.wsh.model.Order;
import com.wsh.model.User;
import com.wsh.repo.ItemRepository;
import com.wsh.repo.OrderRepository;
import com.wsh.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("order")
@RestController
@Slf4j
public class CartController {
    @Autowired
    private OrderRepository repo;
    @Autowired
    private UserRepository repoUser;
    @Autowired
    private ItemRepository repoItem;

    @GetMapping("/list")
    @ResponseBody
    public List<Order> list() {
        return repo.findAll();
    }

    @GetMapping("/list/{id}")
    @ResponseBody
    public List<Order> listById(@PathVariable long id) {
        log.debug("listById");
        return repo.findByUser_IdEquals(id);
    }

    @PostMapping("/{id}")
    @ResponseBody
    public Order makeOrder(@PathVariable long id, @RequestBody OrderHelper[] array) {
        Set idSet = new LinkedHashSet();
        for (OrderHelper l : array) {
            idSet.add(l.getId());
        }
        Set<Order> orders = new LinkedHashSet<>();
        List<Item> list = repoItem.findByIdIn(idSet);
        Map<Item, Integer> map = new HashMap();
        for (Item l : list) {
            OrderHelper g = Arrays.stream(array).filter(y -> y.getId() == l.getId()).findFirst().get();
            map.put(l, g.getQuantity());
        }
        User user = repoUser.findById(id);
        Order card = Order.builder().user(user).itemQuantityMap(map).build();
        repo.save(card);
        return card;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Order id(@PathVariable long id) {
        return repo.findById(id);
    }
}
