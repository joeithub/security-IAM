package com.security.model;

import com.security.common.core.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * @Author: tongq
 * @Date: 2020/3/3 16:09
 * @since：
 */
@Table(name = "tb_security_user")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserModel extends BaseModel {
    @Size(max = 32, message = "账号不能超过32个字符")
    private String username;
    @Size(max = 255, message = "密码不能超过255个字符")
    private String password;
    @Size(max = 100, message = "姓名不能超过100个字符")
    private String name;
    @Size(max = 20, message = "手机号不能超过20个字符")
    private String mobile;
    @Size(max=2,message = "用户状态不能超过2个字符")
    private String userState;
    @Size(max = 2,message = "认证类型不能超过2个字符")
    private String authType;
    @Size(max = 2, message = "状态不能超过2个字符")
    private String state;
}
