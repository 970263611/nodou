package com.dahuaboke.nodou.controller;

import com.dahuaboke.nodou.exception.NodouException;
import com.dahuaboke.nodou.manager.ReadOnlyManager;
import com.dahuaboke.nodou.manager.RegisterManager;
import com.dahuaboke.nodou.manager.WriteReadManager;
import com.dahuaboke.nodou.model.RequestModel;
import com.dahuaboke.nodou.util.NodouUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GovernController {

    @RequestMapping("/")
    public String govern(RequestModel requestModel) throws NodouException {
        if (!"admin".equals(requestModel.getUsername())) {
            NodouUtil.checkParam(requestModel, "get");
        }
        return "govern";
    }

    @RequestMapping("/governWriteData")
    @ResponseBody
    public Object governWriteData() {
        return WriteReadManager.getInstance();
    }

    @RequestMapping("/governRegisterData")
    @ResponseBody
    public Object governRegisterData() {
        return RegisterManager.getInstance();
    }

    @RequestMapping("/governReadData1")
    @ResponseBody
    public Object governReadData1() {
        return ReadOnlyManager.getInstance1();
    }

    @RequestMapping("/governReadData2")
    @ResponseBody
    public Object governReadData2() {
        return ReadOnlyManager.getInstance2();
    }
}
