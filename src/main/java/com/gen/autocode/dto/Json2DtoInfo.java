package com.gen.autocode.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.utils.HumpUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 根据json字符串转换成dto文件
 */
public class Json2DtoInfo {

    public void parse(String json, Map<String,Map<String,String>> baseInfo,String clsName,int times) throws Exception {
        JSONObject obj;
        if(json.startsWith("{")){
            obj = JSON.parseObject(json);
        }else if(json.startsWith("[{")){
            JSONArray array = JSONArray.parseArray(json);
            obj = array.getJSONObject(0);
        }else if(times == 1 && json.startsWith("[")){
            Map<String,String> info = new HashMap<>();
            JSONArray sArray = JSONArray.parseArray(json);
            Object sObj = sArray.get(0);
            if(sObj instanceof Integer){
                info.put("key","List<Integer>");
            }else if(sObj instanceof BigDecimal){
                info.put("key","List<BigDecimal>");
            }else if(sObj instanceof String){
                info.put("key","List<String>");
            }else if(sObj instanceof Boolean){
                info.put("key","List<Boolean>");
            }else if(sObj instanceof Date){
                info.put("key","List<Date>");
            }
            baseInfo.put(clsName,info);
            return;
        }else{
            throw new Exception("字符串不是json字符串");
        }
        times++;
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
                }else if(value instanceof Boolean){
                    info.put(key,"Boolean");
                }else if(value instanceof Date){
                    info.put(key,"Date");
                }else{
                    String s = value.toString();
                    if(s.startsWith("{")){
                        info.put(HumpUtil.toUpperCaseFirstOne(key),"Object");
                        parse(s,baseInfo,HumpUtil.toUpperCaseFirstOne(key),times);
                    }else if(s.startsWith("[{")){
                        info.put(HumpUtil.toUpperCaseFirstOne(key),"List<"+HumpUtil.toUpperCaseFirstOne(key)+">");
                        parse(s,baseInfo,HumpUtil.toUpperCaseFirstOne(key),times);
                    }else if(s.startsWith("[")){
                        JSONArray sArray = JSONArray.parseArray(s);
                        Object sObj = sArray.get(0);
                        if(sObj instanceof Integer){
                            info.put(key,"List<Integer>");
                        }else if(sObj instanceof BigDecimal){
                            info.put(key,"List<BigDecimal>");
                        }else if(sObj instanceof String){
                            info.put(key,"List<String>");
                        }else if(sObj instanceof Boolean){
                            info.put(key,"List<Boolean>");
                        }else if(sObj instanceof Date){
                            info.put(key,"List<Date>");
                        }
                    }
                }
            }
            baseInfo.put(clsName,info);
        }
    }

    /**
     * 替换模板内容
     * @param replaceMap
     * @return
     */
    public String replaceTemplateContent(Map<String,String> replaceMap){
        try{
            StringBuffer sb = new StringBuffer();
            ClassPathResource resource = new ClassPathResource("template/dto/DtoTemplate") ;
            InputStream inputStream = resource.getInputStream();
            IOUtils.readLines(inputStream).forEach(e->{
                for(Map.Entry<String, String> data : replaceMap.entrySet()){
                    e = e.replace(data.getKey(),data.getValue());
                }
                sb.append(e);
                sb.append("\n");
            });
            return sb.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建文件
     */
    public void createFile(String fileName,String packageOutPath,String sb){
        File directory = new File("");
        File dirFile;
        String outputPath;
        try{
            dirFile = new File(directory.getAbsolutePath()+ "/src/main/java/"+packageOutPath.replace(".", "/")+"/");
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }
            outputPath = dirFile.toString()+"/"+fileName + ".java";
            FileWriter fw = new FileWriter(outputPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sb.toString());
            pw.flush();
            pw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 创建替换模板
     * @return
     */
    public Map<String,String> createReplaceMap(String clsName,Map<String, String> propertyList,String packageOutPath){
        Map<String,String> replaceMap = new HashMap<>();
        replaceMap.put("${dtoName}",clsName);
        replaceMap.put("${dtoPackageOutPath}",packageOutPath);
        //创建属性列表和get、set函数
        StringBuffer columSb = new StringBuffer();
        StringBuffer getSetSb = new StringBuffer();
        for (Map.Entry<String, String> entry : propertyList.entrySet()) {
            String typeName = entry.getKey();
            String type = entry.getValue().equals("Object") ? typeName : entry.getValue();
            String colName = type.startsWith("List") ? HumpUtil.toLowerCaseFirstOne(HumpUtil.convertToJava(typeName)+"s") : HumpUtil.toLowerCaseFirstOne(HumpUtil.convertToJava(typeName));
            columSb.append("private "+type+" "+colName+";\n\t");
            /*columSb.append("@ApiModelProperty(value=\"\")\n" +
                    "\tprivate "+type+" "+colName+";\n\t");*/
            getSetSb.append("public "+type+" get"+HumpUtil.toUpperCaseFirstOne(colName)+"() {\n" +
                    "        return "+colName+";\n" +
                    "    }\n" +
                    "\n" +
                    "    public void set"+HumpUtil.toUpperCaseFirstOne(colName)+"("+type+" "+colName+") {\n" +
                    "        this."+colName+" = "+colName+";\n" +
                    "    }\n\t");
        }
        replaceMap.put("${dtoColumList}",columSb.toString());
        replaceMap.put("${dtoGetSetList}",getSetSb.toString());
        return replaceMap;
    }

    public static void main(String[] args) throws Exception {
        String json = args[0];
        String packageOutPath = args[1];
        Json2DtoInfo json2DtoInfo = new Json2DtoInfo();
        Map<String,Map<String,String>> baseInfo = new HashMap<>();
        json2DtoInfo.parse(json,baseInfo,"Root",1);
        //遍历生成java文件
        for (Map.Entry<String, Map<String, String>> entry : baseInfo.entrySet()) {
            String clsName = entry.getKey();
            Map<String, String> propertyList = entry.getValue();
            json2DtoInfo.createFile(clsName,packageOutPath,json2DtoInfo.replaceTemplateContent(json2DtoInfo.createReplaceMap(clsName,propertyList,packageOutPath)));
        }
    }
}
