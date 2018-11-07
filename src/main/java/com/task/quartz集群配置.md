# quartz集群配置（集群方式，单机的不建议采用）

 
**java定时任务开源框架常用的是：Quartz和spring task**
Quartz
spring对quartz进行了封装，完整支持cron表达式，而且具有很多高级特性，如JDBCJobStore（作业仓库）、集群模式

spring task
独立实现了定时任务的调度器,相对较为简单，cron表达式不支持L W(这个月最后一周的工作日)

**分布式定时任务开源框架**
Elastic-Job（当当网）
light-task-scheduler
clover
TBSchedule（阿里）
niubi-job
Uncode-Schedule

本篇的重点是Quartz的集群部署
一个 Quartz 集群中的每个节点是一个独立的 Quartz 应用，它又管理着其他的节点。意思是你必须对每个节点分别启动或停止。不像许多应用服务器的集群，独立的 Quartz 节点并不与另一其的节点或是管理节点通信。Quartz 应用是通过数据库表来感知到另一应用的。

## 添加maven配置

```
<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz</artifactId>
    <version>2.2.1</version>
</dependency>
<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz-jobs</artifactId>
    <version>2.2.1</version>
</dependency>
```

## 导入对应的表


创建quartz要用的数据库表，此sql文件在：quartz-2.2.3\docs\dbTables。此文件夹下有各个数据库的sql文件，mysql选择tables_mysql_innodb.sql

## 配置文件
quartz.properties的配置文件

```
#调度标识名 集群中每一个实例都必须使用相同的名称
org.quartz.scheduler.instanceName = MyScheduler
#线程数量
org.quartz.threadPool.threadCount = 10
#线程优先级
org.quartz.threadPool.threadPriority = 5
#数据保存方式为持久化
org.quartz.jobStore.class = org.quartz.impl.jdbcjobstore.JobStoreTX
#数据库平台
org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate
#表的前缀
org.quartz.jobStore.tablePrefix = QRTZ_
#库的别名
org.quartz.jobStore.dataSource = myDS
# Cluster开启集群
org.quartz.jobStore.isClustered = true
#ID设置为自动获取 每一个必须不同
org.quartz.scheduler.instanceId = AUTO

org.quartz.dataSource.myDS.driver = com.mysql.jdbc.Driver
org.quartz.dataSource.myDS.URL = jdbc:mysql://localhost:3306/lxhtest?characterEncoding=utf-8
org.quartz.dataSource.myDS.user = root
org.quartz.dataSource.myDS.password = 123123
org.quartz.dataSource.myDS.maxConnections = 5
```

quartz.xml的配置文件

