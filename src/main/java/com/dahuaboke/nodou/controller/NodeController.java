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
@RequestMapping("/nodou")
public class NodeController {

    @Value("${secret.key}")
    private String secret_key;

    @RequestMapping("/addNode")
    public ResultMsg addNode(@RequestBody RequestModel requestModel) {
        Map map = JSON.parseObject(secret_key, Map.class);
        if (requestModel == null || (requestModel.getUsername() == null) || (requestModel.getPassword() == null) ||
                (requestModel.getNameNode() == null) || (requestModel.getNodeMsg() == null)) {
            return new ResultMsg(false, "参数不合法");
        }
        if (map.get(requestModel.getUsername()).equals(requestModel.getPassword())) {
            NodeManager.addNode(requestModel.getUsername(), requestModel.getNameNode(), requestModel.getNodeMsg());
            return new ResultMsg(true, "注册成功");
        } else {
            return new ResultMsg(false, "node password error 节点密码错误");
        }
    }

    @RequestMapping("/getNode")
    public ResultMsg getNode(@RequestBody RequestModel requestModel) {
        Map map = JSON.parseObject(secret_key, Map.class);
        if (requestModel == null || (requestModel.getUsername() == null) || (requestModel.getPassword() == null)) {
            return new ResultMsg(false, "参数不合法");
        }
        if (map.get(requestModel.getUsername()).equals(requestModel.getPassword())) {
            return new ResultMsg(true, NodeManager.getNode(requestModel.getUsername()));
        } else {
            return new ResultMsg(false, "node password error 节点密码错误");
        }
    }
}
