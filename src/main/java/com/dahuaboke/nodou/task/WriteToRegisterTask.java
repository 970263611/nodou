package com.dahuaboke.nodou.task;

import com.dahuaboke.nodou.manager.RegisterManager;
import com.dahuaboke.nodou.manager.WriteReadManager;
import com.dahuaboke.nodou.util.NodouUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author:dahua
 * @date:2020/6/17
 * @description: dahua nodou 2.0
 */
public class WriteToRegisterTask implements Runnable {

    @Override
    public void run() {
        System.out.println("----------WriteToRegisterTask begin!----------");
        if (!WriteReadManager.getInstance().isEmpty()) {
            ConcurrentHashMap map = new ConcurrentHashMap();
            //复制 - 清空 这里要保证一致性，所以需要加锁
            synchronized (this) {
                map.putAll(WriteReadManager.getInstance());
                WriteReadManager.clear();
            }
            map.forEach((k, v) -> {
                Map m = (Map) RegisterManager.getInstance().get(k);
                if (NodouUtil.isNotBlank(m)) {
                    m.putAll(((Map) v));
                } else {
                    RegisterManager.getInstance().put(k, v);
                }
            });
            map = null;
        }
        System.out.println("----------WriteToRegisterTask end!----------");
    }
}
