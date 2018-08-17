package com.lijun.autocode;

import com.lijun.autocode.GenProp.GenCommon;
import com.lijun.autocode.GenProp.GenProperties;
import com.lijun.autocode.entity.TableColumInfo;
import com.lijun.autocode.util.HumpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 自动生成xml文件
 * @author lijun
 */
public class GenXml {
    /**
     * 构造函数
     */
    public GenXml(){
        try{
            //替换内容
            Map<String,String> replaceMap = GenCommon.createReplaceMap();
            replaceMap.put("${inftDaoFullPath}", GenProperties.inftDaoFullPath);
            replaceMap.put("${entityFileFullPath}",GenProperties.entityFullPath);
            replaceMap.put("${resultMapResultList}",createResultList());
            replaceMap.put("${whereSqlList}",createWhereSqlList());
            replaceMap.put("${idList}",createIdList(""));
            replaceMap.put("${tableColumList}",createTableColumList((byte)1));
            replaceMap.put("${entityPropList}",createTableColumList((byte)2));
            replaceMap.put("${batchEntityPropList}",createTableColumList((byte)3));
            replaceMap.put("${setList}",createSetList(""));
            replaceMap.put("${batchSetList}",createSetList("obj."));
            replaceMap.put("${batchIdList}",createIdList("obj."));
            replaceMap.put("${getIdMax}",GenCommon.getTableIdList().get(0));

            //创建文件
            GenCommon.createFile(false,GenProperties.xmlFileName,GenProperties.xmlPackageOutPath,GenCommon.replaceTemplateContent("XmlTemplate",replaceMap));
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 创建resultMap列表
     */
    private String createResultList(){
        StringBuffer sb = new StringBuffer();
        List<TableColumInfo> tableColumInfoList = GenProperties.tableColumInfoList;
        for(int i=0;i<tableColumInfoList.size();i++){
            String jdbcType = tableColumInfoList.get(i).getTableColumType().toUpperCase();
            if(jdbcType.equals("INT")){
                jdbcType = "INTEGER";
            }else if(jdbcType.equals("DATETIME")){
                jdbcType = "DATE";
            }
            String str = "\t\t<result column=\""+tableColumInfoList.get(i).getTableColumName()+"\" jdbcType=\""+ jdbcType+"\" property=\""+tableColumInfoList.get(i).getEntityPropName()+"\" />";
            if(i != tableColumInfoList.size()-1){
                sb.append(str+"\n");
            }else{
                sb.append(str);
            }
        }
        return sb.toString();
    }

    /**
     * 创建where_sql列表
     */
    private String createWhereSqlList(){
        StringBuffer sb = new StringBuffer();
        List<TableColumInfo> tableColumInfoList = GenProperties.tableColumInfoList;
        for(int i=0;i<tableColumInfoList.size();i++){
            String entityType = tableColumInfoList.get(i).getEntityType();
            String tableColumType = tableColumInfoList.get(i).getTableColumType();
            String tableColumName = tableColumInfoList.get(i).getTableColumName();
            String entityPropName = tableColumInfoList.get(i).getEntityPropName();
            String entityPropNameEqual = tableColumInfoList.get(i).getEntityPropName()+"Equal";
            String jdbcType = tableColumType.toUpperCase();
            if(jdbcType.equals("INT")){
                jdbcType = "INTEGER";
            }else if(jdbcType.equals("DATETIME")){
                jdbcType = "DATE";
            }
            String str = "";
            if(entityType.equals("String")){
                str =   "\t    <if test=\""+entityPropName+" != null\">\n" +
                        "\t      <if test=\""+entityPropNameEqual+" != null and "+entityPropNameEqual+"\">\n" +
                        "\t         and "+tableColumName+" = #{"+entityPropName+",jdbcType="+jdbcType+"}\n" +
                        "\t      </if>\n" +
                        "\t      <if test=\""+entityPropNameEqual+" != null and not "+entityPropNameEqual+"\">\n" +
                        "\t         <![CDATA[and "+tableColumName+" like CONCAT('%',#{"+entityPropName+",jdbcType="+jdbcType+"} ,'%')]]>\n" +
                        "\t      </if>\n" +
                        "\t    </if>";
            }else if(entityType.equals("Date")){
                String entityPropNameStart = tableColumInfoList.get(i).getEntityPropName()+"Start";
                String entityPropNameEnd = tableColumInfoList.get(i).getEntityPropName()+"End";
                str =   "\t    <if test=\""+entityPropNameStart+" != null\">\n" +
                        "\t       and "+tableColumName+" &gt;= #{"+entityPropNameStart+",jdbcType="+jdbcType+"}\n" +
                        "\t    </if>\n" +
                        "\t    <if test=\""+entityPropNameEnd+" != null\">\n" +
                        "\t       and #{"+entityPropNameEnd+",jdbcType="+jdbcType+"} &gt;= "+tableColumName+"\n" +
                        "\t    </if>";
            }else if(entityType.equals("Integer") || entityType.equals("Long") || entityType.equals("Byte")){
                String entityPropNameInList = tableColumInfoList.get(i).getEntityPropName()+"InList";
                String entityPropNameNotInList = tableColumInfoList.get(i).getEntityPropName()+"NotInList";
                str =   "\t    <if test=\""+entityPropName+" != null\">\n" +
                        "\t       and "+tableColumName+" = #{"+entityPropName+",jdbcType="+jdbcType+"}\n" +
                        "\t    </if>\n";
                str +=  "\t    <if test=\""+entityPropNameInList+" != null and "+entityPropNameInList+".size()>0\">\n" +
                        "\t       and "+tableColumName+" in\n" +
                        "\t         <foreach collection=\""+entityPropNameInList+"\" index=\"index\" item=\"item\" separator=\",\" open=\"(\" close=\")\">\n" +
                        "\t              #{item}\n" +
                        "\t         </foreach>\n" +
                        "\t    </if>\n" +
                        "\t    <if test=\""+entityPropNameNotInList+" != null and "+entityPropNameNotInList+".size()>0\">\n" +
                        "\t       and "+tableColumName+" not in\n" +
                        "\t         <foreach collection=\""+entityPropNameNotInList+"\" index=\"index\" item=\"item\" separator=\",\" open=\"(\" close=\")\">\n" +
                        "\t             #{item}\n" +
                        "\t         </foreach>\n" +
                        "\t    </if>";
            }else{
                str =   "\t    <if test=\""+entityPropName+" != null\">\n" +
                        "\t       and "+tableColumName+" = #{"+entityPropName+",jdbcType="+jdbcType+"}\n" +
                        "\t    </if>";
            }
            if(i != tableColumInfoList.size()-1){
                sb.append(str+"\n");
            }else{
                sb.append(str);
            }
        }
        return sb.toString();
    }

    /**
     * 创建【AND id】列表
     */
    private String createIdList(String prefix){
        StringBuffer sb = new StringBuffer();
        Map<String, String> idMap = GenProperties.tableColumInfoList.stream().filter(e -> e.getTableColumKey().equals("PRI")).collect(Collectors.toMap(e -> e.getEntityPropName(), e -> e.getTableColumName()));
        Map<String, String> typeMap = GenProperties.tableColumInfoList.stream().filter(e -> e.getTableColumKey().equals("PRI")).collect(Collectors.toMap(e -> e.getEntityPropName(), e -> e.getTableColumType()));
        for(Map.Entry<String,String> data: idMap.entrySet()){
            String jdbcType = typeMap.get(data.getKey()).toUpperCase();
            if(jdbcType.equals("INT")){
                jdbcType = "INTEGER";
            }else if(jdbcType.equals("DATETIME")){
                jdbcType = "DATE";
            }
            sb.append("AND "+data.getValue()+"=#{"+prefix+data.getKey()+",jdbcType="+jdbcType+"} ");
        }
        return sb.toString();
    }

    /**
     * 创建插入sql
     * @Param type=1:tableColumList    type=2:entityPropList
     */
    private String createTableColumList(byte type){
        List<String> tableColList = GenProperties.tableColumInfoList.stream().map(e -> e.getTableColumName()).collect(Collectors.toList());
        if(type == 1){
            return String.join(",",tableColList);
        }else if(type == 2){
            List<String> objList = tableColList.stream().map(e -> {
                return "#{"+ HumpUtils.convertToJava(e)+"}";
            }).collect(Collectors.toList());
            return String.join(",",objList);
        }else if(type == 3){
            List<String> objList = tableColList.stream().map(e -> {
                return "#{item."+HumpUtils.convertToJava(e)+"}";
            }).collect(Collectors.toList());
            return String.join(",",objList);
        }
        return null;
    }

    /**
     * 创建更新对象的setList sql
     */
    private String createSetList(String prefix){
        List<String> tableColList = GenProperties.tableColumInfoList.stream().map(e -> {
            return e.getTableColumName()+" = #{"+prefix+HumpUtils.convertToJava(e.getTableColumName())+"}";
        }).collect(Collectors.toList());
        return "set "+String.join(",",tableColList);
    }

}
