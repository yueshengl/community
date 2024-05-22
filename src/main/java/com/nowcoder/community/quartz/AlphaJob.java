package com.nowcoder.community.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author: Dai
 * @Date: 2024/05/21 11:27
 * @Description: AlphaJob
 * @Version: 1.0
 */
public class AlphaJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(Thread.currentThread().getName()+ ":execute a quartz job.");
    }
}
