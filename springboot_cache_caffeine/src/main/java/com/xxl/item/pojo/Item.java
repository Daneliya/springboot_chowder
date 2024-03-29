//package com.xxl.item.pojo;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import lombok.Data;
//
//
//import javax.persistence.Id;
//import java.util.Date;
//
///**
// * @author xxl
// * @date 2022/12/6 0:41
// */
//@Data
//@TableName("tb_item")
//public class Item {
//    @TableId(type = IdType.AUTO)
//    @Id
//    private Long id;//商品id
//    private String name;//商品名称
//    private String title;//商品标题
//    private Long price;//价格（分）
//    private String image;//商品图片
//    private String category;//分类名称
//    private String brand;//品牌名称
//    private String spec;//规格
//    private Integer status;//商品状态 1-正常，2-下架
//    private Date createTime;//创建时间
//    private Date updateTime;//更新时间
//    @TableField(exist = false)
//    @Transient
//    private Integer stock;
//    @TableField(exist = false)
//    @Transient
//    private Integer sold;
//}
