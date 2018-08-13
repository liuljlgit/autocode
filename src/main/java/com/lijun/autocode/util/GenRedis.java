package com.lijun.autocode.util;

import com.lijun.autocode.GenProp.GenCommon;
import com.lijun.autocode.GenProp.GenProperties;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 自动生成Redis
 * @author lijun
 */
public class GenRedis {

    /**
     * 构造函数
     */
    public GenRedis() {
        try{
            //导入列表
            Set<String> inftImportList = new HashSet<>();
            Set<String> implImportList = new HashSet<>();
            implImportList.add(GenProperties.entityFullPath+";");
            implImportList.add(GenProperties.inftRedisFullPath+";");
            implImportList.add(GenProperties.BaseRedisPath+".BaseRedis;");

            //Redis接口内容替换
            Map<String,String> inftReplaceMap = new HashMap<>();
            inftReplaceMap.put("${packageName}",GenProperties.redisPackageOutPath.concat(".inft"));
            inftReplaceMap.put("${entityName}",GenProperties.entityName);
            inftReplaceMap.put("${entityObj}",GenProperties.objName);
            //导入列表请在最后设置
            inftReplaceMap.put("${importList}",GenCommon.changeImportSetToString(inftImportList));

            //Redis实现类内容替换
            Map<String,String> implReplaceMap = new HashMap<>();
            implReplaceMap.put("${packageName}",GenProperties.redisPackageOutPath.concat(".impl"));
            implReplaceMap.put("${entityName}",GenProperties.entityName);
            implReplaceMap.put("${entityObj}",GenProperties.objName);
            implReplaceMap.put("${entityNameUpperCase}",GenProperties.objName.toUpperCase());
            //导入列表请在最后设置
            implReplaceMap.put("${importList}",GenCommon.changeImportSetToString(implImportList));


            //创建接口文件
            GenCommon.createFile(true,GenProperties.inftRedisFileName,GenProperties.redisPackageOutPath.concat(".inft"),GenCommon.replaceTemplateContent("InftRedisTemplate",inftReplaceMap));
            //创建实现文件
            GenCommon.createFile(true,GenProperties.implRedisFileName,GenProperties.redisPackageOutPath.concat(".impl"),GenCommon.replaceTemplateContent("ImplRedisTemplate",implReplaceMap));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
