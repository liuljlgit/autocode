package com.lijun.autocode;

import com.lijun.autocode.GenProp.GenCommon;
import com.lijun.autocode.GenProp.GenProperties;
import com.lijun.autocode.entity.TableColumInfo;

import java.util.*;

/**
 * 自动生成resp
 * @author lijun
 */
public class GenResp {

    /**
     * 构造函数
     */
    public GenResp() {
        try{
            //导入列表
            Set<String> importList = new HashSet<>();
            importList.add(GenProperties.commonUtilPath);
            importList.add(GenProperties.entityPackageOutPath+"."+GenProperties.entityName+";");
            //resp模板内容替换
            Map<String, String> respReplaceMap = GenCommon.createReplaceMap();
            respReplaceMap.put("${packageName}",GenProperties.respPackageOutPath);
            respReplaceMap.put("${returnProp}",createReturnProp());
            //导入列表请在最后设置
            respReplaceMap.put("${importList}", GenCommon.changeImportSetToString(importList));
            //创建接口文件
            GenCommon.createFile(true,GenProperties.entityName.concat("Resp"),GenProperties.respPackageOutPath,GenCommon.replaceTemplateContent("RespTemplate",respReplaceMap));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String createReturnProp(){
        List<String> propList = new ArrayList<>();
        List<TableColumInfo> tableColumInfoList = GenProperties.tableColumInfoList;
        for(int i=0;i<tableColumInfoList.size();i++){
            String propName = GenProperties.entityName+".PROP_"+tableColumInfoList.get(i).getTableColumName().toUpperCase();
            propList.add(propName);
        }
        return String.join(",\n\t",propList);
    }
}
