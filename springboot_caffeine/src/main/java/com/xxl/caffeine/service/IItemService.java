package com.xxl.caffeine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxl.caffeine.pojo.Item;

/**
 * @author xxl
 * @date 2022/12/6 0:41
 */
public interface IItemService extends IService<Item> {

    /**
     * 保存商品
     *
     * @param item
     */
    void saveItem(Item item);

}
