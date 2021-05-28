package com.github.whitechen233.at.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure( AuthenticationManagerBuilder auth) throws Exception {
        auth
            // TODO 暂时先使用内存中的 inMemoryAuthentication  需要修改成 userDetailsService
            .inMemoryAuthentication()
            .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder())
            .withUser("admin").password("admin").roles("ADMIN")
            .and()
            .withUser("normal").password("normal").roles("NORMAL");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("test/echo").permitAll()
            .antMatchers("test/admin").hasRole("ADMIN")
            .antMatchers("test/normal").access("hasRole('ROlE_NORMAL')")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }
}
