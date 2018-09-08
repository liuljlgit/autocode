package com.gen.autocode;

import com.cloud.common.utils.HumpUtil;
import com.gen.autocode.GenProp.GenCommon;
import com.gen.autocode.GenProp.GenProperties;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自动生成resp
 * @author gen
 */
public class GenExample {

    /**
     * 构造函数
     */
    public GenExample() {
        try{
            //导入列表
            Set<String> importList = new HashSet<>();
            //Example模板内容替换
            Map<String, String> respReplaceMap = GenCommon.createReplaceMap();
            respReplaceMap.put("${packageName}",GenProperties.entityPackageOutPath);
            respReplaceMap.put("${exampleSql}",createExampleSql());
            //导入列表请在最后设置
            respReplaceMap.put("${importList}", GenCommon.changeImportSetToString(importList));
            //创建接口文件
            GenCommon.createFile(true,GenProperties.entityName.concat("Example"),GenProperties.entityPackageOutPath,GenCommon.replaceTemplateContent("EntityExampleTemplate",respReplaceMap));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /* 生成动态example sql列表 */
    public String createExampleSql(){
        StringBuffer sb = new StringBuffer("");
        Map<String, String> entityColNameTypeList = GenCommon.getEntityColNameTypeList();
        List<String> tableColNameList = GenCommon.getTableColNameList();
        for(int i=0;i<tableColNameList.size();i++){
            String tableName = tableColNameList.get(i);
            String javaName = HumpUtil.convertToJava(tableName);
            String javaType = entityColNameTypeList.get(javaName);
            String tmp = "\t\tpublic Criteria "+javaName+"IsNull(String prefix) {\n" +
                    "            addCriterion(prefix+\" "+tableName+" is null\");\n" +
                    "            return (Criteria) this;\n" +
                    "        }\n" +
                    "\n" +
                    "        public Criteria "+javaName+"IsNotNull(String prefix) {\n" +
                    "            addCriterion(prefix+\" "+tableName+" is not null\");\n" +
                    "            return (Criteria) this;\n" +
                    "        }\n" +
                    "\n" +
                    "        public Criteria "+javaName+"EqualTo(String prefix,"+javaType+" value) {\n" +
                    "            addCriterion(prefix+\" "+tableName+" =\", value, \""+javaName+"\");\n" +
                    "            return (Criteria) this;\n" +
                    "        }\n" +
                    "\n" +
                    "        public Criteria "+javaName+"NotEqualTo(String prefix,"+javaType+" value) {\n" +
                    "            addCriterion(prefix+\" "+tableName+" <>\", value, \""+javaName+"\");\n" +
                    "            return (Criteria) this;\n" +
                    "        }\n" +
                    "\n" +
                    "        public Criteria "+javaName+"GreaterThan(String prefix,"+javaType+" value) {\n" +
                    "            addCriterion(prefix+\" "+tableName+" >\", value, \""+javaName+"\");\n" +
                    "            return (Criteria) this;\n" +
                    "        }\n" +
                    "\n" +
                    "        public Criteria "+javaName+"GreaterThanOrEqualTo(String prefix,"+javaType+" value) {\n" +
                    "            addCriterion(prefix+\" "+tableName+" >=\", value, \""+javaName+"\");\n" +
                    "            return (Criteria) this;\n" +
                    "        }\n" +
                    "        \n" +
                    "        public Criteria "+javaName+"LessThan(String prefix,"+javaType+" value) {\n" +
                    "            addCriterion(prefix+\" "+tableName+" <\", value, \""+javaName+"\");\n" +
                    "            return (Criteria) this;\n" +
                    "        }\n" +
                    "\n" +
                    "        public Criteria "+javaName+"LessThanOrEqualTo(String prefix,"+javaType+" value) {\n" +
                    "            addCriterion(prefix+\" "+tableName+" <=\", value, \""+javaName+"\");\n" +
                    "            return (Criteria) this;\n" +
                    "        }\n" +
                    "\n" +
                    "        public Criteria "+javaName+"Like(String prefix,String value) {\n" +
                    "            addCriterion(prefix+\" "+tableName+" like\", value, \""+javaName+"\");\n" +
                    "            return (Criteria) this;\n" +
                    "        }\n" +
                    "\n" +
                    "        public Criteria "+javaName+"NotLike(String prefix,String value) {\n" +
                    "            addCriterion(prefix+\" "+tableName+" not like\", value, \""+javaName+"\");\n" +
                    "            return (Criteria) this;\n" +
                    "        }\n" +
                    "\n" +
                    "        public Criteria "+javaName+"In(String prefix,List<"+javaType+"> values) {\n" +
                    "            addCriterion(prefix+\" "+tableName+" in\", values, \""+javaName+"\");\n" +
                    "            return (Criteria) this;\n" +
                    "        }\n" +
                    "\n" +
                    "        public Criteria "+javaName+"NotIn(String prefix,List<"+javaType+"> values) {\n" +
                    "            addCriterion(prefix+\" "+tableName+" not in\", values, \""+javaName+"\");\n" +
                    "            return (Criteria) this;\n" +
                    "        }\n" +
                    "\n" +
                    "        public Criteria "+javaName+"Between(String prefix,"+javaType+" value1, "+javaType+" value2) {\n" +
                    "            addCriterion(prefix+\" "+tableName+" between\", value1, value2, \""+javaName+"\");\n" +
                    "            return (Criteria) this;\n" +
                    "        }\n" +
                    "\n" +
                    "        public Criteria "+javaName+"NotBetween(String prefix,"+javaType+" value1, "+javaType+" value2) {\n" +
                    "            addCriterion(prefix+\" "+tableName+" not between\", value1, value2, \""+javaName+"\");\n" +
                    "            return (Criteria) this;\n" +
                    "        }\n\n";
            sb.append(tmp);
        }
        return sb.toString();
    }
}
