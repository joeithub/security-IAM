package com.security.common.core;

/**
 * impl
 *
 * @Author: tongq
 * @Date: 2020/3/3 17:50
 * @since：
 */

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

import java.util.List;

/**
 * 通用Mapper接口,特殊方法，批量插入，支持批量插入的数据库都可以使用，例如mysql,h2等
 *
 * @param <T> 不能为空
 * @author liuzh
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface SpecialSqlMapper<T> {

    /**
     * 修改tkmybatis，不限制自动生成主键
     *
     * @param recordList
     * @return
     */
    @Options(useGeneratedKeys = false)
    @InsertProvider(type = SpecialSqlProvider.class, method = "dynamicSQL")
    int insertBatchList(List<? extends T> recordList);

}
