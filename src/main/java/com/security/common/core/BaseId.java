package com.security.common.core;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * @Author: tongq
 * @Date: 2020/3/3 16:31
 * @since：
 */
@Data
public class BaseId {
    @Size(max = 36,message = "id字符不能超过36个字符")
    @Id
    private String id;
}
