package com.task.business;


import com.SpringBeanHelper;
import com.common.HttpReturnRnums;
import com.common.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                JsonResult result = synDo(beanName);
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

}

