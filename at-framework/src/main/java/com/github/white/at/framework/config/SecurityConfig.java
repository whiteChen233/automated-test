package com.github.white.at.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.white.at.framework.security.filter.JwtAuthenticationTokenFilter;
import com.github.white.at.framework.security.handle.AccessDeniedHandlerImpl;
import com.github.white.at.framework.security.handle.LogoutSuccessHandlerImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final AccessDeniedHandlerImpl jwtAccessDeniedHandler;

    private final LogoutSuccessHandlerImpl logoutSuccessHandler;

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    public SecurityConfig(AuthenticationEntryPoint authenticationEntryPoint,
                          AccessDeniedHandlerImpl jwtAccessDeniedHandler,
                          LogoutSuccessHandlerImpl logoutSuccessHandler,
                          JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter) {
        this.jwtAuthenticationEntryPoint = authenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        /*
         * 在 spring security 5 提供了这样一个思路，应该将密码编码之后的 hash 值和加密方式一起存储，并提供了一个 DelegatingPasswordEncoder 来作为众多密码密码编码方式的集合。
         */
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 禁用CSRF 因为使用session
            .csrf().disable()
            .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
            // 授权异常
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
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
            .authorizeRequests()

            // 放行静态资源
            .antMatchers("/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/webSocket/**").permitAll()
            // 放行swagger
            .antMatchers("/swagger-resources/**").permitAll()
            .antMatchers("/webjars/**").permitAll()
            .antMatchers("/*/api-docs").permitAll()
            // 放行文件访问
            .antMatchers("/file/**").permitAll()
            // 放行druid
            .antMatchers("/druid/**").permitAll()
            // 放行OPTIONS请求
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//            .anyRequest().authenticated()
            .and()
            .formLogin()
            .permitAll()
            .and()
            .logout()
            .logoutSuccessHandler(logoutSuccessHandler);
    }
}
