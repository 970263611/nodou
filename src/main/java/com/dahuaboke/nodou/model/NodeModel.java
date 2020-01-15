package com.dahuaboke.nodou.model;

import com.alibaba.fastjson.JSON;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NodeModel<K, V> extends ConcurrentHashMap<K, V> implements Map<K, V> {

    @Override
    public String toString() {
        return JSON.toJSONString(super.entrySet());
    }
}
