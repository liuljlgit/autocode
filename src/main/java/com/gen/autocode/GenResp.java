package com.gen.autocode;

import com.gen.autocode.GenProp.GenCommon;
import com.gen.autocode.GenProp.GenProperties;
import com.gen.autocode.entity.TableColumInfo;

import java.util.*;

/**
 * 自动生成resp
 * @author gen
 */
public class GenResp {

    /**
     * 构造函数
     */
    public GenResp() {
        try{
            //设置模板替换内容
            Map<String, String> replaceMap = GenCommon.createReplaceMap();
            replaceMap.put("${returnProp}",createReturnProp());
            //创建接口文件
            GenCommon.createFile(true,
                    GenProperties.respFileName,
                    GenProperties.respPackageOutPath,
                    GenCommon.replaceTemplateContent("RespTemplate",replaceMap));
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
