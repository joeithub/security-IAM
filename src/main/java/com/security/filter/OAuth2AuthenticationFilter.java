package com.security.filter;

import com.security.AuthenticationToken.OAuth2AuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: tongq
 * @Date: 2020/4/13 18:58
 * @since：
 */
@Slf4j
public class OAuth2AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private boolean postOnly = false;

    public OAuth2AuthenticationFilter() {
        // 回调地址 微信钉钉登录的请求 get方式的 /login/wechat or dingding
        // super(new AntPathRequestMatcher("/api/user/callback/wechat*", "GET"));
        super(new AntPathRequestMatcher("/api/user/callback/**", "GET"));
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request , HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (postOnly && ! request.getMethod().equals("GET")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        Map<String, String> map = obtainParams(request);
        if (CollectionUtils.isEmpty(map)){
            throw  new BadCredentialsException("AUTHENTICATION FAILURE");
        }
        log.info("code&state: {}",map);
        OAuth2AuthenticationToken authRequest = new OAuth2AuthenticationToken(map);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected Map<String, String> obtainParams(HttpServletRequest request) {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("code",request.getParameter("code"));
        parameterMap.put("state",request.getParameter("state"));
        //第三方 wechat/dingding ...
        parameterMap.put("type",request.getRequestURI().replace("/api/user/callback/",""));
        return parameterMap;
    }
    protected void setDetails(HttpServletRequest request, OAuth2AuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
