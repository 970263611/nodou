package com.dahuaboke.nodou.controller;

import com.dahuaboke.nodou.exception.NodouException;
import com.dahuaboke.nodou.manager.ReadOnlyManager;
import com.dahuaboke.nodou.manager.RegisterManager;
import com.dahuaboke.nodou.manager.WriteReadManager;
import com.dahuaboke.nodou.model.RequestModel;
import com.dahuaboke.nodou.util.NodouUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class NodeController {

    @GetMapping("/nodou")
    public Map getNode(@RequestBody RequestModel requestModel) throws NodouException {
        NodouUtil.checkParam(requestModel);
        final String key = NodouUtil.assemblyKey(requestModel);
        Map map = ReadOnlyManager.getNode(key);
        if (NodouUtil.isBlank(map)) {
            map = WriteReadManager.getNode(key);
            if (NodouUtil.isBlank(map)) {
                map = RegisterManager.getNode(key);
            }
        }
        if (NodouUtil.isBlank(map)) {
            throw new NodouException("没有找到符合的节点");
        }
        return map;
    }

    @PostMapping("/nodou")
    public void postNode(@RequestBody RequestModel requestModel) throws NodouException {
        NodouUtil.checkParam(requestModel);
        WriteReadManager.addNode(requestModel);
    }
}
