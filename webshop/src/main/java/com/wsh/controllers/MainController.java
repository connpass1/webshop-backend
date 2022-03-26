package com.wsh.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

 



@RestController
public class MainController {
	@GetMapping(path = "/404.html" )
	  
		@ResponseBody
		public    ModelAndView   error(@PathVariable long id ) {
			 ModelAndView modelAndView = new ModelAndView();
		    modelAndView.setViewName("index.html");
		    return modelAndView;
		    }
	 @GetMapping(path = "/" )
		@ResponseBody
		public    ModelAndView   main( ) {
		 ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("index.html");
	    return modelAndView;
		 } 	
	     
}
