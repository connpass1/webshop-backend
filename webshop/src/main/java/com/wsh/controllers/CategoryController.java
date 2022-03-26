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

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequestMapping("catalog")
@RestController 
 
public class CategoryController {


	
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

		 Item it= new Item(id,root );
		it= repoitem.save(it);
		Category c = repo.save(new Category(id,root).addChild (it));

		it. setParent(c);it= repoitem.save(it);
		  
			 return repo.save(c); 
		}

	@GetMapping("{id}" )
		@ResponseBody
		public    Category   id(@PathVariable long id ) {
		if(id==0)return repo.findByName("root");
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

	}	}
