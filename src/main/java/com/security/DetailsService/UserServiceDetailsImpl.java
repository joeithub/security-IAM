//package com.security.DetailsService;
//
//import com.security.mapper.RoleMapper;
//import com.security.mapper.UserMapper;
//import com.security.mapper.UserRoleMapper;
//import com.security.model.Role;
//import com.security.model.UserModel;
//import com.security.model.UserRoleMp;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Author: tongq
// * @Date: 2020/3/3 19:59
// * @since：
// */
//@Service
//@Slf4j
//public class UserServiceDetailsImpl implements UserDetailsService {
//    @Autowired
//    private UserMapper userMapper;
//    @Autowired
//    private RoleMapper roleMapper;
//    @Autowired
//    private UserRoleMapper userRoleMapper;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //查数据库里的用户
//        UserModel userModel = new UserModel();
//        userModel.setUsername(username);
//        UserModel user = userMapper.selectOne(userModel);
//        if (null == user){
//            throw new UsernameNotFoundException("用户不存在");
//        }
//        //如果存在，查关系表，找出所有的角色id
//        UserRoleMp userRoleMp = new UserRoleMp();
//        userRoleMp.setUserId(user.getId());
//        List<UserRoleMp> roleMps = userRoleMapper.select(userRoleMp);
//        //根据roleId找出所有的角色
//        List<String> roles = new ArrayList<>();
//        roleMps.forEach(x -> {
//            Role role = new Role();
//            role.setId(x.getRoleId());
//            Role one = roleMapper.selectOne(role);
//            roles.add(one.getName());
//        });
//        // 将角色添加到授权
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (int i = 0; i < roles.size(); i++) {
//            authorities.add(new SimpleGrantedAuthority(roles.get(i)));
//        }
//        log.info("获取登录用户已具有的权限：{}", authorities.toString());
//        // 构建 Security 的 User 对象
//        User userDetail = new User(user.getName(), user.getPassword(), authorities);
//        return userDetail;
//    }
//}
