package com.security.common.core;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * impl
 *
 * @Author: tongq
 * @Date: 2020/3/3 17:37
 * @since：
 */
public interface BaseMapper <T extends BaseId > extends Mapper<T>, MySqlMapper<T>, SpecialSqlMapper<T>, ConditionMapper<T> {
}
