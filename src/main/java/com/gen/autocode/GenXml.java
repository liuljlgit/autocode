package com.gen.autocode;

import com.cloud.common.utils.HumpUtil;
import com.gen.autocode.GenProp.GenCommon;
import com.gen.autocode.GenProp.GenProperties;
import com.gen.autocode.entity.TableColumInfo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 自动生成xml文件
 * @author gen
 */
public class GenXml {
    /**
     * 构造函数
     */
    public GenXml(){
        try{
            //替换内容
            Map<String,String> replaceMap = GenCommon.createReplaceMap();
            replaceMap.put("${resultMapResultList}",createResultList());
            replaceMap.put("${whereSqlList}",createWhereSqlList());
            replaceMap.put("${tableColumList}",createTableColumList((byte)1));
            replaceMap.put("${entityPropList}",createTableColumList((byte)2));
            replaceMap.put("${batchEntityPropList}",createTableColumList((byte)3));
            replaceMap.put("${setList}",createSetList(""));
            replaceMap.put("${batchSetList}",createSetList("obj."));

            //创建文件
            GenCommon.createFile(false,
                    GenProperties.xmlFileName,
                    GenProperties.xmlPackageOutPath,
                    GenCommon.replaceTemplateContent("XmlTemplate",replaceMap));
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
     * 创建插入sql
     * @Param type=1:tableColumList    type=2:entityPropList
     */
    private String createTableColumList(byte type){
        List<String> tableColList = GenProperties.tableColumInfoList.stream().map(e -> e.getTableColumName()).collect(Collectors.toList());
        if(type == 1){
            return String.join(",",tableColList);
        }else if(type == 2){
            List<String> objList = tableColList.stream().map(e -> {
                return "#{"+ HumpUtil.convertToJava(e)+"}";
            }).collect(Collectors.toList());
            return String.join(",",objList);
        }else if(type == 3){
            List<String> objList = tableColList.stream().map(e -> {
                return "#{item."+HumpUtil.convertToJava(e)+"}";
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
            return e.getTableColumName()+" = #{"+prefix+HumpUtil.convertToJava(e.getTableColumName())+"}";
        }).collect(Collectors.toList());
        return "set "+String.join(",",tableColList);
    }

}
