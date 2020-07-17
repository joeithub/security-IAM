//package com.security.cfg.security;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//
///**
// * @Author: tongq
// * @Date: 2020/4/23 09:51
// * @since：
// */
//
//@Slf4j
//@Configuration
//@EnableAuthorizationServer
//public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//        oauthServer
//                .tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()")
//                .allowFormAuthenticationForClients();
//    }
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.authenticationManager(authenticationManager);
//        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()//数据存在内存中
//                .withClient("demoApp")//授权服务器id
//                .secret("demoAppSecret")//授权密码
//                .authorizedGrantTypes("authorization_code", "password", "refresh_token")//获取模式
//                .scopes("all")
//                .resourceIds("oauth2-resource")//资源服务器id
//                .accessTokenValiditySeconds(1200)//token的存在时间
//                .refreshTokenValiditySeconds(50000);//刷新token的token的存在时间
//    }
//}
