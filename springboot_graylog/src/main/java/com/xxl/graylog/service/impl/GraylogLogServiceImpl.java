package com.xxl.graylog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxl.graylog.entity.GraylogLog;
import com.xxl.graylog.mapper.GraylogLogMapper;
import com.xxl.graylog.service.GraylogLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 记录日志内容 服务实现类
 * </p>
 *
 * @author xxl
 * @since 2025-06-23
 */
@Service
public class GraylogLogServiceImpl extends ServiceImpl<GraylogLogMapper, GraylogLog> implements GraylogLogService {

}
