package com.github.white.at.framework.config;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.white.at.framework.basic.enums.AccountEnum;
import com.github.white.at.framework.security.filter.CustomUsernamePasswordAuthenticationFilter;
import com.github.white.at.framework.security.filter.JwtAuthenticationTokenFilter;
import com.github.white.at.framework.security.handle.AuthenticationEntryPointHandler;
import com.github.white.at.framework.security.handle.LoginFailureHandler;
import com.github.white.at.framework.security.handle.LoginSuccessHandler;
import com.github.white.at.framework.security.handle.PermsAccessDeniedHandler;
import com.github.white.at.framework.security.handle.UserLogoutHandler;
import com.github.white.at.framework.security.handle.UserLogoutSuccessHandler;
import com.github.white.at.framework.security.service.TokenService;
import com.github.white.at.framework.web.LoginUser;

import cn.hutool.core.map.MapUtil;

// @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenService tokenService;

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            // 禁用CSRF 因为不使用session
            .and().csrf(CsrfConfigurer::disable)
            // 默认监听 /login 接口
            // .formLogin(FormLoginConfigurer::disable)
            .formLogin(
                // 重写了 UsernamePasswordAuthenticationFilter 就不使用表单的设置了
                FormLoginConfigurer::disable
                // c -> c
                // .loginPage("/security_login")
                // .successHandler(new LoginSuccessHandler())
                // .failureHandler(new LoginFailureHandler())
            )
            .logout(c -> c
                .addLogoutHandler(new UserLogoutHandler())
                .logoutSuccessHandler(new UserLogoutSuccessHandler())
            )
            // 授权异常
            .exceptionHandling(c -> c
                .authenticationEntryPoint(new AuthenticationEntryPointHandler())
                .accessDeniedHandler(new PermsAccessDeniedHandler())
            )
            .headers(c -> c
                // 设置 iframe 跨域
                .frameOptions().disable()
                // 设置 cache
                .cacheControl().disable()
            )
            // 不创建session
            .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
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
            )
            .addFilterBefore(new JwtAuthenticationTokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
            .addFilterAt(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
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
                return Optional.ofNullable(this.users.get(username))
                    .orElseThrow(() -> new UsernameNotFoundException("can not find user"));
            }
        };
        LoginUser loginUser = LoginUser.builder()
            .username("white")
            .password("{noop}Admin.1324")
            .status(AccountEnum.AVAILABLE)
            .roles(Collections.singletonList("user"))
            .perms(Collections.emptyList())
            .build();
        manager.createUser(loginUser);
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter() throws Exception {
        CustomUsernamePasswordAuthenticationFilter filter =
            new CustomUsernamePasswordAuthenticationFilter(tokenService);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        filter.setAuthenticationFailureHandler(new LoginFailureHandler());
        filter.setFilterProcessesUrl("/login");
        return filter;
    }

}
