package com.spring.bycrypt.example.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class MemorySecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("emma")
                .password("emma")
                .roles("boss", "dev")
                .and()
                .withUser("chris")
                .password("chris")
                .roles("dev").and()
                .passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin();

        http.authorizeRequests()
                .antMatchers("/devs/*").hasAnyRole("boss", "dev")
                .antMatchers("/boss/*").hasRole("boss")
                .antMatchers("/").permitAll();
    }
}
