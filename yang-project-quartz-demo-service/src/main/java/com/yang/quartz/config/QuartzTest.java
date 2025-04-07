package com.yang.quartz.config;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2025/3/25
 */
public class QuartzTest {

    public static void main(String[] args) throws SchedulerException {
        //1. 创建一个JobDetail，把实现了Job接口的类邦定到JobDetail 构建者模式 绑定job withIdentity这里起一个唯一的名字
        JobDetail jobDetail= JobBuilder.newJob(MyJob.class).withIdentity("demo1").build();
        //第二个组件 Trigger触发器
        //2.创建一个Trigger触发器的实例，定义该job立即执行，并且每2秒执行一次，一直执行 repeatForever重复
        SimpleTrigger trigger= TriggerBuilder
                .newTrigger()
                .withIdentity("trriger1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();
        //创建schedule实例  三 调度器   StdSchedulerFactory 工厂模式
        StdSchedulerFactory factory = new StdSchedulerFactory();
        //获取调度器实例
        Scheduler scheduler = factory.getScheduler();
        //开启调度器
        scheduler.start();
        //把SimpleTrigger和JobDetail注册给调度器
        scheduler.scheduleJob(jobDetail,trigger);
    }


}
