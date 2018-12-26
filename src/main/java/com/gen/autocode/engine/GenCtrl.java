package com.gen.autocode.engine;


import com.gen.autocode.common.GenCommon;
import com.gen.autocode.common.GenProperties;

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
