package com.dahuaboke.nodou.task;

import com.dahuaboke.nodou.manager.ReadOnlyManager;
import com.dahuaboke.nodou.manager.RegisterManager;
import com.dahuaboke.nodou.util.NetUtil;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class HeartbeatTask implements Runnable {

    @Override
    public void run() {
        System.out.println("----------HeartbeatTask begin!----------");
        RegisterManager.getInstance().forEach((k, v) -> {
            String[] keys = ((String) k).split("-");
            if (Boolean.valueOf(keys[keys.length - 1])) {
                Iterator iterator = ((Map) v).keySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
                    if (!NetUtil.live(entry.getValue())) {
                        iterator.remove();
                    } else {
                        if (ReadOnlyManager.toReadSyncMap.containsKey(k)) {
                            HashSet set = ReadOnlyManager.toReadSyncMap.get(k);
                            set.add(entry.getKey());
                            ReadOnlyManager.toReadSyncMap.put((String) k, set);
                        } else {
                            ReadOnlyManager.toReadSyncMap.put((String) k, new HashSet());
                        }
                    }
                }
            }
        });
        System.out.println("----------HeartbeatTask end!----------");
    }
}
