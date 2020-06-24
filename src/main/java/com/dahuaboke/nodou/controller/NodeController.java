package com.dahuaboke.nodou.controller;

import com.dahuaboke.nodou.exception.NodouException;
import com.dahuaboke.nodou.manager.ReadOnlyManager;
import com.dahuaboke.nodou.manager.RegisterManager;
import com.dahuaboke.nodou.manager.WriteReadManager;
import com.dahuaboke.nodou.model.RequestModel;
import com.dahuaboke.nodou.util.NodouUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
public class NodeController {

    private static final Logger log = LoggerFactory.getLogger(NodeController.class);
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd :hh:mm:ss");

    @GetMapping("/nodou")
    public Map getNode(RequestModel requestModel) throws NodouException {
        log.info("获取:" + requestModel.toString() + " 时间：" + LocalDateTime.now().format(dtf));
        NodouUtil.checkParam(requestModel, "get");
        final String key = NodouUtil.assemblyKey(requestModel);
        Map map = ReadOnlyManager.getNode(key);
        if (NodouUtil.isBlank(map)) {
            map = RegisterManager.getNode(key);
            if (NodouUtil.isBlank(map)) {
                map = WriteReadManager.getNode(key);
            }
        }
        if (NodouUtil.isBlank(map)) {
            throw new NodouException("没有找到符合的节点");
        }
        return map;
    }

    @PostMapping("/nodou")
    public String postNode(@RequestBody RequestModel requestModel) throws NodouException {
        log.info("添加:" + requestModel.toString() + " 时间：" + LocalDateTime.now().format(dtf));
        NodouUtil.checkParam(requestModel, "set");
        WriteReadManager.addNode(requestModel);
        return "ok";
    }
}
