package com.gen.autocode;

import com.gen.autocode.GenProp.GenCommon;
import com.gen.autocode.GenProp.GenProperties;

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
                    GenProperties.daoPackageOutPath.concat(".inft"),
                    GenCommon.replaceTemplateContent("InftDaoTemplate",GenCommon.createReplaceMap()));

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
