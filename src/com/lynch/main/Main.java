package com.lynch.main;

import com.lynch.dao.MultiplePriceDao;
import com.lynch.job.HelloQuartzJob;
import com.lynch.model.MultiplePrice;
import com.lynch.utils.Constants;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.monitor.SpiderStatusMXBean;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import javax.management.JMException;
import java.util.Date;
import java.util.UUID;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by linxueqi on 2016/9/5 0005.
 */
public class Main {

    public static void main(String[] args) throws BeansException, SchedulerException {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler sched = schedulerFactory.getScheduler();

        // computer a time that is on the next round minute
        Date runTime = evenMinuteDate(new Date());
        // define the job and tie it to our HelloJob class
        JobDetail job = newJob(HelloQuartzJob.class).withIdentity("job1", "group1").build();

        // Trigger the job to run on the next round minute
        Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(runTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(2).withRepeatCount(5)).build();

        // Tell quartz to schedule the job using our trigger
        sched.scheduleJob(job, trigger);

        // scheduler has been started)
        sched.start();


        //

        // start();
    }

    private static void start() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/config/applicationContext.xml");// 没有classpath表示当前目录

        MultiplePriceDao dao = context.getBean(MultiplePriceDao.class);
        try
        {
            dao.insert(new MultiplePrice(UUID.randomUUID().toString().replace("-",""),
                    "234231","90","1200","40032",false,"customcode","lessmall"));
               System.out.println("main");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

//        SpiderMonitor spiderMonitor = new SpiderMonitor(){
//            @Override
//            protected SpiderStatusMXBean getSpiderStatusMBean(Spider spider, MonitorSpiderListener monitorSpiderListener) {
//
//                return new CustomSpiderStatus(spider, monitorSpiderListener);
//            }
//        };

        Spider spider = Spider.create(new ChaoYueProcessor());
        spider.addUrl(Constants.SCHEMA+"://"+Constants.URL_CATEGORY) //必须加上前缀http
         //.addUrl("http://www.lessomall.com/medias/?context=bWFzdGVyfGltYWdlc3wxMDIwOTl8aW1hZ2UvanBlZ3xpbWFnZXMvaDJkL2hhNi84Nzk4Njk0OTY1Mjc4LmpwZ3w3ODc4NjAxNDg4Y2FkMmQ0ZDMwOWYzODhhNmRhNmNlOTM2OTc0YmFiODM4Mjc1YmI4ZmZmN2RkMDQzMTIzY2Ux")
        .addPipeline(new MyPipeline()).addPipeline(new ConsolePipeline())
        .setDownloader(new ImageDownloader())
        .thread(1)
        .run();

        try {
            SpiderMonitor.instance().register(spider);

        } catch (JMException e) {
            e.printStackTrace();
        }
    }

}
