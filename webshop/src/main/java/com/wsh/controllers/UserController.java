package com.wsh.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wsh.model.Item;
import com.wsh.model.User;
import com.wsh.repo.RepositoryItem;
import com.wsh.repo.RepositoryUser;

@RequestMapping("user")
@RestController
public class UserController {


	@Autowired
	private RepositoryUser repo;

	@GetMapping("/add/{name}/{password}" )
		@ResponseBody
		public    User   add(@PathVariable String name,@PathVariable String password ) { 
			return  repo.save(new User(name,password));
		}

	@GetMapping("/id/{id}" )
		@ResponseBody
		public    User   id(@PathVariable long id ) {
			return repo.findById(id);
		}

	@GetMapping("/list" )
		@ResponseBody
		public    List<User>   list( ) {
			return repo.findAll();

		}

	@GetMapping("/name/{id}" )
		@ResponseBody
		public    User   name(@PathVariable String id ) {
			return repo.findByName(id);
		}
	@GetMapping("/remove/{id}" )
		@ResponseBody
		public    String   remove(@PathVariable long id ) {

		 repo.deleteById(id);
			return "user deleted"+id;
		}
	 
}
