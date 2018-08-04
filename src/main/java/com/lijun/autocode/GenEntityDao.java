package com.lijun.autocode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 自动生成dao
 * @author lijun
 */
public class GenEntityDao {

    /**
     * 构造函数
     */
    public GenEntityDao() {
        try{
            //导入列表
            Set<String> inftImportList = new HashSet<>();
            inftImportList.add("org.springframework.stereotype.Repository;");
            inftImportList.add(GenProperties.entityFullPath+";");
            //接口Dao替换内容
            Map<String,String> inftReplaceMap = new HashMap<>();
            inftReplaceMap.put("${packageName}",GenProperties.daoPackageOutPath.concat(".inft"));
            inftReplaceMap.put("${classNote}",GenCommon.createFileNote(GenProperties.inftDaoFileName));
            inftReplaceMap.put("${className}",GenProperties.inftDaoFileName);
            inftReplaceMap.put("${entityName}",GenProperties.entityName);
            inftReplaceMap.put("${entityObj}",GenProperties.objName);
            inftReplaceMap.put("${loadByKeyParams1}",createIdList());
            //导入列表请在最后设置
            inftReplaceMap.put("${importList}",GenCommon.changeImportSetToString(inftImportList));
            //创建接口文件
            GenCommon.createFile(true,GenProperties.inftDaoFileName,GenProperties.daoPackageOutPath.concat(".inft"),GenCommon.replaceTemplateContent("InftDaoTemplate",inftReplaceMap));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 创建id列表
     * @return
     */
    public String createIdList(){
        StringBuffer sb = new StringBuffer("");
        List<String> priList = GenProperties.tableColumInfoList.stream().filter(e -> e.getTableColumKey().equals("PRI")).map(e -> {
            return "@Param(\""+e.getEntityPropName()+"\") "+e.getEntityType()+" " +e.getEntityPropName();
        }).collect(Collectors.toList());
        return String.join(",",priList);
    }
}
