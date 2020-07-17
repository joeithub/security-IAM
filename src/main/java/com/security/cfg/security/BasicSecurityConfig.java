package com.security.cfg.security;

import com.security.AuthenticationProvider.BasicAuthenticationProvider;
import com.security.DetailsService.CustomUserDetailsServiceImpl;
import com.security.filter.BasicAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: tongq
 * @Date: 2020/4/17 20:35
 * @since：
 */
@Configuration
public class BasicSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;

//    @Autowired
//    private UnionAuthenticationSuccessHandler unionAuthenticationSuccessHandler ;
//
//    @Autowired
//    private UnionAuthenticationFailureHandler unionAuthenticationFailureHandler;

        @Override
    public void configure(HttpSecurity http) throws Exception {
        BasicAuthenticationFilter BasicAuthenticationFilter = new BasicAuthenticationFilter();
        BasicAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
//        BasicAuthenticationFilter.setAuthenticationSuccessHandler(unionAuthenticationSuccessHandler);
//        BasicAuthenticationFilter.setAuthenticationFailureHandler(unionAuthenticationFailureHandler);

        BasicAuthenticationProvider basicAuthenticationProvider = new BasicAuthenticationProvider();
        basicAuthenticationProvider.setUserDetailsService(userDetailsService);

        http
                .authenticationProvider(basicAuthenticationProvider)
                //先走用户名密码校验
                .addFilterAfter(BasicAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
