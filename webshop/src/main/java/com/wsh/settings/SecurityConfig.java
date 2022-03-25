package com.wsh.settings;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private  CustomAuthencationProvider customAuthencationProvider;

    @Autowired
    private  UserDetailsService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
       //и добавляем его сюда
       auth.authenticationProvider(customAuthencationProvider);
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/**","/*/remove/**" ).hasAnyRole("USER", "ADMIN")
                .antMatchers("/test" ).hasAnyRole("USER", "ADMIN")
                .antMatchers("/user/list","/item/list","/cat/list" ).hasAnyRole("USER", "ADMIN")

                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/**").permitAll().and().logout()

                .and().formLogin();
//todo
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return NoOpPasswordEncoder.getInstance();
    }
}