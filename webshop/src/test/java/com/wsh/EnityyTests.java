package com.wsh;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class EnityTests {
    String paths[] = {"catalog", "item", "user", "photo", "cart", "photo", "profile", "property"};
    @Autowired
    private MockMvc mockMvc;
    private int status = 200;

    @Test
    void contextLoadsCatalog() throws Exception {
        for (String path : paths) {
            String p = String.format("/%s/list", path);
            mockMvc.perform(get(p)).andDo(print()).andExpect(status().is(status));
            log.debug(p);
        }


    }
}