```
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd">

    <!-- #####################################单节点部署的项目 start##################################### -->
    <bean name="exampleJobDetail" class="org.springframework.scheduling.quartz.JobDetailFactoryBean">
        <property name="jobClass" value="com.task.ExampleJob"/>
        <property name="jobDataAsMap">
            <map>
                <entry key="timeout" value="5"/>
            </map>
        </property>
    </bean>
    <bean id="exampleJobTrigger"
          class="org.springframework.scheduling.quartz.SimpleTriggerFactoryBean">
        <property name="jobDetail" ref="exampleJobDetail"/>
        <!-- 延迟触发时间，延迟10秒进行触发 -->
        <property name="startDelay" value="10000"/>
        <!-- 重复触发的时间间隔，5秒 -->
        <property name="repeatInterval" value="5000"/>
    </bean>

    <bean class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
        <property name="triggers">
            <list>
                <ref bean="exampleJobTrigger"/>
            </list>
        </property>
    </bean>


    <bean id="exampleJob2" class="com.task.ExampleJob2"></bean>
    <bean id="exampleJob2Detail"
          class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">
        <!-- 指定任务类 -->
        <property name="targetObject" ref="exampleJob2"/>
        <!-- 指定任务执行的方法 -->
        <property name="targetMethod" value="execute"/>
    </bean>
    <bean id="exampleJob2Trigger"
          class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
        <property name="jobDetail" ref="exampleJob2Detail"/>
        <!-- 每10秒运行一次 -->
        <property name="cronExpression" value="0/10 * * * * ?"/>
    </bean>

    <bean class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
        <property name="triggers">
            <list>
                <!-- <ref bean="exampleJobTrigger" /> -->
                <ref bean="exampleJob2Trigger"/>
            </list>
        </property>
    </bean>
    <!-- #####################################单节点部署的项目 end##################################### -->



    <!-- #####################################多节点部署的项目 start##################################### -->
    <!-- 定义调用对象和调用对象的方法 -->
    <bean id="SpringQtzJobDetail"
          class="org.springframework.scheduling.quartz.JobDetailFactoryBean">
        <property name="jobClass"  value="com.task.SpringQtz"/>
        <property name="durability" value="true"/>

        <property name="group" value="job_work"/>
        <property name="name" value="job_work_name"/>
    </bean>
    <bean id="SpringQtzJobDetail1"
          class="org.springframework.scheduling.quartz.JobDetailFactoryBean">
        <property name="jobClass" value="com.task.SpringQtzDemo"/>
        <property name="durability" value="true"/>

        <property name="group" value="job_work1"/>
        <property name="name" value="job_work_name1"/>
    </bean>


    <!-- ======================== 调度触发器 ======================== -->
    <bean id="CronTriggerBean"
          class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
        <property name="jobDetail" ref="SpringQtzJobDetail"></property>
        <!-- cron表达式 -->
        <property name="cronExpression" value="0/20 * * * * ?"></property>
    </bean>

    <bean id="CronTriggerBean1"
          class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
        <property name="jobDetail" ref="SpringQtzJobDetail1"></property>
        <!-- cron表达式 -->
        <property name="cronExpression" value="0/30 * * * * ?"></property>
    </bean>

    <!-- ======================== 调度工厂 ======================== -->
    <bean id="SpringJobSchedulerFactoryBean"
          class="org.springframework.scheduling.quartz.SchedulerFactoryBean">

        <property name="dataSource">
            <ref bean="mySqlDataSource"/>
        </property>
        <property name="applicationContextSchedulerContextKey" value="applicationContext" />
        <property name="configLocation" value="WEB-INF/config/quartz.properties"/>
        <!--启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了-->
        <property name="overwriteExistingJobs" value="true"/>
        <property name="triggers">
            <list>
                <ref bean="CronTriggerBean" />
                <ref bean="CronTriggerBean1" />
            </list>
        </property>
        <property name="jobDetails">
            <list>
                <ref bean="SpringQtzJobDetail" />
                <ref bean="SpringQtzJobDetail1" />
            </list>
        </property>
    </bean>
    <!-- #####################################多节点部署的项目 end##################################### -->
  
</beans>
```

在applicontext.xml或者spring-servlet.xml中导入quartz.xml文件


## 具体 的执行类

SpringQtz类

```
public class SpringQtz extends QuartzJobBean {

    static Logger logger = LoggerFactory.getLogger(SpringQtz.class);

    private static int counter = 0;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long ms = System.currentTimeMillis();
//        System.out.println(" SpringQtz start  执行");
        System.out.println("SpringQtz-------"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(ms))+"  "+"(" + counter++ + ")");
    }


}
```

SpringQtzDemo类

```
public class SpringQtzDemo extends QuartzJobBean {

    private static final Log log = LogFactory.getLog(SpringQtzDemo.class);

    static Logger logger = LoggerFactory.getLogger(SpringQtzDemo.class);

    private static int counter = 0;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long ms = System.currentTimeMillis();

//        System.out.println(" SpringQtzDemo start  执行");
        System.out.println("SpringQtzDemo**********" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(ms)) + "  " + "(" + counter++ + ")");

    }
}
```

此处是写了两个实例



## 启动项目测试
启动一个tomcat观察，该服务器定期执行操作，结果如下
tomcatA

