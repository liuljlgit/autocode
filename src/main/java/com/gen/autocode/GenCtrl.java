package com.gen.autocode;


import com.gen.autocode.GenProp.GenCommon;
import com.gen.autocode.GenProp.GenProperties;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 自动生成controller
 * @author gen
 */
public class GenCtrl {

    /**
     * 构造函数
     */
    public GenCtrl() {
        //导入列表
        Set<String> importList = new HashSet<>();

        //接口Dao替换内容
        Map<String, String> replaceMap = GenCommon.createReplaceMap();
        replaceMap.put("${importList}",GenCommon.changeImportSetToString(importList));

        //创建接口文件
        GenCommon.createFile(true,GenProperties.ctrlName,GenProperties.controllerPackageOutPath,GenCommon.replaceTemplateContent("CtrlTemplate",replaceMap));
    }

}
