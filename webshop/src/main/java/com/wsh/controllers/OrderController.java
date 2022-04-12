package com.wsh.controllers;

import com.wsh.helper.OrderHelper;
import com.wsh.model.Item;
import com.wsh.model.Order;
import com.wsh.model.OrderItem;
import com.wsh.model.User;
import com.wsh.repo.ItemRepository;
import com.wsh.repo.OrderItemRepository;
import com.wsh.repo.OrderRepository;
import com.wsh.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RequestMapping("order")
@RestController
@Slf4j
public class OrderController {
    @Autowired
    private OrderRepository repo;

    @Autowired
    private OrderItemRepository orderItemRepo;

    @Autowired
    private UserRepository repoUser;
    @Autowired
    private ItemRepository repoItem;

    @GetMapping("/list")
    @ResponseBody
    public List<Order> list() {
        return repo.findAll();

    }

    @GetMapping("/list/item")
    @ResponseBody
    public List<OrderItem> listItem() {
        return orderItemRepo.findAll();

    }

    @GetMapping("")
    @ResponseBody
    public List<Order> listById(Principal principal) {
        Long id = Long.parseLong(principal.getName());
        return repo.findByUser_IdEquals(id);
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity makeOrder(Principal principal, @RequestBody OrderHelper[] array) {
        Long id = Long.parseLong(principal.getName());
        log.debug("" + array.length);
        Set idSet = new LinkedHashSet();
        for (OrderHelper l : array) {
            idSet.add(l.getId());
        }
        Map<Item, Integer> map = new HashMap();

        List<Item> list = repoItem.findByIdIn(idSet);

        List<OrderItem> list1 = new LinkedList<>();
        User user = repoUser.findById(id).get();
        Order order = repo.save(Order.builder().status(0).user(user).orderItems(list1).build());
        for (Item i : list) {
            list1.add(OrderItem.builder().order(order).item(i).quantity(5).build());
        }

        orderItemRepo.saveAll(list1);


        return new ResponseEntity(order, HttpStatus.OK);
    }


}
