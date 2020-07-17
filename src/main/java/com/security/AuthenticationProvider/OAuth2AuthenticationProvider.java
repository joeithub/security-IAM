package com.security.AuthenticationProvider;

import com.alibaba.fastjson.JSONObject;
import com.security.AuthenticationToken.OAuth2AuthenticationToken;
import com.security.DetailsService.CustomUserDetailsService;
import com.security.cfg.normal.OAuth2Config;
import com.security.common.ConstantsOauth;
import com.security.common.utils.GsonUtils;
import com.security.common.utils.PostUtil;
import com.security.vo.DingDingUserInfoVo;
import com.security.vo.FeiShuResultVo;
import com.security.vo.FeiShuUserInfoVo;
import com.security.vo.WechatUserInfoVo;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Author: tongq
 * @Date: 2020/4/15 09:08
 * @since：0.0.1
 */
@Slf4j
@NoArgsConstructor
@Component
public class OAuth2AuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OAuth2Config oAuth2Config;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        Object principal = authenticationToken.getPrincipal();
        Map<String, Object> map = GsonUtils.jsonToMaps(GsonUtils.convertToString(principal));
        String code = (String) map.get("code");
        String state = (String) map.get("state");
        String type = (String)map.get("type");
        if (! "SECURITYIAM".equals(state)){
            throw new BadCredentialsException("非正常访问");
        }
        UserDetails userDetails;
        switch (type){
            case ConstantsOauth.WECHAT:
                userDetails = getWeChatAuthentication(authenticationToken, code, type);
                break;
            case ConstantsOauth.DINGDING:
                userDetails = getDingDingAuthentication(authenticationToken, code, type);
                break;
            case ConstantsOauth.QYWECHAT:
                userDetails = getQyWeChatAuthentication(authenticationToken, code, type);
                break;
            case ConstantsOauth.FEISHU:
                userDetails = getFeiShuAuthentication(authenticationToken, code, type);
                break;
            default:
                userDetails = null;
        }
        if (userDetails == null) {
            throw new BadCredentialsException("登录异常！");
        }
        OAuth2AuthenticationToken authenticationResult = new OAuth2AuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    private UserDetails getDingDingAuthentication(OAuth2AuthenticationToken authenticationToken, String code, String type) {
        //TODO DINGDING
        String replace =ConstantsOauth.DING_ACCESS_TOKEN_URL
                .replace(ConstantsOauth.APPID,oAuth2Config.getDingdingClientId())
                .replace(ConstantsOauth.SECRET,oAuth2Config.getDingdingClientSecret());
        String forObject = restTemplate.getForObject(replace, String.class);
        Map<String, Object> accessToken = GsonUtils.jsonToMaps(forObject);
        String access_token = (String)accessToken.get("access_token");
        if (! StringUtils.isEmpty(access_token)){
            //获取永久code
            String persistent_code_url = ConstantsOauth.DING_PERSISTENT_CODE_URL.replace(ConstantsOauth.ACCESS_TOKEN, access_token);
            JSONObject persistent = new JSONObject();
            persistent.put("tmp_auth_code",code);
            log.info("ding code: " + code);
            String post = PostUtil.post(persistent, persistent_code_url);
            DingDingUserInfoVo dingDingUserInfoVo = GsonUtils.convertToBean(post, DingDingUserInfoVo.class);
            String sns_token = ConstantsOauth.DING_SNS_TOKEN_URL.replace(ConstantsOauth.ACCESS_TOKEN, access_token);
            JSONObject requestBody = new JSONObject();
            requestBody.put("openid",dingDingUserInfoVo.getOpenid());
            requestBody.put("persistent_code",dingDingUserInfoVo.getPersistent_code());
            String snsTokenString = PostUtil.post(requestBody, sns_token);
            DingDingUserInfoVo snsToken = GsonUtils.convertToBean(snsTokenString, DingDingUserInfoVo.class);
            String userInfo = ConstantsOauth.DING_GET_USER_INFO.replace(ConstantsOauth.SNS_TOKEN, snsToken.getSns_token());
            DingDingUserInfoVo user_info = restTemplate.getForObject(userInfo, DingDingUserInfoVo.class);
            log.info(GsonUtils.convertToString(user_info));
            if (StringUtils.isEmpty(dingDingUserInfoVo.getOpenid())){
                throw new BadCredentialsException("AUTHENTICATION FAILURE");
            }
            UserDetails userDetails = null;
            try {
                userDetails = userDetailsService.loadUserByOpenId(dingDingUserInfoVo.getOpenid());
            }catch (Exception e){
                throw new UsernameNotFoundException("Please Bind User First");
            }
            return userDetails;
//            OAuth2AuthenticationToken authenticationResult = new OAuth2AuthenticationToken(userDetails, userDetails.getAuthorities());
//            authenticationResult.setDetails(authenticationToken.getDetails());
//            return authenticationResult;
        }
        throw new BadCredentialsException("CAN NOT OBTAIN ACCESS_TOKEN");
    }

    private UserDetails getWeChatAuthentication(OAuth2AuthenticationToken authenticationToken, String code, String type) {
        //TODO WECHAT
        String replace = ConstantsOauth.OPEN_ACCESS_TOKEN_URL
                .replace(ConstantsOauth.APPID, oAuth2Config.getWechatClientId())
                .replace(ConstantsOauth.SECRET, oAuth2Config.getWechatClientSecret().replace("CODE", code));
        String forObject = restTemplate.getForObject(replace, String.class);
        Map<String, Object> openIdAndAccessToken = GsonUtils.jsonToMaps(forObject);
        String openid = (String)openIdAndAccessToken.get("openid");
        String access_token = (String) openIdAndAccessToken.get("access_token");
        WechatUserInfoVo wechatUserInfoVo = null;
        if (!StringUtils.isEmpty(openid) &&! StringUtils.isEmpty(access_token)){
            String userInfo = ConstantsOauth.OPEN_URL_GET_USER_INFO
                    .replace("OPENID",openid).replace(ConstantsOauth.ACCESS_TOKEN,access_token);
            wechatUserInfoVo = restTemplate.getForObject(userInfo, WechatUserInfoVo.class);
            log.info(GsonUtils.convertToString(wechatUserInfoVo));
        }
        if (StringUtils.isEmpty(wechatUserInfoVo.getOpenid())){
            throw new BadCredentialsException("AUTHENTICATION FAILURE");
        }
        UserDetails userDetails = null;
        try {
            userDetails = userDetailsService.loadUserByOpenId(wechatUserInfoVo.getOpenid());
        } catch (UsernameNotFoundException e){
            throw new UsernameNotFoundException("Please Bind User First");
        }
        // 此时鉴权成功后，应当重新 new 一个拥有鉴权的 authenticationResult 返回
//        OAuth2AuthenticationToken authenticationResult = new OAuth2AuthenticationToken(userDetails, userDetails.getAuthorities());
//        authenticationResult.setDetails(authenticationToken.getDetails());
//        return authenticationResult;
        return userDetails;
    }

    private UserDetails getQyWeChatAuthentication(OAuth2AuthenticationToken authenticationToken, String code, String type) {
        log.info("【企业微信扫码登录】");
        String accessTokenUrl = ConstantsOauth.QIYE_ACCESS_TOKEN_URL
                .replace(ConstantsOauth.APPID, oAuth2Config.getQywechatClientId())
                .replace(ConstantsOauth.SECRET, oAuth2Config.getQywechatClientSecret());
        String forObject = restTemplate.getForObject(accessTokenUrl, String.class);
        Map<String, Object> accessToken = GsonUtils.jsonToMaps(forObject);
        String access_token = (String) accessToken.get("access_token");
        if (!StringUtils.isEmpty(access_token)) {
            String userInfoUrl = ConstantsOauth.QIYE_GET_USER_INFO.replace(ConstantsOauth.ACCESS_TOKEN, access_token)
                    .replace(ConstantsOauth.CODE, code);
            String userInfo = restTemplate.getForObject(userInfoUrl, String.class);
            Map<String, Object> mapInfo = GsonUtils.jsonToMaps(userInfo);
            String userId = (String) mapInfo.get("UserId");
            if (!StringUtils.isEmpty(userId)) {
                String oauthid = oAuth2Config.getQywechatClientId() + "_" + userId;
                UserDetails userDetails = null;
                try {
                    userDetails = userDetailsService.loadUserByOpenId(oauthid);
                } catch (Exception e) {
                    log.info("【企业微信：】未找到用户");
                    //将信息存到redis 把oauthid 塞到session传给绑定用
//						redisTemplate.opsForValue().set(oauthid,userInfo,Duration.ofMinutes(2));
                    mapInfo.put("qrType", "03");
                    throw new UsernameNotFoundException(oauthid + ": USER NOT FOUND");
                }
                return userDetails;
            } else {
                throw new BadCredentialsException("AUTHENTICATION FAILURE");
            }
        } else {
            throw new BadCredentialsException("CAN NOT OBTAIN ACCESS_TOKEN");
        }
    }

    private UserDetails getFeiShuAuthentication(OAuth2AuthenticationToken authenticationToken, String code, String type) {
        log.info("【飞书扫码登录】");
        String accessTokenUrl = ConstantsOauth.FEISHU_ACCESS_TOKEN_URL;
        JSONObject requestBody = new JSONObject();
		requestBody.put("app_id",oAuth2Config.getFeishuClientId());
		requestBody.put("app_secret",oAuth2Config.getFeishuClientSecret());
        requestBody.put("grant_type", "authorization_code");
        requestBody.put("code", code);
        String post = PostUtil.post(requestBody, accessTokenUrl);
        FeiShuResultVo feiShuResultVo = GsonUtils.convertToBean(post, FeiShuResultVo.class);
        FeiShuUserInfoVo data = feiShuResultVo.getData();
        if (null == data || StringUtils.isEmpty(data.getOpen_id())) {
            throw new BadCredentialsException("AUTHENTICATION FAILURE");
        }
        String openId = data.getOpen_id();
        UserDetails userDetails = null;
        try {
            userDetails = userDetailsService.loadUserByOpenId(openId);
        } catch (UsernameNotFoundException e) {
            log.info("【飞书】未找到用户");
            //把对象 塞到redis
            throw new UsernameNotFoundException(openId + ": USER NOT FOUND");
        }
        return userDetails;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2AuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

}
