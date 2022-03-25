package com.wsh.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wsh.model.Category;
import com.wsh.model.Item;
import com.wsh.repo.RepositoryCategory;
import com.wsh.repo.RepositoryItem;

@RequestMapping("cat")
@RestController
public class CategoryController {


	@Autowired Logger log;
	@Autowired
	private RepositoryCategory repo;

	@Autowired
	private RepositoryItem repoitem;


	@Transactional
	@GetMapping("/add/{id}" )
		@ResponseBody
		public    Category   add(@PathVariable String id )   {

		Category root = repo.findByName("root");
		 log.error("root"+(root==null));

		 Item it= new Item("tttt");
		it= repoitem.save(it);
		Category c = repo.save(new Category(id,root).addItem(it));

		it. setParent(c);it= repoitem.save(it);
		 c = repo.save(new Category(id).addItem(it));
			 return c;
		}

	@GetMapping("/id/{id}" )
		@ResponseBody
		public    Category   id(@PathVariable long id ) {
			return repo.findById(id);
		}

	@GetMapping("/list" )
		@ResponseBody
		public    List<Category>   list( ) {
			return repo.findAll();

		}

	@GetMapping("/name/{id}" )
		@ResponseBody
		public    Category   name(@PathVariable String id ) {
			return repo.findByName(id);
		}
	@GetMapping("/remove/{id}" )
		@ResponseBody
		public    String   remove(@PathVariable long id ) {

		 repo.deleteById(id);
			return "CategoryController"+id;
		}
	@GetMapping(  "/test" )
		@ResponseBody
		public    String   test( ) {
			return "CategoryController test";

		}

}
