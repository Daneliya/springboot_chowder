package com.xxl.x_file_storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxl.x_file_storage.entity.FilePartDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 文件分片信息表，仅在手动分片上传时使用 Mapper 接口
 * </p>
 *
 * @author xxl
 * @since 2024-05-03
 */
@Mapper
public interface FilePartDetailMapper extends BaseMapper<FilePartDetail> {

}
