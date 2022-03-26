package com.wsh;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
 
class WebshopApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	 
	 
	 
	
	//@Test
	void contextLoads() throws Exception {
		
		 mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
	 
	}
	//@Test
	void contextLoadscat() throws Exception {
		 
		 mockMvc.perform(get("/cat/list")) .andDo(print()).andExpect(status().is(403));
		 log.debug("jjjjj");
	}
}
