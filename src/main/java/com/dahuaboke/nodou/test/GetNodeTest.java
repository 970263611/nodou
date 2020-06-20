package com.dahuaboke.nodou.test;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

/**
 * @author: dahua
 * @date: 2020/6/20
 * @description: lianxi jilu
 */
public class GetNodeTest {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:9888/nodou";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username","dahuaboke");
        params.add("password","dwq520");
        params.add("version","1.0.0");
        params.add("autoRemove","false");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        URI uri = builder.queryParams(params).build().encode().toUri();
        Map result = restTemplate.getForObject(uri, Map.class);
        List<String> list = new ArrayList<>();
        if (result != null) {
            for (Object key : result.keySet()) {
                List<String> valueList = (List<String>) result.get(key);
                for (String value : valueList) {
                    list.add(key + "#" + value);
                }
            }
        }
        System.out.println(list);
    }
}
