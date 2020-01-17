package com.dahuaboke.nodou.controller;

import com.alibaba.fastjson.JSON;
import com.dahuaboke.nodou.manager.NodeManager;
import com.dahuaboke.nodou.model.RequestModel;
import com.dahuaboke.nodou.model.ResultMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class NodeController {

    @Value("${secret.key}")
    private String secret_key;

    @RequestMapping("/nodou")
    public ResultMsg addNode(@RequestBody RequestModel requestModel) {
        Map map = JSON.parseObject(secret_key, Map.class);
        if (requestModel != null && requestModel.getType() != null) {
            if (requestModel.getUsername() == null || requestModel.getPassword() == null) {
                return new ResultMsg(false, "用户名密码不能为空");
            }
            if (map.get(requestModel.getUsername()).equals(requestModel.getPassword())) {
                if ("add".equals(requestModel.getType())) {
                    if (requestModel.getNameNode() == null || requestModel.getNodeMsg() == null) {
                        return new ResultMsg(false, "注册节点参数不能为空");
                    } else {
                        NodeManager.nodeIps.add(requestModel.getNodeMsg());
                        NodeManager.addNode(requestModel.getUsername(), requestModel.getNameNode(), requestModel.getNodeMsg());
                        return new ResultMsg(true, "注册成功");
                    }
                } else if ("get".equals(requestModel.getType())) {
                    return new ResultMsg(true, NodeManager.getNode(requestModel.getUsername()));
                }else{
                    return new ResultMsg(false, "获取类型错误，请填写正确的方式");
                }
            } else {
                return new ResultMsg(false, "node password error 节点密码错误");
            }
        }else{
            return new ResultMsg(false, "获取类型不能为空");
        }
    }
}
