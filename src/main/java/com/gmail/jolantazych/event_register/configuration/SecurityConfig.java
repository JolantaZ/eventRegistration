package com.gmail.jolantazych.event_register.configuration;

import com.gmail.jolantazych.event_register.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)   opcja przy używaniu @Secured
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.authorizeRequests().anyRequest().permitAll()
                .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/eventDeleteParam/{idEvent}").hasRole("ADMIN")
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied")
                .and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("userEmail") // nazwa taka jak name inputa w htmlu do logowania
                .passwordParameter("userPassword") // jak wyżej
                .defaultSuccessUrl("/home")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");  // stworzony przycisk jako formularz w menu




    }


    // bez tej metody logowanie się nie powiedzie
    // błędy: brak PasswordEncoder'a  - There is no PasswordEncoder mapped for the id "null" ????
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth ) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // zakomentowanie tej linii tylko również powoduje powyższy błąd (sprawdzane na userach z kodowanym hasłem i nie)
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}
