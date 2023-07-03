//package com.xxl.item.service.impl;
//
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.xxl.item.mapper.ItemMapper;
//import com.xxl.item.pojo.Item;
//import com.xxl.item.pojo.ItemStock;
//import com.xxl.item.service.IItemService;
//import com.xxl.item.service.IItemStockService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * @author xxl
// * @date 2022/12/6 0:41
// */
//@Service
//public class ItemService extends ServiceImpl<ItemMapper, Item> implements IItemService {
//
//    @Autowired
//    private IItemStockService stockService;
//
//    @Override
//    @Transactional
//    public void saveItem(Item item) {
//        // 新增商品
//        save(item);
//        // 新增库存
//        ItemStock stock = new ItemStock();
//        stock.setId(item.getId());
//        stock.setStock(item.getStock());
//        stockService.save(stock);
//    }
//}
