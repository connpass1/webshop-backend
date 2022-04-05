package com.wsh.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
public class MainController {
	@GetMapping(path = "/")
	@ResponseBody
	public ModelAndView main() {
		log.debug("main controller 2");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index.html");
		log.debug("main controller 2");
		return modelAndView;
	}

	@GetMapping(path = "/test1")
	@ResponseBody
	public String test1() {
		log.debug("main controller");
		return "main controller ";
	}

	@GetMapping(path = "/test ")
	@ResponseBody
	public String test() {
		log.debug("main controller");
		return "main controller";
	}
}
