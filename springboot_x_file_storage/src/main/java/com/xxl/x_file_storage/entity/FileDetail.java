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
 * 文件记录表
 * </p>
 *
 * @author xxl
 * @since 2024-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("file_detail")
public class FileDetail extends Model<FileDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     */
    private String id;
    /**
     * 文件访问地址
     */
    private String url;
    /**
     * 文件大小，单位字节
     */
    private Long size;
    /**
     * 文件名称
     */
    private String filename;
    /**
     * 原始文件名
     */
    @TableField("original_filename")
    private String originalFilename;
    /**
     * 基础存储路径
     */
    @TableField("base_path")
    private String basePath;
    /**
     * 存储路径
     */
    private String path;
    /**
     * 文件扩展名
     */
    private String ext;
    /**
     * MIME类型
     */
    @TableField("content_type")
    private String contentType;
    /**
     * 存储平台
     */
    private String platform;
    /**
     * 缩略图访问路径
     */
    @TableField("th_url")
    private String thUrl;
    /**
     * 缩略图名称
     */
    @TableField("th_filename")
    private String thFilename;
    /**
     * 缩略图大小，单位字节
     */
    @TableField("th_size")
    private Long thSize;
    /**
     * 缩略图MIME类型
     */
    @TableField("th_content_type")
    private String thContentType;
    /**
     * 文件所属对象id
     */
    @TableField("object_id")
    private String objectId;
    /**
     * 文件所属对象类型，例如用户头像，评价图片
     */
    @TableField("object_type")
    private String objectType;
    /**
     * 文件元数据
     */
    private String metadata;
    /**
     * 文件用户元数据
     */
    @TableField("user_metadata")
    private String userMetadata;
    /**
     * 缩略图元数据
     */
    @TableField("th_metadata")
    private String thMetadata;
    /**
     * 缩略图用户元数据
     */
    @TableField("th_user_metadata")
    private String thUserMetadata;
    /**
     * 附加属性
     */
    private String attr;
    /**
     * 文件ACL
     */
    @TableField("file_acl")
    private String fileAcl;
    /**
     * 缩略图文件ACL
     */
    @TableField("th_file_acl")
    private String thFileAcl;
    /**
     * 哈希信息
     */
    @TableField("hash_info")
    private String hashInfo;
    /**
     * 上传ID，仅在手动分片上传时使用
     */
    @TableField("upload_id")
    private String uploadId;
    /**
     * 上传状态，仅在手动分片上传时使用，1：初始化完成，2：上传完成
     */
    @TableField("upload_status")
    private Integer uploadStatus;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

}
