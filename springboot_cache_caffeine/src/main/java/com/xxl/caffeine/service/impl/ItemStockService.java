package com.xxl.caffeine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxl.caffeine.mapper.ItemStockMapper;
import com.xxl.vo.ItemStock;
import com.xxl.caffeine.service.IItemStockService;
import org.springframework.stereotype.Service;

/**
 * @author xxl
 * @date 2022/12/6 0:41
 */
@Service
public class ItemStockService extends ServiceImpl<ItemStockMapper, ItemStock> implements IItemStockService {
}
