package com.dahuaboke.nodou.test;

import com.dahuaboke.nodou.model.NodeModel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author: dahua
 * @date: 2020/6/21
 * @description: lianxi jilu
 */
public class CopyTest {

    public static void main(String[] args) {
        //测试nodeModel深拷贝
//        NodeModel nodeModel1 = new NodeModel(){{
//            put("1","1");
//        }};
//        NodeModel nodeModel2 = new NodeModel();
//        nodeModel2.putAll(nodeModel1);
//        nodeModel2.put("1","2");
//        System.out.println(nodeModel1);
        //测试hashset深拷贝 1
//        HashSet set1 = new HashSet(){{
//            add(1);
//            add(2);
//        }};
//        HashSet set2 = new HashSet();
//        set2.addAll(set1);
//        set2.remove(1);
//        System.out.println(set1);
        //测试hashset深拷贝 2
//        HashSet set1 = new HashSet(){{
//            add(1);
//            add(2);
//        }};
//        HashSet set2 = new HashSet();
//        set2.addAll(set1);
//        HashSet set3 = new HashSet();
//        set3.addAll(set1);
//        set1.clear();
//        set2.remove(1);
//        System.out.println(set3);
        //测试hashset深拷贝 3
//        HashSet set1 = new HashSet() {{
//            add(1);
//            add(2);
//        }};
//        HashSet set2 = new HashSet();
//        set2.addAll(set1);
//        HashSet set3 = new HashSet();
//        set3.addAll(set1);
//        set1.clear();
//        Iterator it = set2.iterator();
//        while (it.hasNext()) {
//            int a = (int) it.next();
//            if (a == 1) {
//                it.remove();
//            }
//        }
//        System.out.println(set3);
        //联合测试 这里发现了问题
//        HashSet set1 = new HashSet() {{
//            add(1);
//            add(2);
//        }};
//        NodeModel nodeModel1 = new NodeModel() {{
//            put("1", set1);
//        }};
//        NodeModel nodeModel2 = new NodeModel() {{
//            put("1", set1);
//        }};
//        Iterator it = nodeModel1.entrySet().iterator();
//        while(it.hasNext()){
//            Map.Entry entry = (Map.Entry) it.next();
//            Set set = (Set) entry.getValue();
//            Iterator sit = set.iterator();
//            while (sit.hasNext()){
//                int a = (int) sit.next();
//                if(a == 1){
//                    sit.remove();
//                }
//            }
//        }
//        System.out.println(nodeModel2);
        //优化 变成深拷贝
        HashSet set1 = new HashSet() {{
            add(1);
            add(2);
        }};
        NodeModel nodeModel1 = new NodeModel() {{
            HashSet tset = new HashSet();
            tset.addAll(set1);
            put("1", tset);
        }};
        NodeModel nodeModel2 = new NodeModel() {{
            HashSet tset = new HashSet();
            tset.addAll(set1);
            put("1", tset);
        }};
        Iterator it = nodeModel1.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Set set = (Set) entry.getValue();
            Iterator sit = set.iterator();
            while (sit.hasNext()) {
                int a = (int) sit.next();
                if (a == 1) {
                    sit.remove();
                }
            }
        }
        System.out.println(nodeModel2);
    }
}
