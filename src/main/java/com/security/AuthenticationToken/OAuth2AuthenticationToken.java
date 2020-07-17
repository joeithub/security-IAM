package com.security.AuthenticationToken;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author: tongq
 * @Date: 2020/4/14 20:39
 * @since：
 */
public class OAuth2AuthenticationToken extends AbstractAuthenticationToken {
    /**
     * request参数中获取的信息
     */
    private final Object principal;

    /**
     * 构建一个没有鉴权的 WechatAuthenticationToken
     */
    public OAuth2AuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(false);
    }


    /**
     * 构建拥有鉴权的 SmsCodeAuthenticationToken
     */
    public OAuth2AuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        // must use super, as we override
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    //这个一定要有返回值不然校验信息拿不到
    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
