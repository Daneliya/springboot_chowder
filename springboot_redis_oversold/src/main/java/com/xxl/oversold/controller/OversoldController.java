package com.xxl.oversold.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 超卖问题模拟
 *
 * @author xxl
 * @date 2023/10/09
 */
@RestController
public class OversoldController {

    /**
     * 引入String类型redis操作模板
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 测试数据设置接口
     *
     * @return
     */
    @RequestMapping("/setStock")
    public String setStock() {
        stringRedisTemplate.opsForValue().set("stock", 200 + "");
        return "ok";
    }

    /**
     * 模拟商品超卖代码
     *
     * @return
     */
    @RequestMapping("/deductStock")
    public String deductStock() {
        // 获取Redis数据库中的商品数量
        int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
        // 减库存
        if (stock > 0) {
            int realStock = stock - 1;
            stringRedisTemplate.opsForValue().set("stock", String.valueOf(realStock));
            System.out.println("商品扣减成功，剩余商品：" + realStock);
        } else {
            System.out.println("库存不足.....");
        }
        return "end";
    }

    /**
     * 模拟商品超卖代码 设置synchronized
     *
     * @return
     */
    @RequestMapping("/deductStock2")
    public String deductStock2() {
        synchronized (this) {
            // 获取Redis数据库中的商品数量
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            // 减库存
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", String.valueOf(realStock));
                System.out.println("商品扣减成功，剩余商品：" + realStock);
            } else {
                System.out.println("库存不足.....");
            }
        }
        return "end";
    }

    /**
     * 模拟商品超卖代码 设置setnx
     *
     * @return
     */
    @RequestMapping("/deductStock3")
    public String deductStock3() {
        // 创建一个key，保存至redis
        String key = "lock";
        // setnx
        // 由于redis是一个单线程，执行命令采取“队列”形式排队！
        // 优先进入队列的命令先执行，由于是setnx，第一个执行后，其他操作执行失败。
        boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, "this is lock");
        // 当不存在key时，可以设置成功，回执true；如果存在key，则无法设置，返回false
        if (!result) {
            // 前端监测，redis中存在，则不能让这个抢购操作执行，予以提示！
            return "err";
        }
        // 获取Redis数据库中的商品数量
        Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
        // 减库存
        if (stock > 0) {
            int realStock = stock - 1;
            stringRedisTemplate.opsForValue().set("stock", String.valueOf(realStock));
            System.out.println("商品扣减成功，剩余商品：" + realStock);
        } else {
            System.out.println("库存不足.....");
        }

        // 程序执行完成，则删除这个key
        stringRedisTemplate.delete(key);

        return "end";
    }

    /**
     * 模拟商品超卖代码 设置setnx
     * try…finally解决Redis分布式锁问题
     *
     * @return
     */
    @RequestMapping("/deductStock4")
    public String deductStock4() {
        // 创建一个key，保存至redis
        String key = "lock";
        // setnx
        // 由于redis是一个单线程，执行命令采取队列形式排队！优先进入队列的命令先执行，由于是setnx，第一个执行后，其他操作执行失败
        boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, "this is lock");
        // 当不存在key时，可以设置成功，回执true；如果存在key，则无法设置，返回false
        if (!result) {
            // 前端监测，redis中存在，则不能让这个抢购操作执行，予以提示！
            return "err";
        }

        try {
            // 获取Redis数据库中的商品数量
            Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            // 减库存
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", String.valueOf(realStock));
                System.out.println("商品扣减成功，剩余商品：" + realStock);
            } else {
                System.out.println("库存不足.....");
            }
        } finally {
            // 程序执行完成，则删除这个key
            // 放置于finally中，保证即使上述逻辑出问题，也能del掉
            stringRedisTemplate.delete(key);
        }

        return "end";
    }

    /**
     * 模拟商品超卖代码
     * 设置setnx保证分布式环境下，数据处理安全行问题；<br>
     * 但如果某个代码段执行异常，导致key无法清理，出现死锁，添加try...finally;<br>
     * 如果某个服务因某些问题导致释放key不能执行，导致死锁，此时解决思路为：增加key的有效时间
     *
     * @return
     */
    @RequestMapping("/deductStock5")
    public String deductStock5() {
        // 创建一个key，保存至redis
        String key = "lock";
        // setnx
        // 由于redis是一个单线程，执行命令采取队列形式排队！优先进入队列的命令先执行，由于是setnx，第一个执行后，其他操作执行失败
        boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, "this is lock");
        // 当不存在key时，可以设置成功，回执true；如果存在key，则无法设置，返回false
        if (!result) {
            // 前端监测，redis中存在，则不能让这个抢购操作执行，予以提示！
            return "err";
        }
        // 设置key有效时间
        stringRedisTemplate.expire(key, 10, TimeUnit.SECONDS);

        try {
            // 获取Redis数据库中的商品数量
            Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            // 减库存
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", String.valueOf(realStock));
                System.out.println("商品扣减成功，剩余商品：" + realStock);
            } else {
                System.out.println("库存不足.....");
            }
        } finally {
            // 程序执行完成，则删除这个key
            // 放置于finally中，保证即使上述逻辑出问题，也能del掉
            stringRedisTemplate.delete(key);
        }

        return "end";
    }

    /**
     * 模拟商品超卖代码
     * 设置setnx保证分布式环境下，数据处理安全行问题；<br>
     * 但如果某个代码段执行异常，导致key无法清理，出现死锁，添加try...finally;<br>
     * 如果某个服务因某些问题导致释放key不能执行，导致死锁，此时解决思路为：增加key的有效时间;<br>
     * 为了保证设置key的值和设置key的有效时间，两条命令构成同一条原子命令，将下列逻辑换成其他代码。
     *
     * @return
     */
    @RequestMapping("/deductStock6")
    public String deductStock6() {
        // 创建一个key，保存至redis
        String key = "lock";
        // setnx
        // 由于redis是一个单线程，执行命令采取队列形式排队！优先进入队列的命令先执行，由于是setnx，第一个执行后，其他操作执行失败
        //boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, "this is lock");
        //让设置key和设置key的有效时间都可以同时执行
        boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, "this is lock", 10, TimeUnit.SECONDS);

        // 当不存在key时，可以设置成功，回执true；如果存在key，则无法设置，返回false
        if (!result) {
            // 前端监测，redis中存在，则不能让这个抢购操作执行，予以提示！
            return "err";
        }
        // 设置key有效时间
        //stringRedisTemplate.expire(key, 10, TimeUnit.SECONDS);

        try {
            // 获取Redis数据库中的商品数量
            Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            // 减库存
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", String.valueOf(realStock));
                System.out.println("商品扣减成功，剩余商品：" + realStock);
            } else {
                System.out.println("库存不足.....");
            }
        } finally {
            // 程序执行完成，则删除这个key
            // 放置于finally中，保证即使上述逻辑出问题，也能del掉
            stringRedisTemplate.delete(key);
        }

        return "end";
    }

    /**
     * 模拟商品超卖代码 <br>
     * 解决`deductStock6`中，key形同虚设的问题。
     *
     * @return
     */
    @RequestMapping("/deductStock7")
    public String deductStock7() {
        // 创建一个key，保存至redis
        String key = "lock";
        String lock_value = UUID.randomUUID().toString();
        // setnx
        //让设置key和设置key的有效时间都可以同时执行
        boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, lock_value, 10, TimeUnit.SECONDS);
        // 当不存在key时，可以设置成功，回执true；如果存在key，则无法设置，返回false
        if (!result) {
            // 前端监测，redis中存在，则不能让这个抢购操作执行，予以提示！
            return "err";
        }
        try {
            // 获取Redis数据库中的商品数量
            Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            // 减库存
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", String.valueOf(realStock));
                System.out.println("商品扣减成功，剩余商品：" + realStock);
            } else {
                System.out.println("库存不足.....");
            }
        } finally {
            // 程序执行完成，则删除这个key
            // 放置于finally中，保证即使上述逻辑出问题，也能del掉
            // 判断redis中该数据是否是这个接口处理时的设置的，如果是则删除
            if(lock_value.equalsIgnoreCase(stringRedisTemplate.opsForValue().get(key))) {
                stringRedisTemplate.delete(key);
            }
        }
        return "end";
    }
}
