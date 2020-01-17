package com.dahuaboke.nodou.manager;

import com.dahuaboke.nodou.timeTask.HeartbeatTask;
import com.dahuaboke.nodou.timeTask.PersistenceTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskManager {

    @Value("${log.address}")
    private String log_address;

    @Bean
    public PersistenceTask persistenceTask() {
        return new PersistenceTask(log_address);
    }

    @Bean
    public HeartbeatTask heartbeatTask(){
        return new HeartbeatTask();
    }
}
