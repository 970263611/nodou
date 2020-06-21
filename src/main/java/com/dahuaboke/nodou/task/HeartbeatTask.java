package com.dahuaboke.nodou.task;

import com.dahuaboke.nodou.manager.ReadOnlyManager;
import com.dahuaboke.nodou.manager.RegisterManager;
import com.dahuaboke.nodou.util.NetUtil;

import java.util.*;

public class HeartbeatTask implements Runnable {

    /**
     * //TODO
     * 当移除数据达到1000，移除100个后效率非常低下
     * v size:8762020-06-20T23:25:49.605
     * remove:192.168.0.160:9999 size:125
     * v size:8752020-06-20T23:26:10.609
     * remove:192.168.0.169:9999 size:126
     * v size:8742020-06-20T23:26:31.614
     * remove:192.168.0.168:9999 size:127
     * v size:8732020-06-20T23:26:52.618
     * remove:192.168.0.167:9999 size:128
     * v size:8722020-06-20T23:27:13.625
     */
    @Override
    public void run() {
        RegisterManager.getInstance().forEach((k, v) -> {
            String[] keys = ((String) k).split("-");
            if (Boolean.valueOf(keys[keys.length - 1])) {
                Iterator<Map.Entry<String, Set<String>>> iterator = ((Map) v).entrySet().iterator();
                Map map = new HashMap();
                while (iterator.hasNext()) {
                    Map.Entry<String, Set<String>> entry = iterator.next();
                    Set<String> set = entry.getValue();
                    Iterator<String> sit = set.iterator();
                    Set removeSet = new HashSet();
                    while (sit.hasNext()) {
                        String url = sit.next();
                        if (!NetUtil.live(url)) {
                            removeSet.add(url);
                            sit.remove();
                            System.out.println("remove node:" + url);
                        }
                    }
                    if (set.isEmpty()) {
                        iterator.remove();
                    }
                    if (((Map) RegisterManager.getInstance().get(k)).isEmpty()) {
                        RegisterManager.getInstance().remove(k);
                    }
                    if (!removeSet.isEmpty()) {
                        map.put(entry.getKey(), removeSet);
                    }
                }
                if (!map.isEmpty()) {
                    ReadOnlyManager.toReadSyncMap.put((String) k, map);
                }
                if(((Map) v).isEmpty()){
                    RegisterManager.getInstance().remove(k);
                }
            }
        });
    }
}
