package com.xxl.x_file_storage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 文件分片信息表，仅在手动分片上传时使用
 * </p>
 *
 * @author xxl
 * @since 2024-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("file_part_detail")
public class FilePartDetail extends Model<FilePartDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 分片id
     */
    private String id;
    /**
     * 存储平台
     */
    private String platform;
    /**
     * 上传ID，仅在手动分片上传时使用
     */
    @TableField("upload_id")
    private String uploadId;
    /**
     * 分片 ETag
     */
    @TableField("e_tag")
    private String eTag;
    /**
     * 分片号。每一个上传的分片都有一个分片号，一般情况下取值范围是1~10000
     */
    @TableField("part_number")
    private Integer partNumber;
    /**
     * 文件大小，单位字节
     */
    @TableField("part_size")
    private Long partSize;
    /**
     * 哈希信息
     */
    @TableField("hash_info")
    private String hashInfo;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

}
