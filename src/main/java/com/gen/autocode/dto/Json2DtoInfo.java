package com.gen.autocode.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.utils.HumpUtil;

import java.math.BigDecimal;
import java.util.*;

public class Json2DtoInfo {

    public void parse(String json, Map<String,Map<String,String>> baseInfo,String clsName) throws Exception {
        JSONObject obj;
        if(json.startsWith("{")){
            obj = JSON.parseObject(json);
        }else if(json.startsWith("[")){
            JSONArray array = JSONArray.parseArray(json);
            obj = array.getJSONObject(0);
        }else{
            throw new Exception("字符串不是json字符串");
        }
        Set<Map.Entry<String, Object>> entries = obj.entrySet();
        if(entries.size()>0){
            Map<String,String> info = new HashMap<>();
            for (Map.Entry<String, Object> entry : entries) {
                String key = HumpUtil.convertToJava(entry.getKey());
                Object value = entry.getValue();
                if(value instanceof Integer){
                    info.put(key,"Integer");
                }else if(value instanceof BigDecimal){
                    info.put(key,"BigDecimal");
                }else if(value instanceof String){
                    info.put(key,"String");
                }else{
                    String s = value.toString();
                    if(s.startsWith("{")){
                        info.put(HumpUtil.toUpperCaseFirstOne(key),"Object");
                        parse(s,baseInfo,HumpUtil.toUpperCaseFirstOne(key));
                    }else if(s.startsWith("[{")){
                        info.put(key,"List");
                        parse(s,baseInfo,HumpUtil.toUpperCaseFirstOne(key));
                    }else if(s.startsWith("[")){
                        JSONArray sArray = JSONArray.parseArray(s);
                        Object sObj = sArray.get(0);
                        if(sObj instanceof Integer){
                            info.put(key,"List<Integer>");
                        }else if(sObj instanceof BigDecimal){
                            info.put(key,"List<BigDecimal>");
                        }else {
                            info.put(key,"List<String>");
                        }
                    }
                }
            }
            baseInfo.put(clsName,info);
        }
    }

    public static void main(String[] args) throws Exception {
        String json ="{\n" +
                "    \"reqBody\":{\n" +
                "        \"page\":[1,2,3],\n" +
                "        \"pageSize\":10\n" +
                "    }\n" +
                "}";
        Json2DtoInfo json2DtoInfo = new Json2DtoInfo();
        Map<String,Map<String,String>> baseInfo = new HashMap<>();
        json2DtoInfo.parse(json,baseInfo,"Root");
        //遍历生成java文件
        for (Map.Entry<String, Map<String, String>> entry : baseInfo.entrySet()) {
            String clsName = entry.getKey();
            Map<String, String> propertyList = entry.getValue();
        }
    }
}
