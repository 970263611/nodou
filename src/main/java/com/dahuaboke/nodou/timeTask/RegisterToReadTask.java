package com.dahuaboke.nodou.timeTask;

import com.dahuaboke.nodou.manager.RegisterManager;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:dahua
 * @date:2020/6/17
 * @description: dahua nodou 2.0
 */
public class RegisterToReadTask implements Runnable {

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock()) {
                RegisterManager.getInstance();
            }
        } finally {
            lock.unlock();
        }

    }
}
