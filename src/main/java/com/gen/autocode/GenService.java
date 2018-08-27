package com.gen.autocode;

import com.gen.autocode.GenProp.GenCommon;
import com.gen.autocode.GenProp.GenProperties;
import com.gen.autocode.util.HumpUtils;

import java.util.*;
import java.util.stream.Collectors;

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
            //导入列表
            Set<String> inftImportList = new HashSet<>();
            Set<String> implImportList = new HashSet<>();
            implImportList.add(GenProperties.inftServiceFullPath+";");
            implImportList.add(GenProperties.inftDaoFullPath+";");
            implImportList.add(GenProperties.entityFullPath+";");
            implImportList.add(GenProperties.inftRedisFullPath+";");
            inftImportList.add(GenProperties.entityFullPath+";");

            //接口Dao替换内容
            Map<String, String> inftReplaceMap = GenCommon.createReplaceMap();
            inftReplaceMap.put("${packageName}",GenProperties.servicePackageOutPath.concat(".inft"));
            inftReplaceMap.put("${importList}",GenCommon.changeImportSetToString(inftImportList));

            //实现Dao替换内容
            Map<String, String> implReplaceMap = GenCommon.createReplaceMap();
            implReplaceMap.put("${packageName}",GenProperties.servicePackageOutPath.concat(".impl"));
            implReplaceMap.put("${importList}",GenCommon.changeImportSetToString(implImportList));

            //创建接口文件
            GenCommon.createFile(true,GenProperties.inftServiceFileName,GenProperties.servicePackageOutPath.concat(".inft"),GenCommon.replaceTemplateContent("InftServiceTemplate",inftReplaceMap));
            //创建实现文件
            GenCommon.createFile(true,GenProperties.implServiceFileName,GenProperties.servicePackageOutPath.concat(".impl"),GenCommon.replaceTemplateContent("ImplServiceTemplate",implReplaceMap));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
