package com.dahuaboke.nodou.task;

import com.alibaba.fastjson.JSON;
import com.dahuaboke.nodou.manager.RegisterManager;

import java.io.*;

public class PersistenceTask implements Runnable {

    private String address;

    public PersistenceTask(String log_address) {
        address = log_address;
    }

    @Override
    public void run() {
        System.out.println("----------PersistenceTask begin!----------");
        String data = JSON.toJSONString(RegisterManager.getInstance());
        if (data != null && !"".equals(data) && !"{}".equals(data)) {
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
        System.out.println("----------PersistenceTask end!----------");
    }
}
