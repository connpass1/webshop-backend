package com.wsh;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.Date;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class EnityTests {
    String paths[] = {"/", "/catalog/list", "/item/list"};
    String paths1[] = {"/user/list", "/order/list"};
    @Autowired
    private MockMvc mockMvc;
    private int status = 200;

    @Test
    void contextLoadsCatalog() throws Exception {
        for (String path : paths) {
            mockMvc.perform(get(path)).andDo(print()).andExpect(status().is(200));
            log.debug(path);
        }
    }

    @Test
    void contextLoadsCatalog1() throws Exception {
        for (String path : paths1) {
            mockMvc.perform(get(path)).andDo(print()).andExpect(status().is(401));
            log.debug(path);
        }
    }

    @Test
    void contextLoadsCatalog3() throws Exception {
        String headerValue = getAuth();
        //  log.debug(headerValue);
        // assert (headerValue !=null);
        for (String path : paths1)
            mockMvc.perform(get(path).header("authorization", headerValue)).andDo(print()).andExpect(status().is(200));
    }

    @Test
    void contextLoadsCatalog4() throws Exception {
        String headerValue = getAuth();
        log.debug(headerValue);
        MvcResult mvcResult = mockMvc.perform(get("/user/test").header("authorization", headerValue)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        //log.debug(content);
        assert (content != null);
        mvcResult = mockMvc.perform(get("/user/").header("authorization", headerValue)).andReturn();
        content = mvcResult.getResponse().getContentAsString();
        // log.debug(content);
        assert (content != null);
        log.debug(new Date(new Date().getTime() + 365 * 24 * 60 * 60 * 1000L).toString());
        ;// one year
    }


    private String getAuth() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"user\",\"password\": \"password\" }")).andReturn();
        ;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tree = mapper.readTree(mvcResult.getResponse().getContentAsString());
        JsonNode node = tree.get("token");


        return node.textValue();
    }
}