```
SpringQtz-------2018-11-06 03:03:40  (0)
SpringQtzDemo**********2018-11-06 03:04:00  (1)
SpringQtz-------2018-11-06 03:04:00  (1)
SpringQtz-------2018-11-06 03:04:20  (2)
SpringQtzDemo**********2018-11-06 03:04:30  (2)
SpringQtz-------2018-11-06 03:04:40  (3)
SpringQtz-------2018-11-06 03:05:00  (4)
SpringQtzDemo**********2018-11-06 03:05:00  (3)
SpringQtz-------2018-11-06 03:05:20  (5)
SpringQtzDemo**********2018-11-06 03:05:30  (4)
```

在启动tomcat，发现他是两个窗口分别执行任务的，结果如下
tomcatA

```
SpringQtzDemo**********2018-11-06 03:06:00  (5)
SpringQtz-------2018-11-06 03:06:00  (7)
SpringQtz-------2018-11-06 03:06:20  (8)
SpringQtzDemo**********2018-11-06 03:06:30  (6)
SpringQtzDemo**********2018-11-06 03:07:00  (7)
SpringQtz-------2018-11-06 03:07:20  (9)
SpringQtz-------2018-11-06 03:07:40  (10)
SpringQtz-------2018-11-06 03:08:00  (11)
SpringQtz-------2018-11-06 03:08:20  (12)
SpringQtz-------2018-11-06 03:08:40  (13)
```
tomcatB

```
SpringQtz-------2018-11-06 03:06:40  (0)
SpringQtz-------2018-11-06 03:07:00  (1)
SpringQtzDemo**********2018-11-06 03:07:30  (0)
SpringQtzDemo**********2018-11-06 03:08:00  (1)
SpringQtzDemo**********2018-11-06 03:08:30  (2)
SpringQtzDemo**********2018-11-06 03:09:00  (3)
```

注意一个问题
>The load balancing mechanism is near-random for busy schedulers (lots of triggers) but favors the same node that just was just active for non-busy (e.g. one or two triggers) schedulers.

负载平衡机制对于繁忙的调度器(很多触发器)几乎是随机的，但是对于非繁忙的调度器(例如，一个或两个触发器)，负载平衡机制倾向于相同的节点。




> 遗留问题，想象一下如果这个定时任务如果想随时修改这种方法，或者是一个图形化的管理界面，可能方式可能就不太实用了，可以尝试用如下的思路


1、将要执行的方法暴露成接口的形式，定义一个Helper，有一个线程池的，其中放的是要执行的接口名称，这个地方加的是redis锁，防止套圈
2、新建一张task任务表，表信息是名称，要执行的方法，cron表达式等信息，这个表对应的是图形化的管理界面，可以增删改查操作
3、定义一个类继承org.quartz.Job类，如childJob,实现他的execute方法，这个方法中可以取出之前加入的task类信息（下面会提到），因为之前把要执行的方法暴露成接口的形式，这个地方可以直接post或者get直接请求对应的接口即可；

4、写一个单独的项目quartzservice，有一个main方法，执行一个定时器timer，每5000ms从某task表取出所有的列表信息静态变量quartList，每次拿到的列表都删选一遍看一下，是否已经添加到quartList中，删选出要删除的，要增加的，不变的（要注意一下crom表达式是否有变化），

```
JobDetail jobDetail = JobBuilder.newJob(childJob).withIdentity(jobName, jobGroupName).build();// 任务名，任务组，任务执行类
JobDetailImpl detial=(JobDetailImpl)jobDetail;
//上面提到的将task类的信息塞入到hashmap，放入到jobDetail中
detial.getJobDataMap().putAll(paramMap);
```
5、将quartzservice 这个项目导出成一个可执行的main方法，java -jar quartzservice.jar 执行项目里的main方法

**项目中这个jar运行只需要在一台机器部署，多台是不一定不能保证唯一性的**
**暴露出的接口，即定时任务要处理的事情，这个接口是通过redis来保证唯一性的**
 
 

