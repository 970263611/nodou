package com.dahuaboke.nodou.timeTask;

import com.dahuaboke.nodou.manager.RegisterManager;
import com.dahuaboke.nodou.manager.WriteReadManager;

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
            //复制 - 清空 这里要保证一致性，所以需要加锁
            synchronized (this) {
                map.putAll(WriteReadManager.getInstance());
                WriteReadManager.clear();
            }
            RegisterManager.getInstance().putAll(map);
        }
    }
}
