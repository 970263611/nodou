package com.dahuaboke.nodou.controller;

import com.dahuaboke.nodou.exception.NodouException;
import com.dahuaboke.nodou.manager.ReadOnlyManager;
import com.dahuaboke.nodou.manager.RegisterManager;
import com.dahuaboke.nodou.manager.WriteReadManager;
import com.dahuaboke.nodou.model.NodeModel;
import com.dahuaboke.nodou.model.RequestModel;
import com.dahuaboke.nodou.task.HeartbeatTask;
import com.dahuaboke.nodou.util.NodouUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GovernController {

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/login")
    public String login1() {
        return "login";
    }

    @GetMapping("/govern")
    public String govern(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "login";
        }
        return "govern";
    }

    @PostMapping("/toLogin")
    @ResponseBody
    public String govern(RequestModel requestModel, HttpServletRequest request) {
        try {
            NodouUtil.checkParam(requestModel, "get");
        } catch (NodouException e) {
            return "username or password error";
        }
        HttpSession session = request.getSession();
        session.setAttribute("username", requestModel.getUsername());
        return "ok";
    }

    @RequestMapping("/governWriteData")
    @ResponseBody
    public Object governWriteData(HttpServletRequest request) {
        return resultMethod(request, WriteReadManager.getInstance());
    }

    @RequestMapping("/governRegisterData")
    @ResponseBody
    public Object governRegisterData(HttpServletRequest request) {
        return resultMethod(request, RegisterManager.getInstance());
    }

    @RequestMapping("/governReadData1")
    @ResponseBody
    public Object governReadData1(HttpServletRequest request) {
        return resultMethod(request, ReadOnlyManager.getInstance1());
    }

    @RequestMapping("/governReadData2")
    @ResponseBody
    public Object governReadData2(HttpServletRequest request) {
        return resultMethod(request, ReadOnlyManager.getInstance2());
    }

    @RequestMapping("/getHeartTime")
    @ResponseBody
    public Object getHeartTime() {
        return HeartbeatTask.getHeartTime();
    }

    private NodeModel resultMethod(HttpServletRequest request, NodeModel nodeModel) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if ("admin".equals(username)) {
            return nodeModel;
        }
        NodeModel result = new NodeModel();
        nodeModel.forEach((k, v) -> {
            if (((String) k).contains(username)) {
                result.put(k, v);
            }
        });
        return result;
    }
}
