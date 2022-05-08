package com.wsh.controllers;

import com.wsh.model.Category;
import com.wsh.model.ItemDetail;
import com.wsh.repo.*;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Slf4j
@RequestMapping("delete")
@RestController
public class DeleteController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemDetailRepository itemDetailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    @PostMapping("/page")
    @ResponseBody
    public ResponseEntity delPage (@RequestBody JSONObject ob) {
        log.debug(ob.toJSONString());
        Long id=Long.valueOf((Integer)ob.get("id"));
        if (!articleRepository.existsByIdEquals(id))
            return new ResponseEntity("ok", HttpStatus.ACCEPTED);
        articleRepository.deleteById(id);
        return  new ResponseEntity("статья удалена", HttpStatus.ACCEPTED);
    }
    @Transactional
    @PostMapping("/item")
    @ResponseBody
    public ResponseEntity delItem (@RequestBody JSONObject ob) {
        log.debug(ob.toJSONString());
        Long id=Long.valueOf((Integer)ob.get("id"));
        ItemDetail itemDetail = itemDetailRepository.findById(id).get();
        log.debug(itemDetail.toString());
        itemDetailRepository.delete(itemDetail);

        return  new ResponseEntity("товар удален"+id, HttpStatus.ACCEPTED);
    }
    @Transactional
    @PostMapping("/catalog")
    @ResponseBody
    public ResponseEntity delCategory (@RequestBody JSONObject ob) {
        log.debug(ob.toJSONString());
        Long id=Long.valueOf((Integer)ob.get("id"));
        Category category = categoryRepository.findById(id).get();
        if(category.getParent()==null) return  new ResponseEntity("категория  не удалена"+id, HttpStatus.FORBIDDEN);
        if( !category.getItems().isEmpty()){
            itemRepository.deleteAll(category.getItems());
        }
        categoryRepository.delete( category);
        return  new ResponseEntity("категория удалена"+id, HttpStatus.ACCEPTED);
    }
}
