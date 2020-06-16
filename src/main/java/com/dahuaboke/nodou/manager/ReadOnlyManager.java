package com.dahuaboke.nodou.manager;

import com.dahuaboke.nodou.model.NodeModel;

import java.util.Map;

/**
 * @author:dahua
 * @date:2020/6/16
 * @description: dahua nodou 2.0
 */
public class ReadOnlyManager {

    /**
     * 单例 获取NodeModel
     *
     * @return NodeModel
     */
    private static NodeModel getInstance() {
        return SingleRegisterNode.INSTANCE;
    }

    private static class SingleRegisterNode {
        private static final NodeModel INSTANCE = new NodeModel();
    }

    public static Map getNode(String key) {
        return (Map) getInstance().get(key);
    }
}
