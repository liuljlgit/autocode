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
            inftImportList.add(GenProperties.entityFullPath+";");

            //Redis接口内容替换
            Map<String,String> inftReplaceMap = new HashMap<>();
            inftReplaceMap.put("${packageName}",GenProperties.redisPackageOutPath.concat(".inft"));
            inftReplaceMap.put("${entityName}",GenProperties.entityName);
            inftReplaceMap.put("${entityObj}",GenProperties.objName);
            inftReplaceMap.put("${paramIds}",createParamIds());
            //导入列表请在最后设置
            inftReplaceMap.put("${importList}",GenCommon.changeImportSetToString(inftImportList));

            //Redis实现类内容替换
            Map<String,String> implReplaceMap = new HashMap<>();
            implReplaceMap.put("${packageName}",GenProperties.redisPackageOutPath.concat(".impl"));
            implReplaceMap.put("${entityName}",GenProperties.entityName);
            implReplaceMap.put("${entityObj}",GenProperties.objName);
            implReplaceMap.put("${entityNameUpperCase}",GenProperties.objName.toUpperCase());
            implReplaceMap.put("${paramIds}",createParamIds());
            implReplaceMap.put("${concatIds}",createConcatIds());
            implReplaceMap.put("${objGetIds}",createObjGetIds());
            implReplaceMap.put("${Ids}",String.join(",",GenCommon.getEntityIdList()));
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

    private String createParamIds() {
        //colName,colType
        Map<String, String> entityIdMap = GenCommon.getEntityIdMap();
        List<String> idList = new ArrayList<>();
        for(Map.Entry<String, String> data : entityIdMap.entrySet()){
            String colName = data.getKey();
            String colType = data.getValue();
            String str = "final "+colType+" "+colName;
            idList.add(str);
        }
        return String.join(",",idList);
    }

    private String createConcatIds() {
        List<String> collect = GenCommon.getEntityIdList().stream().map(e -> ".concat(String.valueOf(" + e + "))").collect(Collectors.toList());
        return String.join("",collect);
    }

    private String createObjGetIds() {
        List<String> collect = GenCommon.getEntityIdList().stream().map(e -> GenProperties.objName+".get"+HumpUtils.toUpperCaseFirstOne(e)+"()").collect(Collectors.toList());
        return String.join(",",collect);
    }

}
