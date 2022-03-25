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

import com.wsh.model.CartOrder;
import com.wsh.model.Category;
import com.wsh.model.Item;
import com.wsh.repo.RepositoryCartOrder;
import com.wsh.repo.RepositoryCategory;
import com.wsh.repo.RepositoryItem;

@RequestMapping("cart")
@RestController
public class CartController {


	@Autowired Logger log;
	 
	@Autowired
	private RepositoryCartOrder repo ;

 
		@ResponseBody
		public    List<CartOrder>   list( ) {
			return repo.findAll();

		}

	 
	@GetMapping(  "/test" )
		@ResponseBody
		public    String   test( ) {
			return "CategoryController test";

		}

}
