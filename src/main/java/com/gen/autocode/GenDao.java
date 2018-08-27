package com.gen.autocode;

import com.gen.autocode.GenProp.GenCommon;
import com.gen.autocode.GenProp.GenProperties;

import java.util.*;
import java.util.stream.Collectors;

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
            //导入列表
            Set<String> inftImportList = new HashSet<>();
            inftImportList.add(GenProperties.entityFullPath+";");
            //接口Dao替换内容
            Map<String, String> inftReplaceMap = GenCommon.createReplaceMap();
            inftReplaceMap.put("${packageName}",GenProperties.daoPackageOutPath.concat(".inft"));
            inftReplaceMap.put("${importList}",GenCommon.changeImportSetToString(inftImportList));

            //创建接口文件
            GenCommon.createFile(true,GenProperties.inftDaoFileName,GenProperties.daoPackageOutPath.concat(".inft"),GenCommon.replaceTemplateContent("InftDaoTemplate",inftReplaceMap));

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
