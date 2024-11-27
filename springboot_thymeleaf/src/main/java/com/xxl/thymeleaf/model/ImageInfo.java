package com.xxl.thymeleaf.model;

import lombok.Data;

/**
 * @Author: xxl
 * @Date: 2024/11/27 16:40
 */
@Data
public class ImageInfo {

    private String images;
    private String title;

    public ImageInfo(String images, String title) {
        this.images = images;
        this.title = title;
    }
}
