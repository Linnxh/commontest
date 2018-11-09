

> 遗留问题，想象一下如果这个定时任务如果想随时修改这种方法，或者是一个图形化的管理界面，可能方式可能就不太实用了，可以尝试用如下的思路


1、将要执行的方法定义成服务的形式，向外暴露接口，调用该接口时，QuartzServiceHelper的线程池中会加入刚才的接口
2、新建一张task任务表，表信息是名称，要执行的方法，cron表达式等信息，这个表对应的是图形化的管理界面，可以增删改查操作
3、定义一个类继承org.quartz.Job类，如childJob,实现他的execute方法，这个方法中可以取出之前加入的task类信息（下面会提到），因为之前把要执行的方法暴露成接口的形式，这个地方可以直接post或者get直接请求对应的接口即可；

4、写一个单独的项目quartzservice，并且打包成jar的形式，有一个main方法，执行一个定时器timer，每5000ms从某task表取出所有的列表信息静态变量quartList，每次拿到的列表都删选一遍看一下，是否已经添加到quartList中，删选出要删除的，要增加的，不变的（要注意一下crom表达式是否有变化），

```
JobDetail jobDetail = JobBuilder.newJob(childJob).withIdentity(jobName, jobGroupName).build();// 任务名，任务组，任务执行类
JobDetailImpl detial=(JobDetailImpl)jobDetail;
//上面提到的将task类的信息塞入到hashmap，放入到jobDetail中
detial.getJobDataMap().putAll(paramMap);
```
5、将quartzservice 这个项目导出成一个可执行的main方法，java -jar quartzservice.jar 执行项目里的main方法


**项目中这个jar只需要在一台机器执行，多台没有必要，如果是到了要执行任务A的时候，就会执行相应的post请求，由于A项目是被部署到多台机器上的，但是他最终请求是会落在某一台机器上的，该方法就被放到QuartzServiceHelper的线程池中执行的，这样就保证了唯一性**
**暴露出的接口，即定时任务要处理的事情，这个接口是加redis，防止套圈**
 
 
 
 文件目录
 tool 里是后台管理界面，可以进行任务的增删改查的操作，
 business 包里是业务层，