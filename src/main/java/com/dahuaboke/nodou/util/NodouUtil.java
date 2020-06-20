package com.dahuaboke.nodou.util;

import com.alibaba.fastjson.JSON;
import com.dahuaboke.nodou.exception.NodouException;
import com.dahuaboke.nodou.model.RequestModel;

import java.util.Map;

/**
 * @author:dahua
 * @date:2020/6/16
 * @description: dahua nodou 2.0
 */
public class NodouUtil {

    public static String secret_key;

    public static String assemblyKey(RequestModel model) {
        StringBuffer sb = new StringBuffer();
        sb.append(model.getUsername());
        sb.append("-");
        sb.append(model.getVersion());
        sb.append("-");
        sb.append(model.isAutoRemove());
        return new String(sb);
    }

    public static void checkParam(RequestModel requestModel, String type) throws NodouException {
        Map map = JSON.parseObject(secret_key, Map.class);
        if (requestModel != null) {
            String username = requestModel.getUsername();
            String password = requestModel.getPassword();
            if (isBlank(username) || isBlank(password)) {
                throw new NodouException("用户名密码不能为空");
            }
            if (map.containsKey(username)) {
                if (map.get(username).equals(password)) {
                    if ("get".equals(type)) {
                        return;
                    } else if ("set".equals(type)) {
                        if (isBlank(requestModel.getNodeKey()) || isBlank(requestModel.getNodeValue())) {
                            throw new NodouException("注册节点参数不能为空");
                        }
                        return;
                    }
                } else {
                    throw new NodouException("node password error 节点密码错误");
                }
            } else {
                throw new NodouException("node password error 请先配置用户名密码");
            }
        }
        throw new NodouException("请设置参数");
    }

    public static boolean isBlank(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            String s = (String) obj;
            if (s == null || "".equals(s)) {
                return true;
            }
        } else if (obj instanceof Map) {
            Map m = (Map) obj;
            if (m == null || m.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotBlank(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            String s = (String) obj;
            if (s != null && "".equals(s)) {
                return true;
            }
        } else if (obj instanceof Map) {
            Map m = (Map) obj;
            if (m != null && !m.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
