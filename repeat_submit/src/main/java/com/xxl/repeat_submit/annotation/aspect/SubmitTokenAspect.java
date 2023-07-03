package com.xxl.repeat_submit.annotation.aspect;//package com.xxl.config;
//
//import com.xxl.result.ResResult;
//import com.xxl.result.ResResultEnum;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.time.Duration;
//import java.util.Objects;
//
///**
// * @author xxl
// * @date 2022/11/17 23:42
// */
//@Order(1)
//@Aspect
//@Component
//public class SubmitTokenAspect {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(SubmitTokenAspect.class);
//
//    /**
//     * 获取分布式锁等待时间，单位秒
//     */
//    private static final Long LOCK_REDIS_WAIT_TIME = 3L;
//
//    /**
//     * 分布式锁前缀
//     */
//    private static final String LOCK_KEY_PREFIX = "SUBMIT:TOKEN:LOCK";
//
//    /**
//     * 默认锁对应的值
//     */
//    private static final String DEFAULT_LOCK_VALUE = "DEFAULT_LOCK_VALUE";
//
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Autowired
//    private RedisLockService redisLockService;
//
//    /**
//     * 方法调用环绕拦截
//     */
//    @Around(value = "@annotation(com.xxl.config.annotation.SubmitToken)")
//    public Object doAround(ProceedingJoinPoint joinPoint) {
//        HttpServletRequest request = getHttpServletRequest();
//        if (Objects.isNull(request)) {
//            return ResResult.getSysError("请求参数不能为空！");
//        }
//        String submitToken = request.getHeader("submitToken");
//        if (StringUtils.isEmpty(submitToken)) {
//            return ResResult.getSysError("submitToken不能为空！");
//        }
//        //检查submitToken是否存在
//        String submitTokenValue = stringRedisTemplate.opsForValue().get(submitToken);
//        if (StringUtils.isEmpty(submitTokenValue)) {
//            return ResResult.getSysError(ResResultEnum.SUBMIT_ERROR_MESSAGE.getMessage());
//        }
//        //尝试加锁
//        String lockKey = LOCK_KEY_PREFIX + submitToken;
//        boolean lock = redisLockService.tryLock(lockKey, DEFAULT_LOCK_VALUE, Duration.ofSeconds(LOCK_REDIS_WAIT_TIME));
//        if (!lock) {
//            return ResResult.getSysError("服务正在处理，请勿重复提交！");
//        }
//        try {
//            //继续执行后续流程
//            Object result = joinPoint.proceed();
//            //任务执行成功，清除submitToken缓存
//            stringRedisTemplate.delete(submitToken);
//            return result;
//        }
////        catch (CommonException e) {
////            return ResResult.getSysError(e.getMessage());
////        }
//        catch (Throwable e) {
//            LOGGER.error("业务处理发生异常，错误信息：", e);
//            return ResResult.getSysError(ResResultEnum.DEFAULT_ERROR_MESSAGE.getMessage());
//        } finally {
//            //执行完毕之后，手动将锁释放
//            redisLockService.releaseLock(lockKey, DEFAULT_LOCK_VALUE);
//        }
//    }
//
//    /**
//     * 获取请求对象
//     *
//     * @return
//     */
//    private HttpServletRequest getHttpServletRequest() {
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        HttpServletRequest request = sra.getRequest();
//        return request;
//    }
//}