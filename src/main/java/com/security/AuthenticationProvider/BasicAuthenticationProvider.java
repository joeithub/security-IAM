package com.security.AuthenticationProvider;

import com.security.AuthenticationToken.BasicAuthenticationToken;
import com.security.DetailsService.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: tongq
 * @Date: 2020/4/17 20:07
 * @since：0.0.1
 */
public class BasicAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BasicAuthenticationToken authenticationToken = (BasicAuthenticationToken) authentication;
        Map principal = (Map<String,String>)authentication.getPrincipal();
        UserDetails userDetails = null;
        if (! StringUtils.isEmpty(principal.get("mobile"))){
            String mobile =(String) principal.get("mobile");
            checkSmsCode(mobile);
            userDetails = userDetailsService.loadUserByMobile(mobile);
        }else
        if (! StringUtils.isEmpty(principal.get("email"))){
            String email =(String) principal.get("email");
            checkEmailCode(email);
            userDetails = userDetailsService.loadUserByEmail(email);
        }else {
            //不是手机也不是邮箱
        }

        // 此时鉴权成功后，应当重新 new 一个拥有鉴权的 authenticationResult 返回
        BasicAuthenticationToken authenticationResult = new BasicAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    //校验手机号
    private void checkSmsCode(String mobile) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String inputCode = request.getParameter("smsCode");
        Map<String, Object> smsCode = (Map<String, Object>) request.getSession().getAttribute("smsCode");
        if(smsCode == null) {
            throw new BadCredentialsException("未检测到申请验证码");
        }
        String applyMobile = (String) smsCode.get("mobile");
        int code = (int) smsCode.get("code");
        if(!applyMobile.equals(mobile)) {
            throw new BadCredentialsException("申请的手机号码与登录手机号码不一致");
        }
        if(code != Integer.parseInt(inputCode)) {
            throw new BadCredentialsException("验证码错误");
        }
    }

    //校验email
    private void checkEmailCode(String email) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String inputCode = request.getParameter("emailCode");

        Map<String, Object> emailCode = (Map<String, Object>) request.getSession().getAttribute("emailCode");
        if(emailCode == null) {
            throw new BadCredentialsException("未检测到申请验证码");
        }
        String applyEmail = (String) emailCode.get("email");
        int code = (int) emailCode.get("code");
        if(!applyEmail.equals(email)) {
            throw new BadCredentialsException("申请的邮箱与登录邮箱号码不一致");
        }
        if(code != Integer.parseInt(inputCode)) {
            throw new BadCredentialsException("验证码错误");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BasicAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
