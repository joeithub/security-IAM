package com.security.controller;

import com.security.common.http.AjaxResult;
import com.security.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Author: tongq
 * @Date: 2020/3/2 13:53
 * @since：
 */
@RestController
@Slf4j
public class Hello {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/me")
    @Secured("ROLE_用户")
    public Object me() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/details")
    public Object me(@AuthenticationPrincipal UserDetails userDetails) {
       if (userDetails.getUsername().equals("admin")||userDetails.getUsername().equals("tongq")&& Objects.nonNull(userDetails)){
           log.info(userDetails.getUsername());
       }else {
           log.info("不是想打印的用户");
       }
        return userDetails;
    }

    @GetMapping("/user")
    @Secured({"ROLE_ADMIN","ROLE_SUPER"})
    public AjaxResult getUser(){
        return AjaxResult.ok(userMapper.selectAll());
    }

    @GetMapping("/Access_Denied")
    public String deny(){
        return "拒绝访问";
    }

}
