package com.dahuaboke.nodou.task;

import com.dahuaboke.nodou.manager.ReadOnlyManager;
import com.dahuaboke.nodou.manager.RegisterManager;
import com.dahuaboke.nodou.model.NodeModel;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author:dahua
 * @date:2020/6/17
 * @description: dahua nodou 2.0
 */
public class RegisterToReadTask implements Runnable {

    private NodeModel nodeModel;
    private String name;

    public RegisterToReadTask(NodeModel nodeModel, String name) {
        this.nodeModel = nodeModel;
        this.name = name;
    }

    @Override
    public void run() {
        if ("instance1".equals(name)) {
            if (!RegisterManager.getInstance().isEmpty()) {
                synchronized (ReadOnlyManager.getInstance1()) {
                    nodeModel.clear();
                    nodeModel.putAll(RegisterManager.getInstance());
                }
            }
        }
        if ("instance2".equals(name)) {
            if (!ReadOnlyManager.toReadSyncMap.isEmpty()) {
                ReadOnlyManager.toReadSyncMap.forEach((k, v) -> {
                    if (nodeModel.containsKey(k)) {
                        Map m = ReadOnlyManager.toReadSyncMap.get(k);
                        Iterator<Map.Entry<String, Set>> iterator = m.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, Set> entry = iterator.next();
                            boolean hasKey = ((Map) nodeModel.get(k)).containsKey(entry.getKey());
                            if (hasKey) {
                                Set s = (Set) ((Map) nodeModel.get(k)).get(entry.getKey());
                                s.removeAll(entry.getValue());
                                if (s.isEmpty()) {
                                    ((Map) nodeModel.get(k)).remove(entry.getKey());
                                }
                            }
                            if(((Map) nodeModel.get(k)).isEmpty()){
                                nodeModel.remove(k);
                            }
                        }
                    }
                });
                ReadOnlyManager.toReadSyncMap.clear();
            }
        }
    }
}
