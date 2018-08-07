package com.lijun.autocode;


import com.lijun.autocode.GenProp.GenCommon;
import com.lijun.autocode.GenProp.GenProperties;
import com.lijun.autocode.util.HumpUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 自动生成controller
 * @author lijun
 */
public class GenCtrl {

    /**
     * 构造函数
     */
    public GenCtrl() {
        //导入列表
        Set<String> importList = new HashSet<>();
        importList.add(GenProperties.inftServiceFullPath+";");
        importList.add(GenProperties.entityFullPath+";");

        //接口Dao替换内容
        Map<String,String> replaceMap = new HashMap<>();
        replaceMap.put("${packageName}",GenProperties.controllerPackageOutPath);
        replaceMap.put("${classNote}", GenCommon.createFileNote(GenProperties.ctrlName));
        replaceMap.put("${className}",GenProperties.ctrlName);
        replaceMap.put("${inftServiceName}",GenProperties.inftServiceFileName);
        replaceMap.put("${implServiceName}", HumpUtils.toLowerCaseFirstOne(GenProperties.implServiceFileName));
        replaceMap.put("${entityName}",GenProperties.entityName);
        replaceMap.put("${entityObj}",GenProperties.objName);
        replaceMap.put("${urlIdList}",String.join("/",createIdUrlList()));
        replaceMap.put("${PathVariableIdList}",String.join(",",createPathVarList()));
        replaceMap.put("${IdNotNull}",String.join(" || ",createIdNotNull()));
        replaceMap.put("${idParamsList}",String.join(",",GenCommon.getEntityIdList()));

        //导入列表请在最后设置
        replaceMap.put("${importList}",GenCommon.changeImportSetToString(importList));

        //创建接口文件
        GenCommon.createFile(true,GenProperties.ctrlName,GenProperties.controllerPackageOutPath,GenCommon.replaceTemplateContent("CtrlTemplate",replaceMap));
    }

    /**
     * 创建id参数的url列表
     */
    public List<String> createIdUrlList(){
        return GenCommon.getEntityIdList().stream().map(e->{
            return "{"+e+"}";
        }).collect(Collectors.toList());
    }

    /**
     * 创建id参数变量列表
     */
    public List<String> createPathVarList(){
        return GenCommon.getEntityIdMap().entrySet().stream().map(e->{
            return "@PathVariable(value=\""+e.getKey()+"\") "+e.getValue()+" "+e.getKey()+"";
        }).collect(Collectors.toList());
    }

    /**
     * 判断主键非空
     */
    public List<String> createIdNotNull(){
        return GenCommon.getEntityIdList().stream().map(e->{
            return "Objects.isNull("+e+")";
        }).collect(Collectors.toList());
    }

}
