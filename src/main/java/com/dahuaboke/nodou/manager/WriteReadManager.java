package com.dahuaboke.nodou.manager;

import com.dahuaboke.nodou.model.NodeModel;
import com.dahuaboke.nodou.model.RequestModel;
import com.dahuaboke.nodou.util.NodouUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author:dahua
 * @date:2020/6/16
 * @description: dahua nodou 2.0
 */
public class WriteReadManager {

    /**
     * 单例 获取NodeModel
     *
     * @return NodeModel
     */
    public static NodeModel getInstance() {
        return SingleRegisterNode.INSTANCE;
    }

    private static class SingleRegisterNode {
        private static final NodeModel INSTANCE = new NodeModel();
    }

    /**
     * map内部数据格式
     * username+version+autoRemove     interfaces    values
     *
     * @param model
     */
    public static void addNode(RequestModel model) {
        String key = NodouUtil.assemblyKey(model);
        Map map = (ConcurrentHashMap) getInstance().get(key);
        if (map != null) {
            map.put(model.getNodeKey(), model.getNodeValue());
        } else {
            getInstance().put(key, new ConcurrentHashMap<String, String>() {{
                put(model.getNodeKey(), model.getNodeValue());
            }});
        }
    }

    public static Map getNode(String key) {
        return (Map) getInstance().get(key);
    }

    public static void clear() {
        getInstance().clear();
    }

}
