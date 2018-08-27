package com.gen.autocode;


import com.gen.autocode.GenProp.GenCommon;
import com.gen.autocode.GenProp.GenProperties;
import com.gen.autocode.util.HumpUtils;

import java.util.*;
import java.util.stream.Collectors;

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
        importList.add(GenProperties.inftServiceFullPath+";");
        importList.add(GenProperties.entityFullPath+";");
        importList.add(GenProperties.respPackageOutPath+"."+GenProperties.entityName+"Resp;");

        //接口Dao替换内容
        Map<String, String> replaceMap = GenCommon.createReplaceMap();
        replaceMap.put("${packageName}",GenProperties.controllerPackageOutPath);
        replaceMap.put("${importList}",GenCommon.changeImportSetToString(importList));

        //创建接口文件
        GenCommon.createFile(true,GenProperties.ctrlName,GenProperties.controllerPackageOutPath,GenCommon.replaceTemplateContent("CtrlTemplate",replaceMap));
    }

}
