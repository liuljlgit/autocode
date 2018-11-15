package com.gen.autocode;


import com.gen.autocode.GenProp.GenCommon;
import com.gen.autocode.GenProp.GenProperties;

/**
 * 自动生成controller
 * @author gen
 */
public class GenCtrl {

    /**
     * 构造函数
     */
    public GenCtrl() {
        //创建接口文件
        GenCommon.createFile(true,
                GenProperties.ctrlName,
                GenProperties.controllerPackageOutPath,
                GenCommon.replaceTemplateContent("CtrlTemplate",GenCommon.createReplaceMap()));
    }

}
