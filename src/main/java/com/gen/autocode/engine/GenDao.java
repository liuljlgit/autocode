package com.gen.autocode.engine;

import com.gen.autocode.common.GenCommon;
import com.gen.autocode.common.GenProperties;

/**
 * 自动生成dao
 * @author gen
 */
public class GenDao {

    /**
     * 构造函数
     */
    public GenDao() {
        try{
            //创建接口文件
            GenCommon.createFile(true,
                    GenProperties.inftDaoFileName,
                    GenProperties.daoPackageOutPath,
                    GenCommon.replaceTemplateContent("InftDaoTemplate",GenCommon.createReplaceMap()));

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
