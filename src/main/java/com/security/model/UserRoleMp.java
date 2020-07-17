package com.security.model;

import com.security.common.core.BaseMapping;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * @Author: tongq
 * @Date: 2020/3/3 16:41
 * @since：
 */
@Data
@Table(name = "tb_security_user_role_mp")
@EqualsAndHashCode(callSuper = true)
public class UserRoleMp extends BaseMapping {
    @Size(max = 36,message = "角色id不能超过36个字符")
    private String roleId;
    @Size(max = 36,message = "用户id不能超过36个字符")
    private String userId;
}
