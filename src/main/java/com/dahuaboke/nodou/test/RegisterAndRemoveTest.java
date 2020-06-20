package com.dahuaboke.nodou.test;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @author: dahua
 * @date: 2020/6/20
 * @description: lianxi jilu
 */
public class RegisterAndRemoveTest {

    private static String rpc_regist_address = "localhost:9888/nodou?username=dahuaboke&password=dwq520";

    public static void main(String[] args) {
        for (int a = 0; a < 2; a++) {
            String nodeKey = "com.dahuaboke.service" + a;
            String nodeValue = "192.168.3." + a + ":9999";
            String version = "1.0.0";
            HashMap<String, String> paramsMap = new HashMap<>();
            String[] params = rpc_regist_address.split("\\?")[1].split("&");
            for (String param : params) {
                paramsMap.put(param.split("=")[0], param.split("=")[1]);
            }
            paramsMap.put("nodeKey", nodeKey);
            paramsMap.put("nodeValue", nodeValue);
            paramsMap.put("version", version);
//            paramsMap.put("autoRemove", "false");
            paramsMap.put("autoRemove", "true");

            RestTemplate restTemplate = new RestTemplate();
            String url = "http://" + rpc_regist_address.split("\\?")[0];
            String result = restTemplate.postForObject(url, paramsMap, String.class);
            if ("ok".equals(result)) {
                System.out.println("注册成功，namenode：" + nodeKey + "。nodemsg：" + nodeValue + "。version：" + version);
            }
        }
    }
}
