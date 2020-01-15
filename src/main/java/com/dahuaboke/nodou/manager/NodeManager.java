package com.dahuaboke.nodou.manager;

import com.dahuaboke.nodou.model.NodeModel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NodeManager {

    private volatile static NodeModel parentNode = new NodeModel<String, NodeModel>();

    /**
     * 单例
     *
     * @return
     */
    public static NodeModel getInstance(String key) {
        if (parentNode.get(key) == null) {
            NodeModel newNodeModel = new NodeModel<String, Set<String>>();
            parentNode.put(key, newNodeModel);
            return newNodeModel;
        } else {
            return (NodeModel) parentNode.get(key);
        }
    }

    /**
     * 新建节点
     *
     * @param key
     * @param value
     */
    public static void addNode(String name, Object key, Object value) {
        NodeModel nodeModel = getInstance(name);
        Set set = null;
        if (nodeModel.containsKey(key)) {
            set = (Set<String>) nodeModel.get(key);
            if (!set.contains(value)) {
                set.add(value);
            }
        } else {
            set = new HashSet() {{
                add(value);
            }};
        }
        nodeModel.put(key, set);
    }

    /**
     * 获取单一节点
     *
     * @param name,key
     * @return
     */
    public static Object getNode(String name,Object key) {
        NodeModel nodeModel = getInstance(name);
        return nodeModel.get(key);
    }

    /**
     * 获取全部节点
     *
     * @param name
     * @return
     */
    public static Object getNode(String name) {
        return getInstance(name);
    }

    /**
     * 移除节点方法（根据key）
     *
     * @param key
     */
    public static void removeNode(String name,Object key) {
        NodeModel nodeModel = getInstance(name);
        nodeModel.remove(key);
    }

    /**
     * 批量移除节点
     *
     * @param hosts
     */
    public static void removeNode(String name, String[] hosts) {
        NodeModel nodeModel = getInstance(name);
        if (hosts == null || hosts.length <= 0) {
            System.err.println("parameters passed in is illegal 传入参数不合法");
        } else {
            for (String host : hosts) {
                Iterator it = nodeModel.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    Set<String> set = (Set<String>) entry.getValue();
                    for (String ip : set) {
                        if (ip.contains(host)) {
                            set.remove(ip);
                        }
                    }
                    nodeModel.put(host, set);
                }
            }
        }
    }

    /**
     * 持久化方法
     */
    public static void persistence(String name) {
        NodeModel nodeModel = getInstance(name);
        String data = nodeModel.toString();
        System.out.println(data);
    }
}
