package com.security.DetailsService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * impl
 *
 * @Author: tongq
 * @Date: 2020/3/12 19:15
 * @since：
 */
public interface CustomUserDetailsService extends UserDetailsService {
    UserDetails loadUserByMobile(String var1) throws UsernameNotFoundException;
    UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;
    UserDetails loadUserByOpenId(String openId) throws UsernameNotFoundException;
}
