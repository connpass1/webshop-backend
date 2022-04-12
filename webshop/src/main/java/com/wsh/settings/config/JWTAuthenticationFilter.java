package com.wsh.settings.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.wsh.settings.config.dto.CustomUser;
import com.wsh.settings.config.dto.Customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Configuration
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private CustomAuthProvider authProvider;

    @Value("${apps.security.secret}")
    private String secret;

    @Value("${apps.security.token-prefix}")
    private String tokenPrefix;

    @Value("${apps.security.header-string}")
    private String headerString;

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {
        try {
            Customer cred = new ObjectMapper()
                    .readValue(request.getInputStream(), Customer.class);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    cred.getName(),
                    cred.getPassword(),
                    new ArrayList<>());
            return authProvider.authenticate(auth);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        CustomUser customUser = (CustomUser) auth.getPrincipal();

        log.debug(customUser.toString());
        Date d = new Date();
        String token = Jwts.builder()
                .setId(DigestUtils.md5Hex(secret)) // create random md5 as ID
                .setSubject("" + customUser.getId())
                .setIssuedAt(d)
                .setExpiration(new Date(d.getTime() + 365 * 24 * 60 * 60 * 1000L)) // one year
                .claim("role", customUser.getRole()) // add authority details to user, but will increase the payload size
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
        // response.addHeader(headerString, tokenPrefix + token);
        customUser.setToken(token);

        ObjectWriter ow = new ObjectMapper().writer();
        String json = ow.writeValueAsString(customUser);
        response.getWriter().write(json);
        response.setStatus(200); // set custom status 204, instead of 200
    }
}
