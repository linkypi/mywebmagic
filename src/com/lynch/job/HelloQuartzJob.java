package com.lynch.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Created by linxueqi on 2016/9/5 0005.
 */
public class HelloQuartzJob implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {

        System.out.println("Hello, Quartz! - executing its JOB at "+
                new Date() + " by " + context.getTrigger().getCalendarName());
    }
}
