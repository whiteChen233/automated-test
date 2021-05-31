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
            /*
             * 在 spring security 5 提供了这样一个思路，应该将密码编码之后的 hash 值和加密方式一起存储，
             * 并提供了一个 DelegatingPasswordEncoder 来作为众多密码密码编码方式的集合。
             */
            .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder())
            .withUser("admin").password("{noop}admin").roles("ADMIN")
            .and()
            .withUser("normal").password("{noop}normal").roles("NORMAL");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/security/echo").permitAll()
            .antMatchers("/security/admin").hasRole("ADMIN")
            .antMatchers("/security/normal").access("hasRole('NORMAL')")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }
}
