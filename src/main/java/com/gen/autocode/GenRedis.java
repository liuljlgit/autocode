package com.gen.autocode;

import com.gen.autocode.GenProp.GenCommon;
import com.gen.autocode.GenProp.GenProperties;

/**
 * 自动生成Redis
 * @author gen
 */
public class GenRedis {

    /**
     * 构造函数
     */
    public GenRedis() {
        try{
            //创建接口文件
            GenCommon.createFile(true,
                    GenProperties.inftRedisFileName,
                    GenProperties.redisPackageOutPath.concat(".inft"),
                    GenCommon.replaceTemplateContent("InftRedisTemplate",GenCommon.createReplaceMap()));
            //创建实现文件
            GenCommon.createFile(true,
                    GenProperties.implRedisFileName,
                    GenProperties.redisPackageOutPath.concat(".impl"),
                    GenCommon.replaceTemplateContent("ImplRedisTemplate",GenCommon.createReplaceMap()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
