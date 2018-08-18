package com.lijun.autocode;

import com.lijun.autocode.GenProp.GenCommon;
import com.lijun.autocode.GenProp.GenProperties;
import com.lijun.autocode.util.HumpUtils;

import java.util.*;
import java.util.stream.Collectors;

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
            implImportList.add(GenProperties.inftDaoFullPath+";");
            implImportList.add(GenProperties.autocodePath+".util.CommonUtil;");
            inftImportList.add(GenProperties.entityFullPath+";");

            //Redis接口内容替换
            Map<String,String> inftReplaceMap = GenCommon.createReplaceMap();
            inftReplaceMap.put("${packageName}",GenProperties.redisPackageOutPath.concat(".inft"));
            inftReplaceMap.put("${importList}",GenCommon.changeImportSetToString(inftImportList));

            //Redis实现类内容替换
            Map<String,String> implReplaceMap = GenCommon.createReplaceMap();
            implReplaceMap.put("${packageName}",GenProperties.redisPackageOutPath.concat(".impl"));
            implReplaceMap.put("${entityNameUpperCase}",GenProperties.objName.toUpperCase());
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
