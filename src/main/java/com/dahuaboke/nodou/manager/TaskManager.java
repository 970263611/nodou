package com.dahuaboke.nodou.manager;

import com.alibaba.fastjson.JSON;
import com.dahuaboke.nodou.model.NodeModel;
import com.dahuaboke.nodou.timeTask.HeartbeatTask;
import com.dahuaboke.nodou.timeTask.PersistenceTask;
import com.dahuaboke.nodou.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class TaskManager {

    @Value("${log.address}")
    private String log_address;

    @Bean
    public PersistenceTask persistenceTask() {
        String jsonString = FileUtil.read(log_address);
        if(jsonString != null && !"".equals(jsonString)){
//            List<Map> list = JSON.parseArray(jsonString, Map.class);
//            for(Map map : list){
//                NodeManager.parentNode.putAll(map);
//            }
            NodeManager.parentNode = new NodeModel<String, NodeModel>();
        }else{
            NodeManager.parentNode = new NodeModel<String, NodeModel>();
        }
        return new PersistenceTask(log_address);
    }

    @Bean
    public HeartbeatTask heartbeatTask(){
        return new HeartbeatTask();
    }
}
