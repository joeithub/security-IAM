package com.security.common.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

/**
 * @Author: tongq
 * @Date: 2020/3/3 16:33
 * @since：
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseModel extends BaseId{
    @Size(max=36,message = "创建人不能超过36个字符")
    private String creator;
    private String createTime;
    @Size(max=36,message = "更新人不能超过36个字符")
    private String updator;
    private String updateTime;
}
