package com.lijun.autocode;

import com.lijun.autocode.GenProp.GenCommon;
import com.lijun.autocode.GenProp.GenProperties;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 自动生成resp
 * @author lijun
 */
public class GenResp {

    /**
     * 构造函数
     */
    public GenResp() {
        try{
            //导入列表
            Set<String> importList = new HashSet<>();
            importList.add(GenProperties.commonUtilPath);
            importList.add(GenProperties.entityPackageOutPath+"."+GenProperties.entityName+";");
            //resp模板内容替换
            Map<String,String> respReplaceMap = new HashMap<>();
            respReplaceMap.put("${packageName}",GenProperties.respPackageOutPath);
            respReplaceMap.put("${entityName}",GenProperties.entityName);
            respReplaceMap.put("${entityObj}",GenProperties.objName);
            //导入列表请在最后设置
            respReplaceMap.put("${importList}", GenCommon.changeImportSetToString(importList));
            //创建接口文件
            GenCommon.createFile(true,GenProperties.entityName.concat("Resp"),GenProperties.respPackageOutPath,GenCommon.replaceTemplateContent("RespTemplate",respReplaceMap));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
