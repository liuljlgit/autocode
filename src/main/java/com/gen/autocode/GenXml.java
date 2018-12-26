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
            replaceMap.put("${baseColumnList}",createTableColumList((byte)1));
            replaceMap.put("${BaseObjectList}",createTableColumList((byte)2));
            replaceMap.put("${BaseItemList}",createTableColumList((byte)3));
            replaceMap.put("${setList}",createSetList(1));
            replaceMap.put("${batchSetList}",createBatchSetList(1));
            replaceMap.put("${fullSetList}",createSetList(2));
            replaceMap.put("${fullBatchSetList}",createBatchSetList(2));

            //自动生成xml文件
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
        StringBuffer sb = new StringBuffer();
        List<String> tableColList = GenProperties.tableColumInfoList.stream().map(e -> e.getTableColumName()).collect(Collectors.toList());
        if(type == 1){
            for(int i=0;i<tableColList.size();i++){
                if(i<(tableColList.size()-1)){
                    sb.append(tableColList.get(i)).append(",");
                }else{
                    sb.append(tableColList.get(i));
                }
                if(i>0 && (i+1)%5 == 0 && i<(tableColList.size()-1)){
                    sb.append("\n\t\t");
                }
            }
            return sb.toString();
        }else if(type == 2){
            for(int i=0;i<tableColList.size();i++){
                if(i<(tableColList.size()-1)){
                    sb.append("#{"+ HumpUtil.convertToJava(tableColList.get(i))+"}").append(",");
                }else{
                    sb.append("#{"+ HumpUtil.convertToJava(tableColList.get(i))+"}");
                }
                if(i>0 && (i+1)%5 == 0 && i<(tableColList.size()-1)){
                    sb.append("\n\t\t");
                }
            }
            return sb.toString();
        }else if(type == 3){
            for(int i=0;i<tableColList.size();i++){
                if(i<(tableColList.size()-1)){
                    sb.append("#{item."+ HumpUtil.convertToJava(tableColList.get(i))+"}").append(",");
                }else{
                    sb.append("#{item."+ HumpUtil.convertToJava(tableColList.get(i))+"}");
                }
                if(i>0 && (i+1)%5 == 0 && i<(tableColList.size()-1)){
                    sb.append("\n\t\t");
                }
            }
            return sb.toString();
        }else{
            return null;
        }
    }

    /**
     * 创建更新对象的setList sql
     */
    private String createSetList(Integer type){
        StringBuffer sb = new StringBuffer();
        List<TableColumInfo> tableColumInfoList = GenProperties.tableColumInfoList;
        String suf;
        for(int i=0;i<tableColumInfoList.size();i++){
            TableColumInfo e = tableColumInfoList.get(i);
            if(e.getTableColumKey().equals("PRI")){
                continue;
            }
            suf = i==(tableColumInfoList.size()-1) ? "":"\n";
            if(type == 1){
                sb.append("\t\t\t<if test=\""+HumpUtil.convertToJava(e.getTableColumName())+" != null\">\n" +
                        "\t\t\t\t"+e.getTableColumName()+" = #{"+HumpUtil.convertToJava(e.getTableColumName())+"},\n" +
                        "\t\t\t</if>"+suf);
            }else{
                sb.append("\t\t\t"+e.getTableColumName()+" = #{"+HumpUtil.convertToJava(e.getTableColumName())+"},"+suf);
            }
        }
        return sb.toString();
    }

    /**
     * 创建更新对象的setList sql
     */
    private String createBatchSetList(Integer type){
        StringBuffer sb = new StringBuffer();
        List<TableColumInfo> tableColumInfoList = GenProperties.tableColumInfoList;
        String suf;
        for(int i=0;i<tableColumInfoList.size();i++){
            TableColumInfo e = tableColumInfoList.get(i);
            if(e.getTableColumKey().equals("PRI")){
                continue;
            }
            suf = i==(tableColumInfoList.size()-1) ? "":"\n";
            if(type == 1){
                sb.append("\t\t\t\t<if test=\"item."+HumpUtil.convertToJava(e.getTableColumName())+" != null\">\n" +
                        "\t\t\t\t\t"+e.getTableColumName()+" = #{item."+HumpUtil.convertToJava(e.getTableColumName())+"},\n" +
                        "\t\t\t\t</if>"+suf);
            }else{
                sb.append("\t\t\t\t"+e.getTableColumName()+" = #{item."+HumpUtil.convertToJava(e.getTableColumName())+"}," + suf);
            }
        }
        return sb.toString();
    }

}
