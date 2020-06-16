package com.dahuaboke.nodou.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NodeModel<String, V> extends ConcurrentHashMap<String, V> implements Map<String, V> {

}
