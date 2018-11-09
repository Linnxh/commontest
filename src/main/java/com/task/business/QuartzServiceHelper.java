package com.task.business;


import com.SpringBeanHelper;
import com.common.HttpReturnRnums;
import com.common.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class QuartzServiceHelper {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final Logger logger = LoggerFactory.getLogger(QuartzServiceHelper.class);

    /**
     * 根据beanName异步调用run方法，并记录日志
     *
     * @return
     * @author hailongzhao
     * @date 20160217
     */
    public static JsonResult doRun(final String beanName) {
        if (beanName == null || beanName.trim().isEmpty()) {
            logger.error("beanName不能为空");
            return new JsonResult(HttpReturnRnums.ParaError.value(), "beanName不能为空");
        }
        //由于记录日志是异步执行的，因此需要将request中的属性先拿出来
        //否则当方法返回时，异步方法中可能访问不到request中的属性
//        ActionLog logEngity = LogDetailHelper.getQuartzServiceActionLog();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
//                LogDetailHelper.quartzServiceLogBefore(logEngity);
                JsonResult result = synDo(beanName);
//                LogDetailHelper.quartzServiceLogAfter(logEngity, result);
            }
        });
        return new JsonResult();
    }

    /**
     * 同步调用业务的服务
     *
     * @param beanName
     * @return
     * @date 20160217
     * @author hailongzhao
     */
    private static JsonResult synDo(String beanName) {
//        String key = GlobalConsts.HttpQuartz_Key + beanName;
//        AbstractRedisService redisService = getRedisService(beanName);
//        if (redisService == null) {
//            return new JsonResult(HttpReturnRnums.SystemError.value(),
//                    "执行给定的bean:" + beanName + "的run方法时异常:RedisService不存在或没有继承AbstractRedisService");
//        }
//        String quartzObj = redisService.get(key);
        //redis中存在这个key，则表示当前服务正在执行，防止套圈执行
//        if (quartzObj != null && quartzObj.equals("1")) {
//            return new JsonResult(HttpReturnRnums.ParaError.value(), "服务正在执行，不能重复调用");
//        }
//        redisService.set(key, "1", 4, TimeUnit.HOURS);
        try {
            Object obj = SpringBeanHelper.getCustomBean(beanName);
            if (obj != null && obj instanceof IJobDo) {
                ((IJobDo) obj).run();
            } else {
                return new JsonResult(HttpReturnRnums.ParaError.value(),
                        "给定的bean:" + beanName + "不存在或没有实现com.zhtx.commonentity.common.IJobDo接口");
            }
            return new JsonResult();
        } catch (Exception e) {
            //e.printStackTrace();
//            String stackTrace = StringUtils.getStackTrace(e);
            return new JsonResult(HttpReturnRnums.ParaError.value(),
                    "执行给定的bean:" + beanName + "的run方法时异常:" + e.getMessage(), e);
        } finally {
//            redisService.remove(key);
        }
    }

//    private static AbstractRedisService getRedisService(String beanName) {
//        try {
//            return SpringBeanHelper.getCustomBeanByType(AbstractRedisService.class);
//        } catch (Exception e) {
//            String stackTrace = StringUtils.getStackTrace(e);
//            logger.error("执行给定的bean:" + beanName + "的run方法时异常,RedisService不存在或没有继承AbstractRedisService:" + e.getMessage() + stackTrace);
//            return null;
//        }
//    }
}

