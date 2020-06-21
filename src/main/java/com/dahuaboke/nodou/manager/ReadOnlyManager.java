package com.dahuaboke.nodou.manager;

import com.dahuaboke.nodou.model.NodeModel;
import com.dahuaboke.nodou.util.NodouUtil;

import java.util.Collections;
import java.util.HashMap;
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
    public static Map<String, Map<String, Set>> toReadSyncMap = Collections.synchronizedMap(new HashMap());

    /**
     * 单例 获取NodeModel 这里采用双读机制，提高吞吐量，保证并发
     * INSTANCE1 因为是全量清空和全量set，所以要加锁
     *
     * @return NodeModel
     */
    public static NodeModel getInstance1() {
        synchronized (SingleRegisterNode1.INSTANCE) {
            return SingleRegisterNode1.INSTANCE;
        }
    }

    public static NodeModel getInstance2() {
        return SingleRegisterNode2.INSTANCE;
    }

    private static class SingleRegisterNode1 {
        private static final NodeModel INSTANCE = new NodeModel();
    }

    private static class SingleRegisterNode2 {
        private static final NodeModel INSTANCE = new NodeModel()/*.clone(RegisterManager.getInstance())*/;
    }

    /**
     * 双读
     *
     * @param key
     * @return
     */
    public static Map getNode(String key) {
        //先从INSTANCE2中取，因为INSTANCE1可能同步加锁，导致阻塞
        Map m = (Map) getInstance2().get(key);
        return (Map) (NodouUtil.isBlank(m) ? m : getInstance1().get(key));
    }
}
