package com.xxl.vo;

import lombok.Data;

import java.util.List;

/**
 * @author xxl
 * @date 2022/12/6 0:41
 */
@Data
public class PageDTO {
    private Long total;
    private List<Item> list;

    public PageDTO() {
    }

    public PageDTO(Long total, List<Item> list) {
        this.total = total;
        this.list = list;
    }
}
