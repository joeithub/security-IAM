package com.security.WebAuthenticationDetails;

import lombok.Data;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取用户登录时携带的额外信息
 * 除了用户名和密码外如果还需要其他的信息校验可以自定义details类继承WebAuthenticationDetails
 * @Author: tongq
 * @Date: 2020/3/12 14:46
 * @since：
 */


@Data
public class FormLoginWebAuthenticationDetails extends WebAuthenticationDetails {
    private static final long serialVersionUID = 6975601077710753878L;
    private final String verifyCode;
    public FormLoginWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        // verifyCode为页面中验证码的name
        verifyCode = request.getParameter("verifyCode");
    }
}
