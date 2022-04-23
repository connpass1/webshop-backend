package com.wsh.controllers;

import com.wsh.helper.OrderHelper;
import com.wsh.model.*;
import com.wsh.repo.*;
import lombok.extern.slf4j.Slf4j;
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

@RequestMapping("order")
@RestController
@Slf4j
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository ;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/list")
    @ResponseBody
    public List<Order> list() {
        return orderRepository.findAll();

    }

    @GetMapping("/list/item")
    @ResponseBody
    public List<OrderItem> listItem() {
        return orderItemRepository.findAll();

    }
    @GetMapping("")
    @ResponseBody
    public Set<Order> setById(Principal principal, @PathVariable int page) {
        Long id = Long.parseLong(principal.getName());
        return orderRepository.findByProfileAndStatus(id,0) ;
    }
    @GetMapping("{page}")
    @ResponseBody
    public Page<Order> pageById(Principal principal, @PathVariable int page) {
        Pageable pageable = PageRequest.of(  (page-1), 20);
        Long id = Long.parseLong(principal.getName());
        return orderRepository.findByProfile_Id(id,pageable) ;
    }

    @PostMapping("")
    @ResponseBody
    @Transactional
    public ResponseEntity makeOrder(Principal principal, @RequestBody OrderHelper[] array) {
        Long id = Long.parseLong(principal.getName());
        log.debug("" + array.length);
        Set idSet = new LinkedHashSet();
        for (OrderHelper l : array) {
            idSet.add(l.getId());
        }
        Map<Item, Integer> map = new HashMap();

        List<Item> list = itemRepository.findByIdIn(idSet);

        List<OrderItem> list1 = new LinkedList<>();
        Optional<Profile> optional = profileRepository.findById(id);
        Profile profile=null;
        if (!optional.isEmpty()) profile=optional.get();
        if (profile==null){
            profile=Profile.builder().phone(0l).build();

        profile.setUser(userRepository.findById(id).get());
        profile=profileRepository.save(profile);
        log.debug(profile.toString());
        }
        Order order = orderRepository.save(Order.builder().status(0).profile(profile).orderItems(list1).build());
        log.debug(order.toString());
        for (Item i : list) {
            list1.add(OrderItem.builder().order(order).item(i).quantity(5).build());
        }
        log.debug(profile.toString());
        orderItemRepository.saveAll(list1);
        profile.setOrder(order);
        profileRepository.save(profile);
        return new ResponseEntity(order, HttpStatus.OK);
    }
}
