package com.gen.autocode.engine;

import com.gen.autocode.common.GenCommon;
import com.gen.autocode.common.GenProperties;

/**
 * 自动生成service
 * @author gen
 */
public class GenService {

    /**
     * 构造函数
     */
    public GenService() {
        try{
            //创建接口文件
            GenCommon.createFile(true,
                    GenProperties.inftServiceFileName,
                    GenProperties.servicePackageOutPath.concat(".inft"),
                    GenCommon.replaceTemplateContent("InftServiceTemplate",GenCommon.createReplaceMap()));
            //创建实现文件
            GenCommon.createFile(true,
                    GenProperties.implServiceFileName,
                    GenProperties.servicePackageOutPath.concat(".impl"),
                    GenCommon.replaceTemplateContent("ImplServiceTemplate",GenCommon.createReplaceMap()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
