package com.gen.autocode.dto;

import com.gen.autocode.common.GenCommon;
import com.gen.autocode.common.GenProperties;

public class GenDto {

    public static void main(String[] args){

        DtoInfo.dtoName = "aaa";
        DtoInfo.dtoDesc = "bbb";
        DtoInfo.dtoPackageOutPath = "com.gen.autocodecall.test.dto";
        GenProperties.templatePath = "template/DtoTemplate";
        DtoInfo.entityInfos = new String[]{
                "String,aa,说明",
                "String,aa,说明",
                "String,aa,说明",
                "String,aa,说明",
                "String,aa,说明",
                "String,aa,说明",
                "String,aa,说明",
                "String,aa,说明",
                "String,aa,说明",
        };

        //创建接口文件
        GenCommon.createFile(true,
                DtoInfo.dtoName,
                DtoInfo.dtoPackageOutPath,
                GenCommon.replaceTemplateContent("DtoTemplate",GenCommon.createReplaceMap()));
    }
}
