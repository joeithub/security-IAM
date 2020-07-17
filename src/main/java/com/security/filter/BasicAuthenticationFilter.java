package com.security.filter;

import com.security.AuthenticationToken.BasicAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Author: tongq
 * @Date: 2020/4/17 19:35
 * @since：
 */
public class BasicAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 是否仅 POST 方式
     */
    private boolean postOnly = true;

    public BasicAuthenticationFilter(){
        super(new AntPathRequestMatcher("/login/basic/**", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        Map<String, String> map = new HashMap<>();
        if (! StringUtils.isEmpty(request.getParameter("mobile"))){
            String mobile = request.getParameter("mobile").trim();
            String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
            Pattern p = Pattern.compile(regex);
            if (p.matcher(mobile).matches()){
                 map.put("mobile",mobile);
            }else {
                throw new BadCredentialsException("手机号码格式不正确");
            }
        }
        if (! StringUtils.isEmpty(request.getParameter("email"))){
            String email = request.getParameter("email").trim();
            String regEx1 = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
            Pattern p = Pattern.compile(regEx1);
            if (p.matcher(email).matches()){
                map.put("email",email);
            }
        }

        BasicAuthenticationToken authRequest = new BasicAuthenticationToken(map);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);

    }

    private void setDetails(HttpServletRequest request, BasicAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

}
