package com.lciclcazz.webhook.utils;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by IciclcAzz on 2017/06/26 17:18
 *
 */
public class Tools {

    public static byte[] getAllReq(HttpServletRequest req) {
        byte[] reqAll = null;

        try (InputStream in = req.getInputStream(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            while (true) {
                int i = in.read();
                if (i == -1) {
                    reqAll = out.toByteArray();
                    break;
                }
                out.write(i);
            }
        } catch (IOException | NullPointerException  e) {
            e.printStackTrace();
        }
        return reqAll;
    }


    public static JsonNode getEvent(byte[] request,String key){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode events = null;
        try {
            events = mapper.readTree(request).path(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }

    public static JsonNode getEvent(String strJson){
        return getEvent(strJson,null);
    }

    public static JsonNode getEvent(String strJson,String key){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode events = null;
        try {
            if(key!=null) events = mapper.readTree(strJson).path(key);
            else events = mapper.readTree(strJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }
}
