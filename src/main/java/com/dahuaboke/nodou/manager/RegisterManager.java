package com.dahuaboke.nodou.manager;

import com.dahuaboke.nodou.model.NodeModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author:dahua
 * @date:2020/6/16
 * @description: dahua nodou 2.0
 */
public class RegisterManager {

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
     * 根据用户的命名空间添加节点
     *
     * @param namespace = username + version + autoRemove
     * @param v
     */
    protected static void addNode(String namespace, Map v) {
        //强转必须指定Map具体实例，否则是浅拷贝
        Map map = (ConcurrentHashMap) getInstance().get(namespace);
        if (map != null) {
            map.putAll(v);
        } else {
            getInstance().put(namespace, v);
        }
    }

    /**
     * 整体移除用户命名空间
     *
     * @param namespace
     */
    protected static void removeNamespace(String namespace) {
        ((ConcurrentHashMap) getInstance().get(namespace)).remove(namespace);
    }

    /**
     * 移除用户命名空间下的指定节点
     *
     * @param namespace
     * @param k
     */
    protected static void removeNode(String namespace, String k) {
        Map map = (ConcurrentHashMap) getInstance().get(namespace);
        if (map != null) {
            map.remove(k);
        }
    }

    public static Map getNode(String key) {
        return (Map) getInstance().get(key);
    }

}
