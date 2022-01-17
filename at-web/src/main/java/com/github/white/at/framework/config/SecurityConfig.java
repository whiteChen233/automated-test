package com.github.white.at.framework.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.github.white.at.framework.security.filter.JwtAuthenticationTokenFilter;
import com.github.white.at.framework.security.handle.AccessDeniedHandlerImpl;
import com.github.white.at.framework.security.handle.AuthenticationEntryPointImpl;
import com.github.white.at.framework.security.handle.LogoutSuccessHandlerImpl;

import cn.hutool.core.map.MapUtil;


// @EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and().formLogin().loginProcessingUrl("/")
            .and().logout().logoutSuccessUrl("/")
            .and().authorizeRequests(r -> r
                .antMatchers(HttpMethod.OPTIONS).permitAll()
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
        UserDetails user = User.builder().username("user").password("{noop}12345")
            .authorities(AuthorityUtils.NO_AUTHORITIES).build();
        manager.createUser(user);
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            // 禁用CSRF 因为使用session
            .csrf().disable()
            // .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
            // 授权异常
            .exceptionHandling()
            .authenticationEntryPoint(new AuthenticationEntryPointImpl())
            .accessDeniedHandler(new AccessDeniedHandlerImpl())
            // 设置 iframe 跨域
            .and()
            .headers()
            .frameOptions()
            .disable()
            // 不创建会话
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests(i -> i
                // 放行静态资源
                .antMatchers("/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                // 放行swagger
                .antMatchers("/swagger-resources/**", "/webjars/**", "/*/api-docs").permitAll()
                // webSocket
                .antMatchers("/webSocket/**").permitAll()
                // 放行文件访问
                .antMatchers("/file/**").permitAll()
                // 放行druid
                .antMatchers("/druid/**").permitAll()
                // 放行OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin().permitAll()
            .and()
            .logout()
            .logoutSuccessHandler(new LogoutSuccessHandlerImpl())
            .and()
            .build();
    }
}
