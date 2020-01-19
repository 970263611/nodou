package com.dahuaboke.nodou.manager;

import com.dahuaboke.nodou.model.NodeModel;
import com.dahuaboke.nodou.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.*;

public class NodeManager {

    public volatile static NodeModel parentNode;
    public volatile static Set<String> nodeIps = new HashSet();

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
    public static void removeNode(String[] hosts) {
        if (hosts == null || hosts.length <= 0) {
            System.err.println("parameters passed in is illegal 传入参数不合法");
        } else {
            Iterator it = parentNode.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Map map = (Map) entry.getValue();
                Iterator child = map.entrySet().iterator();
                while (child.hasNext()) {
                    Map.Entry childEntry = (Map.Entry) child.next();
                    Set<String> set = (Set<String>) childEntry.getValue();
                    for (String host : hosts) {
                        if (set.contains(host)) {
                            set.remove(host);
                        }
                    }
                    if(set.size() == 0){
                        map.remove(childEntry.getKey());
                    }else{
                        map.put(childEntry.getKey(), set);
                    }
                }
            }
            Iterator last = parentNode.entrySet().iterator();
            while (last.hasNext()) {
                Map.Entry entry = (Map.Entry) last.next();
                if(((NodeModel) entry.getValue()).size() == 0){
                    last.remove();
                }
            }
        }
    }

    /**
     * 持久化方法
     */
    public static String persistence() {
        return parentNode.toString();
    }
}
