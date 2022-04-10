package com.wsh.settings.config;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.wsh.model.User;
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

    @Value("${apps.user.name}")
    private String name;

    @Value("${apps.user.password}")
    private String password;

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {
        try {
             User creds = new ObjectMapper()
                    .readValue(request.getInputStream(),  User.class);
            UsernamePasswordAuthenticationToken authc = new UsernamePasswordAuthenticationToken(
                    creds.getName(),
                    creds.getPassword(),

                    new ArrayList<>());log.debug(creds.getName());
            return authProvider.authenticate(authc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        Date d = new Date();
        String token = Jwts.builder()
                .setId(DigestUtils.md5Hex(secret)) // create random md5 as ID
                .setSubject((( org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                .setIssuedAt(d)
                .setExpiration(new Date(d.getTime() + 1_314_000_000L)) // 365 years
                .claim("authorities", auth.getAuthorities()) // add authority details to user, but will increase the payload size
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
        response.addHeader(headerString, tokenPrefix + token);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT); // set custom status 204, instead of 200
    }
}
