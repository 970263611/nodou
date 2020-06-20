package com.dahuaboke.nodou.task;

import com.dahuaboke.nodou.manager.ReadOnlyManager;
import com.dahuaboke.nodou.manager.RegisterManager;
import com.dahuaboke.nodou.manager.WriteReadManager;
import com.dahuaboke.nodou.util.NodouUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author:dahua
 * @date:2020/6/17
 * @description: dahua nodou 2.0
 */
public class WriteToRegisterTask implements Runnable {

    @Override
    public void run() {
        if (!WriteReadManager.getInstance().isEmpty()) {
            ConcurrentHashMap map = new ConcurrentHashMap();
            //全量清空要加锁
            synchronized (WriteReadManager.getInstance()) {
                map.putAll(WriteReadManager.getInstance());
                WriteReadManager.clear();
            }
            map.forEach((k, v) -> {
                Map m1 = (ConcurrentHashMap) RegisterManager.getInstance().get(k);
                if (NodouUtil.isNotBlank(m1)) {
                    ((Map) v).forEach((k1, v1) -> {
                        if (m1.containsKey(k1)) {
                            ((Set) m1.get(k1)).addAll((Set) v1);
                        } else {
                            m1.put(k1, v1);
                        }
                    });
                } else {
                    RegisterManager.getInstance().put(k, v);
                }
                Map m2 = (ConcurrentHashMap) ReadOnlyManager.getInstance2().get(k);
                if (NodouUtil.isNotBlank(m2)) {
                    ((Map) v).forEach((k1, v1) -> {
                        if (m2.containsKey(k1)) {
                            ((Set) m2.get(k1)).addAll((Set) v1);
                        } else {
                            m2.put(k1, v1);
                        }
                    });
                } else {
                    ReadOnlyManager.getInstance2().put(k, v);
                }
            });
            map = null;
        }
    }
}
