package com.gen.autocode;

import com.gen.autocode.GenProp.GenCommon;
import com.gen.autocode.GenProp.GenProperties;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 自动替换模板内容值
 */
public class GenRepTemp {
    public static void main(String[] args){
        //CtrlTemplate、EntityTemplate、ImplRedisTemplate、ImplServiceTemplate、
        //InftDaoTemplate、InftRedisTemplate、InftServiceTemplate、RespTemplate、XmlTemplate
        String templateName = "xxx";
        GenProperties.templatePath = "template/cache_template_v2";
        Map<String,String> replaceMap = new HashMap<String,String>(){{
            put("DailyAmount","${entityName}");
            put("dailyAmount","${entityObj}");
            put("daId","${entityId}");
            put("DaId","${upperFirstEntityId}");
        }};
        String content = GenCommon.replaceTemplateContent(templateName, replaceMap);
        try {
            File directory = new File("");
            File dirFile = new File(directory.getAbsolutePath()+ "/src/main/resources/"+GenProperties.templatePath+"/"+templateName);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }
            String outputPath = dirFile.toString();
            FileWriter fw = new FileWriter(outputPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(content.toString());
            pw.flush();
            pw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
