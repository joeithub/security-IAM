package com.security.cfg.security;

import com.security.AuthenticationProvider.OAuth2AuthenticationProvider;
import com.security.DetailsService.CustomUserDetailsServiceImpl;
import com.security.cfg.normal.OAuth2Config;
import com.security.common.ConstantsOauth;
import com.security.common.utils.GsonUtils;
import com.security.filter.OAuth2AuthenticationFilter;
import com.security.handler.UnionAuthenticationFailureHandler;
import com.security.handler.UnionAuthenticationSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;

/**
 * @Author: tongq
 * @Date: 2020/4/15 21:51
 * @since：
 */
@Configuration
@Slf4j
public class OAuth2SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;

    @Autowired
    private UnionAuthenticationSuccessHandler unionAuthenticationSuccessHandler ;

    @Autowired
    private UnionAuthenticationFailureHandler unionAuthenticationFailureHandler;
    
    @Autowired
    private OAuth2Config oAuth2Config;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        OAuth2AuthenticationFilter oauth2AuthenticationFilter = new OAuth2AuthenticationFilter();
        oauth2AuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        oauth2AuthenticationFilter.setAuthenticationSuccessHandler(unionAuthenticationSuccessHandler);
        oauth2AuthenticationFilter.setAuthenticationFailureHandler(unionAuthenticationFailureHandler);
//        //prepare config
//        HashMap<String, HashMap<String,String>> oauthInfo = new HashMap<>();
//        HashMap<String, String> wappIdAndSecret = new HashMap<>();
//        wappIdAndSecret.put(ConstantsOauth.APPID, oAuth2Config.getWechatClientId());
//        wappIdAndSecret.put(ConstantsOauth.SECRET,oAuth2Config.getWechatClientSecret());
//        HashMap<String, String> dappIdAndSecret = new HashMap<>();
//        dappIdAndSecret.put(ConstantsOauth.APPID,oAuth2Config.getDingdingClientId());
//        dappIdAndSecret.put(ConstantsOauth.SECRET,oAuth2Config.getDingdingClientSecret());
//        //set
//        oauthInfo.put(ConstantsOauth.WECHAT,wappIdAndSecret);
//        oauthInfo.put(ConstantsOauth.DINGDING,dappIdAndSecret);
//        //done set
//        OAuth2AuthenticationProvider OAuth2AuthenticationProvider = new OAuth2AuthenticationProvider(oauthInfo);
        OAuth2AuthenticationProvider oAuth2AuthenticationProvider = new OAuth2AuthenticationProvider();
//        oAuth2AuthenticationProvider.setUserDetailsService(userDetailsService);

        http
                .authenticationProvider(oAuth2AuthenticationProvider)
                .addFilterAfter(oauth2AuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
