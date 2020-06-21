package com.dahuaboke.nodou.manager;

import com.dahuaboke.nodou.model.NodeModel;
import com.dahuaboke.nodou.model.RequestModel;
import com.dahuaboke.nodou.util.NodouUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author:dahua
 * @date:2020/6/16
 * @description: dahua nodou 2.0
 */
public class WriteReadManager {

    /**
     * 单例 获取NodeModel
     * 因为有全量清空操作，所以要加锁
     *
     * @return NodeModel
     */
    public static NodeModel getInstance() {
        synchronized (SingleRegisterNode.INSTANCE) {
            return SingleRegisterNode.INSTANCE;
        }
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
        if (getInstance().containsKey(key)) {
            Map map = ((Map) getInstance().get(key));
            if (NodouUtil.isNotBlank(map)) {
                if (map.containsKey(model.getNodeKey())) {
                    Set set = (Set) map.get(model.getNodeKey());
                    set.add(model.getNodeValue());
                } else {
                    Set set = new HashSet();
                    set.add(model.getNodeValue());
                    map.put(model.getNodeKey(), set);
                }
            }
        } else {
            getInstance().put(key, new NodeModel<String, Set>() {{
                put(model.getNodeKey(), new HashSet() {{
                    add(model.getNodeValue());
                }});
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
