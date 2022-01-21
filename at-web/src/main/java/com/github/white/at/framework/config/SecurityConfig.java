package com.github.white.at.framework.config;

import java.util.Collections;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.white.at.framework.core.domain.LoginUser;
import com.github.white.at.framework.enums.AccountEnum;
import com.github.white.at.framework.security.filter.AjaxUsernamePasswordAuthenticationFilter;
import com.github.white.at.framework.security.handle.AuthenticationEntryPointImpl;
import com.github.white.at.framework.security.handle.LoginFailureHandler;
import com.github.white.at.framework.security.handle.LoginSuccessHandler;
import com.github.white.at.framework.security.handle.PermsAccessDeniedHandler;
import com.github.white.at.framework.security.handle.UserLogoutSuccessHandler;

import cn.hutool.core.map.MapUtil;

// @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .addFilterAt(new AjaxUsernamePasswordAuthenticationFilter(authenticationManagerBean()),
                UsernamePasswordAuthenticationFilter.class)
            // 默认监听 /login 接口
            .formLogin()
            .successHandler(new LoginSuccessHandler())
            .failureHandler(new LoginFailureHandler())
            .and().logout()
            .logoutSuccessUrl("/logout")
            .logoutSuccessHandler(new UserLogoutSuccessHandler())
            // 禁用CSRF 因为使用session
            .and().csrf().disable()
            // .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
            // 授权异常
            .exceptionHandling()
            .authenticationEntryPoint(new AuthenticationEntryPointImpl())
            .accessDeniedHandler(new PermsAccessDeniedHandler())
            .and()
            .headers()
            // 设置 iframe 跨域
            .frameOptions().disable()
            // 设置 cache
            .cacheControl().disable()
            // 不创建session
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests(r -> r
                // 放行静态资源
                .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                // 放行OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 放行swagger
                .antMatchers("/swagger-resources/**", "/webjars/**", "/*/api-docs").permitAll()
                // webSocket
                .antMatchers("/webSocket/**").permitAll()
                // 放行文件访问
                .antMatchers("/file/**").permitAll()
                // 放行druid
                .antMatchers("/druid/**").permitAll()
                // 其他都需要认证
                .anyRequest().authenticated()
            );
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetailsManager manager = new UserDetailsManager() {

            final Map<String, UserDetails> users = MapUtil.newHashMap();

            @Override
            public void createUser(UserDetails user) {
                this.users.putIfAbsent(user.getUsername(), user);
            }

            @Override
            public void updateUser(UserDetails user) {
                this.users.put(user.getUsername(), user);
            }

            @Override
            public void deleteUser(String username) {
                this.users.remove(username);
            }

            @Override
            public void changePassword(String oldPassword, String newPassword) {
                // do something
            }

            @Override
            public boolean userExists(String username) {
                return false;
            }

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return this.users.get(username);
            }
        };
        LoginUser loginUser = LoginUser.builder()
            .username("user")
            .password("{noop}12345")
            .status(AccountEnum.AVAILABLE)
            .roles(Collections.singletonList("user"))
            .perms(Collections.emptyList())
            .build();
        manager.createUser(loginUser);
        return manager;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
