package com.dahuaboke.nodou.test;

import com.dahuaboke.nodou.manager.ReadOnlyManager;

/**
 * @author: dahua
 * @date: 2020/6/20
 * @description: lianxi jilu
 */
public class SyncTest {

    public static void main(String[] args) {
        for (int a = 0; a < 100; a++) {
            int finalA = a;
            int finalA1 = a;
            new Thread() {
                public void run() {
                    ReadOnlyManager.getInstance1().put(finalA, finalA1);
                    System.out.print(finalA + " ");
                }
            }.start();
        }
        new Thread() {
            public void run() {
                synchronized (ReadOnlyManager.getInstance1()){
                    System.out.println("-------------------");
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("size:" + ReadOnlyManager.getInstance1().size());
                }
            }
        }.start();
    }
}
