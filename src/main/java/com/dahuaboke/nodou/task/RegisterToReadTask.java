package com.dahuaboke.nodou.task;

import com.dahuaboke.nodou.manager.RegisterManager;
import com.dahuaboke.nodou.model.NodeModel;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:dahua
 * @date:2020/6/17
 * @description: dahua nodou 2.0
 */
public class RegisterToReadTask implements Runnable {

    private NodeModel nodeModel;
    private String name;
    private Lock lock = new ReentrantLock();

    public RegisterToReadTask(NodeModel nodeModel, String name) {
        this.nodeModel = nodeModel;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("----------" + name + ": RegisterToReadTask begin!----------");
        try {
            lock.lock();
            if ("instance1".equals(name)) {
                nodeModel.clear();
                nodeModel.putAll(RegisterManager.getInstance());
            }
            if ("instance2".equals(name)) {

            }
        } finally {
            lock.unlock();
        }
        System.out.println("----------" + name + ": RegisterToReadTask end!----------");
    }
}
