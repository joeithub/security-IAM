package com.security.cfg.security;

import com.security.DetailsService.CustomUserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * @Author: tongq
 * @Date: 2020/3/17 16:02
 * @since：
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class FinalWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    @Autowired
    private FormLoginSecurityConfig formLoginSecurityConfig;

    @Autowired
    private OAuth2SecurityConfig OAuth2SecurityConfig;

    @Autowired
    private BasicSecurityConfig basicSecurityConfig;

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
                .apply(formLoginSecurityConfig)
                .and()
                .apply(basicSecurityConfig)
                .and()
                .apply(OAuth2SecurityConfig)
                .and()
                .authorizeRequests()
                // 如果有允许匿名的url，填在下面
                .antMatchers("/getVerifyCode").permitAll()
//                        .antMatchers("/druid/**").access("hasRole('ROLE_ADMIN')")
//                        .antMatchers("/user").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER')")
//                        .antMatchers("/hello").permitAll()
                // 放行登录
                .antMatchers("/login/basic/**").permitAll()
                .antMatchers("/login/oauth/**").permitAll()
                .antMatchers("/login/code/**").permitAll()
                .anyRequest().authenticated()
                .and()
                // 开启表单认证
                .formLogin()
//                        // 地址写的是 映射的路径
                .loginPage("/login.html")
//                        // 必须添加
                .loginProcessingUrl("/login").permitAll()
                // 第二个参数，如果不写成true，则默认登录成功以后，访问之前被拦截的页面，而非去我们规定的页面
                .defaultSuccessUrl("/index.html", true)
//                        .defaultSuccessUrl("/hello", true)
                // 指定authenticationDetailsSource
                .authenticationDetailsSource(authenticationDetailsSource)
                .and()
                //自定义过滤器 可用于验证码验证，在只需要校验用户名密码没有额外信息时应用轻量级 想在哪种认证方式前执行就把那种认证过滤字节码传入第二个参数
//                        .addFilterBefore(new VerifyFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/logout")
                .and()
                //记住我，自动登录
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                // 有效时间：单位s token有效期
                .tokenValiditySeconds(60)
                //记住我调用的查用户service
                .userDetailsService(userDetailsService)
                //记住我结束
                .and()
                .csrf()
                .disable()
                .httpBasic()
                .and().exceptionHandling().accessDeniedPage("/Access_Denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**","/fonts/**","/images/**","/vendor/**");
    }

    /**
     * 用于自动登录
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 如果token表不存在，使用下面语句可以初始化该表；若存在，请注释掉这条语句，否则会报错。
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_SUPER > ROLE_ADMIN > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

}
