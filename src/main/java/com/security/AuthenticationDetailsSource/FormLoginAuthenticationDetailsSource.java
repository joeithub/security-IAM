package com.security.AuthenticationDetailsSource;

import com.security.WebAuthenticationDetails.FormLoginWebAuthenticationDetails;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 *自定义了WebAuthenticationDetails，
 * 我们还需要将其放入 AuthenticationDetailsSource 中来替换原本的 WebAuthenticationDetails ，
 * 因此还得实现自定义 AuthenticationDetailsSource ：
 *该类内容将原本的 WebAuthenticationDetails 替换为了我们的 FormLoginWebAuthenticationDetails。
 * 然后我们将 FormLoginAuthenticationDetailsSource 注入Spring Security中，替换掉默认的 AuthenticationDetailsSource。
 * 修改 WebSecurityConfig，将其注入，然后在config()中使用 authenticationDetailsSource(authenticationDetailsSource)方法来指定它。
 * @Author: tongq
 * @Date: 2020/3/12 14:55
 * @since：0.0.1
 */

@Component("formLoginAuthenticationDetailsSource")
public class FormLoginAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new FormLoginWebAuthenticationDetails(request);
    }
}
