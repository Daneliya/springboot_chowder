package com.xxl.x_file_storage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxl.x_file_storage.entity.FilePartDetail;
import com.xxl.x_file_storage.mapper.FilePartDetailMapper;
import com.xxl.x_file_storage.service.FilePartDetailService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件分片信息表，仅在手动分片上传时使用 服务实现类
 * </p>
 *
 * @author xxl
 * @since 2024-05-03
 */
@Service
public class FilePartDetailServiceImpl extends ServiceImpl<FilePartDetailMapper, FilePartDetail> implements FilePartDetailService {

}
