package com.dahuaboke.nodou.timeTask;

import com.dahuaboke.nodou.manager.ReadOnlyManager;
import com.dahuaboke.nodou.manager.RegisterManager;
import com.dahuaboke.nodou.util.NetUtil;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HeartbeatTask {

    private static ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

    public void run() {
        pool.scheduleAtFixedRate(new Task(), 30, 30, TimeUnit.SECONDS);
    }

    class Task implements Runnable {
        @Override
        public void run() {
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
        }
    }


}
