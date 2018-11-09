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
	private final  static Logger log = LoggerFactory.getLogger("com.zhtx.tools.quartzservice.ChildJob");
	//private static List<String> runningJobList=new ArrayList<String>(); 
	private static volatile Map<String,Long> runningJobList=new HashMap<String,Long>();
	/**
	 * 判断给定的job是否正在运行
	 * @param reqUrl
	 * @return
	 */
	private synchronized boolean getIsRunning(String reqUrl){
		if (!runningJobList.containsKey(reqUrl)) {
			return false;
		}
		Long oldTime=runningJobList.get(reqUrl);
		long span=System.currentTimeMillis()-oldTime.longValue();
		if (span>=30*1000) {
			log.error(reqUrl+span+"ms之前添加，本次移除");
			clearOldRunningJobMapByKey(reqUrl);
			return false;
		}
		return true;
	}
	/**
	 * 将给定的job标记为正在运行
	 * @param reqUrl
	 */
	private synchronized void addRunningJobMap(String reqUrl){
		if(!runningJobList.containsKey(reqUrl)){
			runningJobList.put(reqUrl,System.currentTimeMillis());
		}
	}
	/**
	 * 清除给定的job的正在运行的标记
	 * @param reqUrl
	 */
	public static synchronized void clearOldRunningJobMapByKey(String reqUrl){
		if(runningJobList.containsKey(reqUrl)){
			runningJobList.remove(reqUrl);
		}
	}
	/**
	 * 封装quartz服务的接口
	 * @author hailongzhao
	 * @date 20151211
	 */
	@Override
	public void execute(JobExecutionContext context) {
		JobDetailImpl detail=((JobDetailImpl)context.getJobDetail());
		QuartzServiceModel newModel=(QuartzServiceModel)detail.getJobDataMap().get(QuartzManager.ParamMapKey);
//		String reqUrl =newModel.getReqUrl();
//		//判断当前job是否正在执行
//		boolean isRunning=getIsRunning(reqUrl);
//		if (isRunning) {
//			log.error(reqUrl+"正在执行(本次跳过)");
//			return;
//		}
//		//如果没有正在执行，则将当前job标记为正在执行
//		addRunningJobMap(reqUrl);
//		String param =newModel.getParam();
//		int reqMethod =newModel.getReqMethod();
//		int paramType =newModel.getReqMethod();
		String result ="";
		try {
//			if(reqMethod == 0){
//				if(paramType == 0){
////					result = HttpUtil.sendJsonPost(reqUrl, param);
//				}else{
////					result = HttpUtil.sendFormPost(reqUrl, param);
//				}
//			}else if(reqMethod == 1){
////				result = HttpUtil.sendGet(reqUrl, param);
//			}
//			log.info(reqUrl+"执行的结果:"+result);
			log.info("执行的结果:"+result);
			//任务执行成功，则从重试队列中移除
//			QuartzJobHelper.removeRetryJob(newModel);
		} catch (Exception e) {

			String title=String.format("执行job失败(%s)", newModel.getName());
//			String content=String.format("执行job时出错(%s)(%s):\n%s",
//					newModel.getName(),
//					reqUrl,
//					e.getMessage()+StringUtils.getStackTrace(e));
//			log.error(content);
//			sendAlert(newModel,title,content);
//			定时任务本次执行失败，则加入重试队列,如果已经加入过重试队列，则跳过
//			QuartzJobHelper.addRetryJob(RetryChildJob.class, newModel);
		}finally{
			//当前job执行完毕，则将当前job正在执行的标记移除
//			clearOldRunningJobMapByKey(reqUrl);
		}
	}

//	public static JsonResult sendAlert(QuartzServiceModel quartzModel,String title,String content){
//		boolean sendSysMail=false;
//		if (quartzModel.getEmails()!=null&&!quartzModel.getEmails().trim().isEmpty()) {
//			sendSysMail=!SystemUtils.sendAlertEmail(title, content,  quartzModel.getEmails());
//		}else {
//			//如果当前url没配置收件人，则给系统配置的收件人发送邮件
//			sendSysMail=true;
//		}
//		if (quartzModel.getMobiles()!=null&&!quartzModel.getMobiles().trim().isEmpty()) {
//			//发送手机短信
//			sendSms(quartzModel.getMobiles(),title+":"+ParseHelper.filterCauseBy(content));
//		}
//		if (sendSysMail) {
//			//给系统配置的收件人发送邮件
//			SystemUtils.sendAlertEmail(title, content);
//		}
//
//		return new JsonResult();
//	}
//	private static void sendSms(String mobiles,String msg){
//		if (msg.length()>50) {
//			//加上短信模板超过70个字时，会按照2条短信来收费
//			//因此这里只截取前50个字
//			msg=msg.substring(0, 50);
//		}
//		try {
//			if (mobiles.indexOf(";")>0) {
//		    	String [] mailtoStrings=mobiles.split(";");
//		    	for (String mobile : mailtoStrings) {
//					if (mobile!=null&&!mobile.trim().isEmpty()) {
//						callSendSms(mobile, msg);
//					}
//				}
//		    }else {
//				callSendSms(mobiles, msg);
//			}
//		} catch (Exception e) {
//			System.out.println("站点心跳报警发送手机短信失败:"+e.getMessage()+StringUtils.getStackTrace(e));
//		}
//
//	}
//	private static boolean callSendSms(String phone,String msg) {
//		SmsModelReq req = new SmsModelReq();
//		req.setContent(msg);
//		req.setMobile(phone);
//		req.setPlatformJp("zhcs");
//		// 不要直接调用 发送短信的方法，走 短信域名下的 接口
//		// JsonResult jr= smsService.send(req);
//		JsonResult jr = ApiHttpUtil.getJsonResult(Config.getSmsnUrl(), req);
//		if (jr != null && jr.getCode() == 1) {
//			return true;
//		} else {
//			return false;
//		}
//	}
}
