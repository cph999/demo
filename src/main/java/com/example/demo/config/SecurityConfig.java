package com.example.demo.config;

import com.example.demo.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <p>安全配置</p>
 *
 * @author gqz20
 * @create 2022-05-25 09:05
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                //当用户请求了一个受保护的资源，但是用户没有通过认证.AuthenticationEntryPoint. Commence(..)就会被调用
                .authenticationEntryPoint(new MyEntryPoint())
                .accessDeniedHandler(new MyAccessDeniedHandler())
                .and()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

//        http.logout().permitAll().logoutSuccessHandler(null);
    }

    @Bean
    public AbstractAuthenticationProcessingFilter jsonAuthenticationFilter() throws Exception {
        JsonAuthenticationFilter filter = new JsonAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new MyLoginSuccessHandler());
        filter.setAuthenticationFailureHandler(new MyLoginFailureHandler());
        return filter;
    }

}
