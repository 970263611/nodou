package com.dahuaboke.nodou.config;

import com.dahuaboke.nodou.manager.ReadOnlyManager;
import com.dahuaboke.nodou.task.HeartbeatTask;
import com.dahuaboke.nodou.task.PersistenceTask;
import com.dahuaboke.nodou.task.RegisterToReadTask;
import com.dahuaboke.nodou.task.WriteToRegisterTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author:dahua
 * @date:2020/6/16
 * @description: dahua nodou 2.0
 */
@Component
public class ManagerConfiguration {

    private static ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);

    @Value("${log.address}")
    private String address;

    public ManagerConfiguration() {
        pool.scheduleAtFixedRate(new WriteToRegisterTask(), 10, 10, TimeUnit.SECONDS);
        Thread t1 = new Thread(new RegisterToReadTask(ReadOnlyManager.getInstance1(),"instance1"));
        Thread t2 = new Thread(new RegisterToReadTask(ReadOnlyManager.getInstance2(),"instance2"));
        pool.scheduleAtFixedRate(t1, 300, 300, TimeUnit.SECONDS);
        pool.scheduleAtFixedRate(t2, 35, 35, TimeUnit.SECONDS);
        pool.scheduleAtFixedRate(new HeartbeatTask(), 30, 30, TimeUnit.SECONDS);
        pool.scheduleAtFixedRate(new PersistenceTask(address), 60 * 30, 60 * 30, TimeUnit.SECONDS);
    }

}
