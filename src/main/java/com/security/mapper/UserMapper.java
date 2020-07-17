package com.security.mapper;

import com.security.model.UserModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * impl
 *
 * @Author: tongq
 * @Date: 2020/3/3 16:56
 * @since：
 */
@Repository
public interface UserMapper extends BaseMapper<UserModel> {
    @Select("select * from tb_security_user where username = #{username}")
    UserModel findUserByUsername(@Param("username") String username);
    @Select("select * from tb_security_user where mobile = #{mobile}")
    UserModel findUserByMobile(@Param("mobile") String mobile);
    @Select("select * from tb_security_user where email = #{email}")
    UserModel findUserByEmail(@Param("email") String email);
    @Select("select * from tb_security_user where id = (select user_id from tb_security_oauth2_user where oauthid = #{openId})")
    UserModel findUserByOpenId(@Param("openId") String openId);
}
