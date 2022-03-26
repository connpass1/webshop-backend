package com.wsh.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wsh.model.CartOrder;
import com.wsh.repo.RepositoryCartOrder;

@RequestMapping("cart")
@RestController
public class CartController {


	 

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
