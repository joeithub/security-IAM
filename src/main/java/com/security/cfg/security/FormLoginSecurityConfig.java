package com.security.cfg.security;

import com.security.AuthenticationProvider.FormLoginMySqlAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * @Author: tongq
 * @Date: 2020/3/3 20:49
 * @since：
 */
@Configuration
//public class FormLoginSecurityConfig extends WebSecurityConfigurerAdapter {
public class FormLoginSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private FormLoginMySqlAuthenticationProvider formLoginMySqlAuthenticationProvider;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(formLoginMySqlAuthenticationProvider);
    }

}
