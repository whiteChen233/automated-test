package com.github.white.at.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.white.at.framework.security.UserDetailsRepository;
import com.github.white.at.framework.security.filter.JwtAuthenticationTokenFilter;
import com.github.white.at.framework.security.handle.AccessDeniedHandlerImpl;
import com.github.white.at.framework.security.handle.AuthenticationEntryPointImpl;
import com.github.white.at.framework.security.handle.LogoutSuccessHandlerImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
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

    @Bean
    public UserDetailsRepository userDetailsRepository() {
        UserDetailsRepository userDetailsRepository = new UserDetailsRepository();
        UserDetails defaultUser = User.builder().username("admin").password("{noop}12345")
            .authorities(AuthorityUtils.NO_AUTHORITIES).build();
        userDetailsRepository.createUser(defaultUser);
        return userDetailsRepository;
    }

    @Bean
    public UserDetailsManager userDetailsManager(UserDetailsRepository userDetailsRepository) {
        return new UserDetailsManager() {
            @Override
            public void createUser(UserDetails user) {
                userDetailsRepository.createUser(user);
            }

            @Override
            public void updateUser(UserDetails user) {
                userDetailsRepository.updateUser(user);
            }

            @Override
            public void deleteUser(String username) {
                userDetailsRepository.deleteUser(username);
            }

            @Override
            public void changePassword(String oldPassword, String newPassword) {

            }

            @Override
            public boolean userExists(String username) {
                return userDetailsRepository.userExists(username);
            }

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userDetailsRepository.loadUserByUsername(username);
            }
        };
    }
}
