package com.dahuaboke.nodou.timeTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HeartbeatTask {

    private static ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

    public HeartbeatTask() {
        run();
    }

    public void run() {
        pool.scheduleAtFixedRate(new Task(), 30, 30, TimeUnit.SECONDS);
    }

    class Task implements Runnable {
        @Override
        public void run() {
            if (NodeManager.nodeIps != null && NodeManager.nodeIps.size() > 0) {
                List<String> list = new ArrayList<>();
                for (String ip : NodeManager.nodeIps) {
                    if(!live(ip)){
                        list.add(ip);
                    }
                }
                if(list.size()>0){
                    for(String host : list){
                        NodeManager.nodeIps.remove(host);
                    }
                    NodeManager.removeNode(list.toArray(new String[list.size()]));
                }
            }
        }
    }

    public boolean live(String ip){
        boolean flag = true;
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ip.split(":")[0], Integer.parseInt(ip.split(":")[1])));
        } catch (IOException e) {
            flag = false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
}
