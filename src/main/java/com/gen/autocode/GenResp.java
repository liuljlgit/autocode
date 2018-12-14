package com.gen.autocode;

import com.cloud.common.utils.HumpUtil;
import com.gen.autocode.GenProp.GenCommon;
import com.gen.autocode.GenProp.GenProperties;
import com.gen.autocode.entity.TableColumInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            replaceMap.put("${dateDetaultFormat}",createDateDetaultFormat());
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

    public String createDateDetaultFormat(){
        StringBuffer sb = new StringBuffer();
        List<TableColumInfo> tableColumInfoList = GenProperties.tableColumInfoList;
        for(int i=0;i<tableColumInfoList.size();i++){
            if(!tableColumInfoList.get(i).getEntityType().equals("Date")){
                continue;
            }
            String propName = HumpUtil.toUpperCaseFirstOne(tableColumInfoList.get(i).getEntityPropName());
            sb.append("    @Override\n" +
                    "    @JSONField(format = DateUtil.DEFAUL_FORMAT)\n" +
                    "    public Date get"+propName+"() {\n" +
                    "        return super.get"+propName+"();\n" +
                    "    }\n\n");
        }
        return sb.toString();
    }
}
