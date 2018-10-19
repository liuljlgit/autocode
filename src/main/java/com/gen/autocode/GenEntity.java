package com.gen.autocode;

import com.cloud.common.utils.HumpUtil;
import com.gen.autocode.GenProp.GenCommon;
import com.gen.autocode.GenProp.GenProperties;
import com.gen.autocode.entity.TableColumInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GenEntity {

    public GenEntity() {
        try{
            /*获取导入列表*/
            Set<String> entityFullTypeSet = GenProperties.tableColumInfoList.stream().map(e->e.getEntityFullType()).collect(Collectors.toSet());

            //生成属性列表和getter和setter函数
            StringBuffer entityColumList = new StringBuffer("");//属性定义
            StringBuffer entityColumPropList = new StringBuffer("");//常量定义
            StringBuffer entityGetSetList = new StringBuffer("");//get和set方法定义
            List<TableColumInfo> tableColumInfoList = GenProperties.tableColumInfoList;
            for(int i=0;i<tableColumInfoList.size();i++){
                String tableColumName = tableColumInfoList.get(i).getTableColumName();
                String tableColumComment = tableColumInfoList.get(i).getTableColumComment();
                String entityPropName = tableColumInfoList.get(i).getEntityPropName();
                String entityType = tableColumInfoList.get(i).getEntityType();
                //设置属性注释和类型、名称
                entityColumList.append(createField(tableColumComment,entityType,"",entityPropName));
                if(entityType.equals("Date")){
                    entityColumList.append(createField(tableColumComment,"transient "+entityType,"Start",entityPropName));
                    entityColumList.append(createField(tableColumComment,"transient "+entityType,"End",entityPropName));
                }else if(entityType.equals("String")){
                    entityColumList.append(createField(tableColumComment,"transient Boolean","Equal = Boolean.FALSE",entityPropName));
                }
                //设置属性常量
                entityColumPropList.append("\tpublic static final transient String PROP_"+tableColumName.toUpperCase()+" = \""+entityPropName+"\";\n");
                //设置Table表字段常量
                entityColumPropList.append("\tpublic static final transient String TABLE_"+tableColumName.toUpperCase()+" = \""+tableColumName+"\";\n");
                //设置getter和setter函数
                entityGetSetList.append(createGetSetFun(entityType,"",entityPropName));
                if(entityType.equals("Date")){
                    entityGetSetList.append(createGetSetFun(entityType,"Start",entityPropName));
                    entityGetSetList.append(createGetSetFun(entityType,"End",entityPropName));
                }else if(entityType.equals("String")){
                    entityGetSetList.append(createGetSetFun("Boolean","Equal",entityPropName));
                }
            }

            //设置order by && in && not in
            entityColumList.append(createField("order by","transient String","","orderByClause"));
            entityColumList.append(createField("and xxx in...列表","transient List<IntervalEntity>","","inSql"));
            entityColumList.append(createField("and xxx not in 列表","transient List<IntervalEntity>","","notInSql"));
            entityGetSetList.append(createGetSetFun("String","","orderByClause"));
            entityGetSetList.append(createGetSetFun("List<IntervalEntity>","","inSql"));
            entityGetSetList.append(createGetSetFun("List<IntervalEntity>","","notInSql"));


            //模板文件内容替换
            Map<String, String> replaceMap = GenCommon.createReplaceMap();
            replaceMap.put("${packageName}",GenProperties.entityPackageOutPath);
            replaceMap.put("${importList}",GenCommon.changeImportSetToString(entityFullTypeSet));
            replaceMap.put("${entityColumList}",entityColumList.toString());
            replaceMap.put("${entityGetSetList}",entityGetSetList.toString());
            replaceMap.put("${entityColumPropName}",entityColumPropList.toString());
            //创建文件
            GenCommon.createFile(true,GenProperties.entityName,GenProperties.entityPackageOutPath,GenCommon.replaceTemplateContent("EntityTemplate",replaceMap));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 创建get和set函数
     * @param entityType:实体类型
     * @param suffix:后缀
     * @param entityPropName:实体名称
     * @return
     */
    public String createGetSetFun(String entityType,String suffix,String entityPropName){
        StringBuffer sb = new StringBuffer("");
        sb.append("\tpublic "+entityType+" "+"get"+ HumpUtil.toUpperCaseFirstOne(entityPropName)+suffix+"() { return "+entityPropName+suffix+"; }\n\n");//设置get函数
        sb.append("\tpublic void set"+ HumpUtil.toUpperCaseFirstOne(entityPropName)+suffix+"("+entityType+" "+entityPropName+suffix+") { this."+entityPropName+suffix+" = "+entityPropName+suffix+"; }\n\n");//设置set函数
        return sb.toString();
    }

    /**
     * 创建Field域
     * @param tableColumComment:备注
     * @param entityType:实体类型
     * @param suffix:后缀
     * @param entityPropName:实体名称
     * @return
     */
    public String createField(String tableColumComment,String entityType,String suffix,String entityPropName){
        StringBuffer sb = new StringBuffer("");
        sb.append(GenCommon.createFieldNote(tableColumComment)+"\n");//备注
        sb.append("\tprivate "+entityType+" "+entityPropName+suffix+";\n\n");//设置属性
        return sb.toString();
    }

}
