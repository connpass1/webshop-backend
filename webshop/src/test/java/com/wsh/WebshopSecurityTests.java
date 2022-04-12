package com.wsh;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class WebShopSecurityTests {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void passEncoder() throws Exception {

        String p = passwordEncoder.encode("password");
        log.debug(p);
        passwordEncoder.matches("password", p);
        assert (!passwordEncoder.matches("pasdddsword", p));
        assert (passwordEncoder.matches("password", p));
    }
}
