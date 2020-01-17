package com.dahuaboke.nodou.timeTask;

import com.dahuaboke.nodou.manager.NodeManager;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PersistenceTask {

    private static ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

    public PersistenceTask(String address) {
        run(address);
    }

    public void run(String address) {
        pool.scheduleAtFixedRate(new Task(address), 0, 60 * 5, TimeUnit.SECONDS);
    }

    class Task implements Runnable {
        private String address;

        public Task(String log_address) {
            address = log_address;
        }

        @Override
        public void run() {
            String data = NodeManager.persistence();
            if (data != null && !"".equals(data) && !"[]".equals(data)) {
                File file = new File(address);
                FileOutputStream fos = null;
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                        fos = new FileOutputStream(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        fos = new FileOutputStream(file, true);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                    osw.write(data);
                    osw.write("\r\n");
                    osw.close();
                    fos.close();
                    System.out.println("数据持久化完成");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
