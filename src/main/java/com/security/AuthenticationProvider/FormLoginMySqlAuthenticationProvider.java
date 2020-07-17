package com.security.AuthenticationProvider;

import com.security.DetailsService.CustomUserDetailsServiceImpl;
import com.security.WebAuthenticationDetails.FormLoginWebAuthenticationDetails;
import com.security.common.utils.VerifyCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * MySQL数据库认证逻辑由AuthenticationManager管理，
 * ProviderManager ，该类是 AuthenticationManager 的实现类
 * 他会获取所有AuthenticationProvider （认证提供者）选择适合当前的认证提供者
 * 传统表单登录的 AuthenticationProvider 主要是由 AbstractUserDetailsAuthenticationProvider 来进行处理的
 * 首先通过 retrieveUser() 方法读取到数据库中的用户信息 user = retrieveUser(username,(UsernamePasswordAuthenticationToken) authentication);
 * retrieveUser() 的具体实现在 DaoAuthenticationProvider 中 UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
 * 我们自己实现个UserDetailsService 的实现类并提供loadUserByUsername方法
 * @Author: tongq
 * @Date: 2020/3/3 20:24
 * @since：0.0.1
 */
@Component
@Slf4j
public class FormLoginMySqlAuthenticationProvider implements AuthenticationProvider {
  @Autowired
  private CustomUserDetailsServiceImpl userDetailService;

//    /**
//     * 无验证码时
//     * @param authentication
//     * @return
//     * @throws AuthenticationException
//     */
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        //获取用户输入的用户名和密码
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//        log.info(password);
//        //获取封装用户信息的对象
//        UserDetails userDetails = userDetailService.loadUserByUsername(username);
//        //进行密码的比对
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        log.info(bCryptPasswordEncoder.encode(password));
//        boolean flag = bCryptPasswordEncoder.matches(password, userDetails.getPassword());
//        // 校验通过
//        if (flag){
//            // 将权限信息也封装进去
//            log.info(username + password + userDetails.getAuthorities());
//            return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
//        }
//        // 验证失败返回 null
//        return null;
//    }

    /**
     * 有验证码时
      * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取用户输入的用户名和密码
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        log.info(password);

        //1 验证码的校验
        FormLoginWebAuthenticationDetails details = (FormLoginWebAuthenticationDetails) authentication.getDetails();
        //获取到验证码
        String verifyCode = details.getVerifyCode();
        //校验验证码
        if(!VerifyCodeUtil.validateVerify(verifyCode)) {
            throw new DisabledException("验证码输入错误");
        }
        //验证码校验结束
        UserDetails userDetails = null;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        if(p.matcher(username).matches()){
            // 邮箱号登录
            userDetails = userDetailService.loadUserByEmail(username);
        }else {
            //2 用户名密码校验
            //获取封装用户信息的对象
            userDetails = userDetailService.loadUserByUsername(username);
        }
        //进行密码的比对
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        log.info(bCryptPasswordEncoder.encode(password));
        boolean flag = bCryptPasswordEncoder.matches(password, userDetails.getPassword());
        // 校验通过

        if (flag){
            // 将权限信息也封装进去
            log.info(username + password + userDetails.getAuthorities());
            return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
        }
        // 验证失败返回 null
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 判断 authentication 是不是 UsernamePasswordAuthenticationToken 的子类或子接口
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
