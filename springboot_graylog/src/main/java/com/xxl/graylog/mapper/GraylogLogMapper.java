package com.xxl.graylog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxl.graylog.entity.GraylogLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 记录日志内容 Mapper 接口
 * </p>
 *
 * @author xxl
 * @since 2025-06-23
 */
@Mapper
public interface GraylogLogMapper extends BaseMapper<GraylogLog> {

}
