package com.security.controller;

import com.security.cfg.normal.OAuth2Config;
import com.security.common.http.AjaxResult;
import com.security.common.utils.CreateCodeUtil;
import com.security.common.utils.SendEmailUtil;
import com.security.common.utils.SendSmsUtil;
import com.security.exception.RequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: tongq
 * @Date: 2020/4/13 19:03
 * @since：
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class Oauth2LoginAction {

    @Autowired
    private OAuth2Config OAuth2Config;

    @Autowired
    private SendSmsUtil sendSmsUtil;

    @Value("${callback.domain}")
    private String callbackDomain;

    @GetMapping("/oauth/{oauth}")
    public ModelAndView wechatqr(@PathVariable String oauth, HttpServletResponse response){
        log.info("进入qr方法");
        Map<String, String> clientInfo = OAuth2Config.getClientInfo(oauth);
        if(! "qywechat".equals(oauth)){
            StringBuilder qrUrl = new StringBuilder(clientInfo.get("qrUrl"));
            qrUrl.append("?appid=" + clientInfo.get("clientId"));
            qrUrl.append("&redirect_uri=https://" + callbackDomain + "/api/sso/callback/" + oauth);
            qrUrl.append("&scope=snsapi_login");
            qrUrl.append("&state=" + "SECURITYIAM");
            qrUrl.append("&response_type=code");
            return new ModelAndView("redirect:" + qrUrl.toString());
        }else if ("qywechat".equals(oauth)){
            StringBuilder qrUrl = new StringBuilder(clientInfo.get("qrUrl"));
            qrUrl.append("?appid=" + clientInfo.get("clientId"));
            qrUrl.append("&agentid=" + clientInfo.get("agentId"));
            qrUrl.append("&scope=" + "snsapi_userinfo");
            qrUrl.append("&redirect_uri=https://" + callbackDomain  + "/api/sso/callback/" + oauth);
            qrUrl.append("&state="  + "SECURITYIAM");
            return new ModelAndView("redirect:" + qrUrl.toString());
        }else if ("feishu".equals(oauth)){
            String qrUrl = clientInfo.get("qrUrl") + "?redirect_uri=https://" + callbackDomain + "/api/sso/callback/" + oauth
                    + "&app_id=" + clientInfo.get("clientId")
                    + "&state=" + "SECURITYIAM";
            return new ModelAndView("redirect:" + qrUrl);
        }
        else {
            return new ModelAndView("redirect:" + "/login");
        }
    }

    @GetMapping("/code/sms/{mobile}")
    public AjaxResult sendCode(@PathVariable String mobile, HttpSession session){
        int code = CreateCodeUtil.getCode();
        log.info(String.valueOf(code));
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile",mobile);
        map.put("code",code);
        session.setAttribute("smsCode",map);
        log.info(String.valueOf(code));
        try {
            sendSmsUtil.sendSms(mobile, String.valueOf(code));
            return AjaxResult.ok();
        } catch (Exception e) {
            log.error("异常",e);
            throw new RequestException("4001","获取验证码失败");
        }
    }

    @GetMapping("/code/email/{email}")
    public AjaxResult sendEmailCode(@PathVariable String email, HttpSession session){
        int code = CreateCodeUtil.getCode();
        log.info(String.valueOf(code));
        HashMap<String, Object> map = new HashMap<>();
        map.put("email",email);
        map.put("code",code);
        session.setAttribute("emailCode",map);
        log.info(String.valueOf(code));
        String content = String.format(SendEmailUtil.getMailContent(),code,SendEmailUtil.getValidate());
        try {
            SendEmailUtil.sendMail(email,content,SendEmailUtil.getTitle());
            return AjaxResult.ok();
        } catch (Exception e) {
            log.error("异常",e);
            throw new RequestException("4001","获取验证码失败");
        }
    }
}
