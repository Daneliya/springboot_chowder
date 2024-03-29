//package com.xxl.item.config;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.xxl.item.service.IItemService;
//import com.xxl.item.service.IItemStockService;
//import com.xxl.item.pojo.Item;
//import com.xxl.item.pojo.ItemStock;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * redis 配置
// *
// * @author xxl
// * @date 2022/12/6 0:40
// */
//@Component
//public class RedisHandler implements InitializingBean {
//
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Autowired
//    private IItemService itemService;
//
//    @Autowired
//    private IItemStockService stockService;
//
//    private static final ObjectMapper MAPPER = new ObjectMapper();
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        // 初始化缓存
//        // 1.查询商品信息
//        List<Item> itemList = itemService.list();
//        // 2.放入缓存
//        for (Item item : itemList) {
//            // 2.1.item序列化为JSON
//            String json = MAPPER.writeValueAsString(item);
//            // 2.2.存入redis
//            redisTemplate.opsForValue().set("item:id:" + item.getId(), json);
//        }
//
//        // 3.查询商品库存信息
//        List<ItemStock> stockList = stockService.list();
//        // 4.放入缓存
//        for (ItemStock stock : stockList) {
//            // 2.1.item序列化为JSON
//            String json = MAPPER.writeValueAsString(stock);
//            // 2.2.存入redis
//            redisTemplate.opsForValue().set("item:stock:id:" + stock.getId(), json);
//        }
//    }
//
//    public void saveItem(Item item) {
//        try {
//            String json = MAPPER.writeValueAsString(item);
//            redisTemplate.opsForValue().set("item:id:" + item.getId(), json);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void deleteItemById(Long id) {
//        redisTemplate.delete("item:id:" + id);
//    }
//}
