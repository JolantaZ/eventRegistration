package com.gmail.jolantazych.event_register.configuration;

import com.gmail.jolantazych.event_register.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("userEmail")
                .passwordParameter("userPassword")
                .defaultSuccessUrl("/home")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");


    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth ) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }


}
