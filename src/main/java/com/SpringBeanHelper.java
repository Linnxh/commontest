package com;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

public class SpringBeanHelper {
	private static volatile ApplicationContext ctx_bean;
	/**
	 * 将ApplicationContext保存到static变量中
	 * @param ctx
	 */
	public static void setApplicationContext(ApplicationContext ctx){
		if (ctx_bean==null) {
			//非web站点时，设置ApplicationContext
			ctx_bean=ctx;
		}
	}
    public static ApplicationContext getApplicationContext() {
        return ctx_bean;
    }
    private static void createContext(){
    	if (ctx_bean==null) {
    		initContext();
		}
    }
    private static synchronized void initContext(){
    	if (ctx_bean==null) {
			//web站点时，获取ApplicationContext
			ctx_bean = ContextLoader.getCurrentWebApplicationContext();
		}
    }
	public static Object getCustomBean(String beanName) {
		createContext();
		if (ctx_bean==null) {
			throw new RuntimeException("没有获取到springcontext");
		}
		return ctx_bean.getBean(beanName);
	}

	public static <T> T getCustomBeanByType(Class<T> type) {
		createContext();
		if (ctx_bean==null) {
			throw new RuntimeException("没有获取到springcontext");
		}
		return ctx_bean.getBean(type);
	}
}
