package com.dahuaboke.nodou.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
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
        NodeManager.parentNode = new NodeModel<String, NodeModel>();
        try{
            String jsonString = FileUtil.read(log_address);
            if (jsonString != null && !"".equals(jsonString)) {
                List<Map> list = JSON.parseArray(jsonString, Map.class);
                for (Map map : list) {
                    for (Object key : map.keySet()) {
                        JSONObject o = (JSONObject) map.get(key);
//                        NodeModel nodeModel = new NodeModel();
//                        for(String jsonKey : o.keySet()){
//                            JSONArray jsonArray = o.getJSONArray(jsonKey);
//                            List<String> temp = jsonArray.toJavaList(String.class);
//                            nodeModel.put(jsonKey,temp);
//                        }
                        NodeModel nodeModel = JSONObject.parseObject(o.toJSONString(),new TypeReference<NodeModel<Object,Object>>(){});
                        NodeManager.parentNode.put(key,nodeModel);
                    }
//                    NodeManager.parentNode.putAll(map);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("无备份文件");
        }
        return new PersistenceTask(log_address);
    }

    @Bean
    public HeartbeatTask heartbeatTask() {
        return new HeartbeatTask();
    }
}
