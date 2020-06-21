package com.dahuaboke.nodou.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NodeModel<K, V> extends ConcurrentHashMap<K, V> implements Map<K, V> {

    //强制深拷贝
    @Override
    public V put(K key, V value) {
        V v = clone(value);
        return super.put(key, v);
    }

    public V clone(V obj) {
        V o = null;
        try {
            // 写入字节流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obs = new ObjectOutputStream(out);
            obs.writeObject(obj);
            obs.close();

            // 分配内存，写入原始对象，生成新对象
            ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(ios);
            // 返回生成的新对象
            o = (V) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }
}
