package com.dahuaboke.nodou.manager;

import com.dahuaboke.nodou.model.NodeModel;
import com.dahuaboke.nodou.util.NodouUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author:dahua
 * @date:2020/6/16
 * @description: dahua nodou 2.0
 */
public class ReadOnlyManager {

    /**
     * value是list，namespace - set（有问题的接口集合）
     */
    public static HashMap<String,HashSet> toReadSyncMap = new HashMap();

    /**
     * 单例 获取NodeModel 这里采用双读机制，提高吞吐量，保证并发
     *
     * @return NodeModel
     */
    public static NodeModel getInstance1() {
        return SingleRegisterNode1.INSTANCE;
    }

    public static NodeModel getInstance2() {
        return SingleRegisterNode2.INSTANCE;
    }

    private static class SingleRegisterNode1 {
        private static final NodeModel INSTANCE = new NodeModel();
    }

    private static class SingleRegisterNode2 {
        private static final NodeModel INSTANCE = new NodeModel();
    }

    /**
     * 双读
     * @param key
     * @return
     */
    public static Map getNode(String key) {
        Map m = (Map) getInstance1().get(key);
        return (Map) (NodouUtil.isBlank(m) ? getInstance2().get(key) : m);
    }
}
