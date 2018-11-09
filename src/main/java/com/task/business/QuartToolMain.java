package com.task.business;

import com.task.manager.QuartzManager;

public class QuartToolMain {
    public static void main(String[] args) throws Exception {
//        ApplicationContext contentApp = new ClassPathXmlApplicationContext("/WEB-INF/spring-servlet.xml");
//        SpringBeanHelper.setApplicationContext(contentApp);
        /* 加载定时任务 */
        QuartzManager.init(ChildJob.class, "quartzservice");
        // 生成PID
//        QuartzJobHelper.writePID();
    }
}
