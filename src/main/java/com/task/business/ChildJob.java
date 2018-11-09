package com.task.business;

import com.task.manager.QuartzManager;
import com.task.tool.bean.QuartzServiceModel;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.impl.JobDetailImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class ChildJob implements Job {
    private final static Logger log = LoggerFactory.getLogger("com.task.business.ChildJob");
    //private static List<String> runningJobList=new ArrayList<String>();
    private static volatile Map<String, Long> runningJobList = new HashMap<String, Long>();

    /**
     * 判断给定的job是否正在运行
     *
     * @param reqUrl
     * @return
     */
    private synchronized boolean getIsRunning(String reqUrl) {
        if (!runningJobList.containsKey(reqUrl)) {
            return false;
        }
        Long oldTime = runningJobList.get(reqUrl);
        long span = System.currentTimeMillis() - oldTime.longValue();
        if (span >= 30 * 1000) {
            log.error(reqUrl + span + "ms之前添加，本次移除");
            clearOldRunningJobMapByKey(reqUrl);
            return false;
        }
        return true;
    }

    /**
     * 将给定的job标记为正在运行
     *
     * @param reqUrl
     */
    private synchronized void addRunningJobMap(String reqUrl) {
        if (!runningJobList.containsKey(reqUrl)) {
            runningJobList.put(reqUrl, System.currentTimeMillis());
        }
    }

    /**
     * 清除给定的job的正在运行的标记
     *
     * @param reqUrl
     */
    public static synchronized void clearOldRunningJobMapByKey(String reqUrl) {
        if (runningJobList.containsKey(reqUrl)) {
            runningJobList.remove(reqUrl);
        }
    }

    /**
     * 封装quartz服务的接口
     */
    @Override
    public void execute(JobExecutionContext context) {
        JobDetailImpl detail = ((JobDetailImpl) context.getJobDetail());
        QuartzServiceModel newModel = (QuartzServiceModel) detail.getJobDataMap().get(QuartzManager.ParamMapKey);
        String reqUrl = newModel.getReqUrl();
        //判断当前job是否正在执行
        boolean isRunning = getIsRunning(reqUrl);
        if (isRunning) {
            log.error(reqUrl + "正在执行(本次跳过)");
            return;
        }
        //如果没有正在执行，则将当前job标记为正在执行
        addRunningJobMap(reqUrl);
        int reqMethod = newModel.getReqMethod();
        String result = "";
        try {
            if (reqMethod == 0) {
                //post请求方式
                String response = HttpUtil.sendPost(reqUrl);

            } else if (reqMethod == 1) {
                //get
            }
            log.info("执行的结果:" + result);
        } catch (Exception e) {
            log.error("childJob的execute方法执行失败");
            e.printStackTrace();
        } finally {
            //当前job执行完毕，则将当前job正在执行的标记移除
            clearOldRunningJobMapByKey(reqUrl);
        }
    }

}
