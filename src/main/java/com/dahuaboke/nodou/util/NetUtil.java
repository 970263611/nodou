package com.dahuaboke.nodou.util;

import java.io.IOException;
import java.net.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;

public class NetUtil {

    //ip心跳超时存储容器（黑名单）
    private static HashMap blackMap = new HashMap();
    //ip心跳超时存储容器（白名单）
    private static HashMap whiteMap = new HashMap();

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
        //走缓存，判断是否在黑名单
        if (checkIpState(blackMap, ip)) {
            return false;
        }
        //走缓存，判断是否在白名单
        if (checkIpState(whiteMap, ip)) {
            return true;
        }
        boolean flag = true;
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ip.split(":")[0], Integer.parseInt(ip.split(":")[1])));
            whiteMap.put(ip, LocalDateTime.now());
        } catch (IOException e) {
            blackMap.put(ip, LocalDateTime.now());
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

    private static boolean checkIpState(HashMap map, String ip) {
        //走缓存，判断是否在黑白名单
        if (NodouUtil.isNotBlank(map) && map.get(ip) != null) {
            //这里存在线程安全问题
            //因为现在数据量不大，所以单线程执行应该不会时间太长，暂时不会有安全问题
            //TODO
            LocalDateTime back = (LocalDateTime) map.get(ip);
            Duration duration = Duration.between(LocalDateTime.now(), back);
            //默认ip超时记录10分钟
            if (duration.toMillis() > 1000 * 60 * 10) {
                map.remove(ip);
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
