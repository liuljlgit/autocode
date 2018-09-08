package com.gen.autocode;

import com.gen.autocode.GenProp.GenCommon;
import com.gen.autocode.GenProp.GenProperties;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 自动生成resp
 * @author gen
 */
public class GenExample {

    /**
     * 构造函数
     */
    public GenExample() {
        try{
            //导入列表
            Set<String> importList = new HashSet<>();
            //Example模板内容替换
            Map<String, String> respReplaceMap = GenCommon.createReplaceMap();
            respReplaceMap.put("${packageName}",GenProperties.entityPackageOutPath);
            //导入列表请在最后设置
            respReplaceMap.put("${importList}", GenCommon.changeImportSetToString(importList));
            //创建接口文件
            GenCommon.createFile(true,GenProperties.entityName.concat("Example"),GenProperties.entityPackageOutPath,GenCommon.replaceTemplateContent("EntityExampleTemplate",respReplaceMap));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
