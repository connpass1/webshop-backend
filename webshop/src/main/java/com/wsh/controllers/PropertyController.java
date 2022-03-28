package com.wsh.controllers;

import com.wsh.model.ItemProperty;
import com.wsh.repo.ItemPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("property")
@RestController
public class PropertyController {
    @Autowired
    private ItemPropertyRepository repo;
    @GetMapping("list")
    @ResponseBody
    public List<ItemProperty> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ItemProperty remove(@PathVariable long id) {
        return repo.findById(id);
    }
}
