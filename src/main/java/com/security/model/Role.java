package com.security.model;

import com.security.common.core.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * @Author: tongq
 * @Date: 2020/3/3 16:25
 * @since：
 */
@Table(name = "tb_security_role")
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseModel {
    @Size(max = 32,message = "角色名不能超过32个字符")
    private String name;
    @Size(max = 255,message = "角色描述不能超过255个字符")
    private String description;
    @Size(max = 2,message = "状态不能超过2个字符")
    private String state;
}
