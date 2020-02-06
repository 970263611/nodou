package com.dahuaboke.nodou.controller;

import com.dahuaboke.nodou.manager.NodeManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GovernController {

    @RequestMapping("/")
    public String govern(){
        return "govern";
    }

    @RequestMapping("/governData")
    @ResponseBody
    public Object governData(){
        return NodeManager.parentNode;
    }
}
