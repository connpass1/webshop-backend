package com.wsh.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wsh.model.Item;
import com.wsh.repo.RepositoryItem;

@RequestMapping("item")
@RestController
public class ItemController {


	@Autowired
	private RepositoryItem repo;

	@GetMapping("/add/{id}" )
		@ResponseBody
		public    String   add(@PathVariable String id ) {

		// repo.save(new Item(id));
			return "ItemController"+id;
		}

	@GetMapping("/id/{id}" )
		@ResponseBody
		public    Item   id(@PathVariable long id ) {
			return repo.findById(id);
		}

	@GetMapping("/list" )
		@ResponseBody
		public    List<Item>   list( ) {
			return repo.findAll();

		}

	@GetMapping("/name/{id}" )
		@ResponseBody
		public    Item   name(@PathVariable String id ) {
			return repo.findByName(id);
		}
	@GetMapping("/remove/{id}" )
		@ResponseBody
		public    String   remove(@PathVariable long id ) {

		 repo.deleteById(id);
			return "ItemController"+id;
		}
	@GetMapping(  "/test" )
		@ResponseBody
		public    String   test( ) {
			return "ItemController test";

		}

}
