package com.security.mapper;

import com.security.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * impl
 *
 * @Author: tongq
 * @Date: 2020/3/3 16:58
 * @since：
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {
}
