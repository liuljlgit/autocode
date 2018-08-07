package com.lijun.autocode;

import com.lijun.autocode.GenProp.GenCommon;
import com.lijun.autocode.GenProp.GenProperties;
import com.lijun.autocode.util.HumpUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 自动生成service
 * @author lijun
 */
public class GenService {

    /**
     * 构造函数
     */
    public GenService() {
        try{
            //导入列表
            Set<String> inftImportList = new HashSet<>();
            Set<String> implImportList = new HashSet<>();
            implImportList.add(GenProperties.inftServiceFullPath+";");
            implImportList.add(GenProperties.inftDaoFullPath+";");
            implImportList.add(GenProperties.entityFullPath+";");
            inftImportList.add(GenProperties.entityFullPath+";");

            //接口Dao替换内容
            Map<String,String> inftReplaceMap = new HashMap<>();
            inftReplaceMap.put("${packageName}",GenProperties.servicePackageOutPath.concat(".inft"));
            inftReplaceMap.put("${classNote}", GenCommon.createFileNote(GenProperties.inftServiceFileName));
            inftReplaceMap.put("${className}",GenProperties.inftServiceFileName);
            inftReplaceMap.put("${entityName}",GenProperties.entityName);
            inftReplaceMap.put("${entityObj}",GenProperties.objName);
            inftReplaceMap.put("${loadByKeyParams1}",createIdList(1));
            //导入列表请在最后设置
            inftReplaceMap.put("${importList}",GenCommon.changeImportSetToString(inftImportList));

            //实现Dao替换内容
            Map<String,String> implReplaceMap = new HashMap<>();
            implReplaceMap.put("${packageName}",GenProperties.servicePackageOutPath.concat(".impl"));
            implReplaceMap.put("${classNote}",GenCommon.createFileNote(GenProperties.implServiceFileName));
            implReplaceMap.put("${className}",GenProperties.implServiceFileName);
            implReplaceMap.put("${inftServiceName}",GenProperties.inftServiceFileName);
            implReplaceMap.put("${serviceName}", HumpUtils.toLowerCaseFirstOne(GenProperties.implServiceFileName));
            implReplaceMap.put("${inftDaoName}", GenProperties.inftDaoFileName);
            implReplaceMap.put("${implDaoName}", HumpUtils.toLowerCaseFirstOne(GenProperties.implDaoFileName));
            implReplaceMap.put("${entityName}",GenProperties.entityName);
            implReplaceMap.put("${entityObj}",GenProperties.objName);
            implReplaceMap.put("${loadByKeyParams1}",createIdList(1));
            implReplaceMap.put("${loadByKeyParams2}",createIdList(2));
            implReplaceMap.put("${KeyNotNull}",createIdList(3));
            implReplaceMap.put("${ObjKeyNotNull}",String.join(" && ",createIdNotNull()));
            //导入列表请在最后设置
            implReplaceMap.put("${importList}",GenCommon.changeImportSetToString(implImportList));
            //创建接口文件
            GenCommon.createFile(true,GenProperties.inftServiceFileName,GenProperties.servicePackageOutPath.concat(".inft"),GenCommon.replaceTemplateContent("InftServiceTemplate",inftReplaceMap));
            //创建实现文件
            GenCommon.createFile(true,GenProperties.implServiceFileName,GenProperties.servicePackageOutPath.concat(".impl"),GenCommon.replaceTemplateContent("ImplServiceTemplate",implReplaceMap));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 创建id列表
     * @return
     */
    public String createIdList(Integer type){
        StringBuffer sb = new StringBuffer("");
        List<String> priList = null;
        if(type == 1){
            priList = GenProperties.tableColumInfoList.stream().filter(e -> e.getTableColumKey().equals("PRI")).map(e -> {
                 return e.getEntityType()+" " +e.getEntityPropName();
            }).collect(Collectors.toList());
        }else if(type == 2){
            priList = GenProperties.tableColumInfoList.stream().filter(e -> e.getTableColumKey().equals("PRI")).map(e -> e.getEntityPropName()).collect(Collectors.toList());
        }else if(type == 3){
            return String.join(" ||　",GenCommon.getEntityIdList().stream().map(e->{
                return "Objects.isNull("+e+")";
            }).collect(Collectors.toList()));
        }
        return String.join(",",priList);
    }

    /**
     * 判断主键非空
     */
    public List<String> createIdNotNull(){
        return GenCommon.getEntityIdList().stream().map(e->{
            return "Objects.isNull("+GenProperties.objName+".get"+HumpUtils.toUpperCaseFirstOne(e)+"())";
        }).collect(Collectors.toList());
    }

}
