package com.dahuaboke.nodou.util;

import java.io.IOException;
import java.net.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;

public class NetUtil {

    //ip心跳超时存储容器（黑名单）
    private static HashMap map = new HashMap();

    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("IP地址获取失败" + e.toString());
        }
        return "";
    }

    /**
     * 仿redis懒过期模式
     *
     * @param ip
     * @return
     */
    public static boolean live(String ip) {
        //先走缓存，判断是否在黑名单
        if (NodouUtil.isNotBlank(map) && map.get(ip) != null) {
            //这里存在线程安全问题
            //因为现在数据量不大，所以单线程执行应该不会时间太长，暂时不会有安全问题
            //TODO
            LocalDateTime back = (LocalDateTime) map.get(ip);
            Duration duration = Duration.between(LocalDateTime.now(), back);
            //默认ip超时记录10分钟
            if (duration.toMillis() > 1000 * 60 * 10) {
                map.remove(ip);
            } else {
                return false;
            }
        }
        boolean flag = true;
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ip.split(":")[0], Integer.parseInt(ip.split(":")[1])));
        } catch (IOException e) {
            map.put(ip, LocalDateTime.now());
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
