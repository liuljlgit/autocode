package com.lijun.autocode.GenProp;

import com.lijun.autocode.util.HumpUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 公共操作类
 * @author lijun
 */
public class GenCommon {

    /**
     * 生成文件操作
     * @param isJava true:生成java文件  false:生成xml文件
     * @param fileName 生成文件名称
     * @param packageOutPath 文件生成路径
     * @param sb 生成文件内容
     */
    public static void createFile(Boolean isJava,String fileName,String packageOutPath,String sb){
        File directory = new File("");
        File dirFile = null;
        String outputPath = null;
        try {
            if(isJava){
                dirFile = new File(directory.getAbsolutePath()+ "/src/main/java/"+packageOutPath.replace(".", "/")+"/");
                if(!dirFile.exists()){
                    dirFile.mkdirs();
                }
                outputPath = dirFile.toString()+"/"+fileName + ".java";
            }else{
                dirFile = new File(directory.getAbsolutePath()+ "/src/main/resources/"+packageOutPath.replace(".", "/")+"/");
                if(!dirFile.exists()){
                    dirFile.mkdirs();
                }
                outputPath = dirFile.toString()+"/"+fileName + ".xml";
            }
            FileWriter fw = new FileWriter(outputPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sb.toString());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成类的注释
     * @return
     */
    public static String createFileNote(String note){
        StringBuffer sb = new StringBuffer();
        sb.append("/**\r\n");
        sb.append("* "+note+" \r\n");
        sb.append("* "+new Date()+" "+ GenProperties.authorName+"\r\n");
        sb.append("*/");
        return sb.toString();
    }

    /**
     * 创建Field的注释
     * @return
     */
    public static String createFieldNote(String note){
        StringBuffer sb = new StringBuffer();
        sb.append("\t/**\r\n");
        sb.append("\t* field comment: "+note+" \r\n");
        sb.append("\t*/");
        return sb.toString();
    }

    /**
     * 功能：数据库类型转换成java中的类型
     * @return
     */
    public static List<String> sqlType2JavaType(String sqlType) {
        List<String> result = new ArrayList<>();
        if(sqlType.equalsIgnoreCase("bit")){
            result.add("Boolean");
            result.add("java.lang.Boolean;");
        }else if(sqlType.equalsIgnoreCase("tinyint")){
            result.add("Byte");
            result.add("java.lang.Byte;");
        }else if(sqlType.equalsIgnoreCase("smallint")){
            result.add("Short");
            result.add("java.lang.Short;");
        }else if(sqlType.equalsIgnoreCase("int")){
            result.add("Integer");
            result.add("java.lang.Integer;");
        }else if(sqlType.equalsIgnoreCase("bigint")){
            result.add("Long");
            result.add("java.lang.Long;");
        }else if(sqlType.equalsIgnoreCase("float")){
            result.add("Float");
            result.add("java.lang.Float;");
        }else if(sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")){
            result.add("BigDecimal");
            result.add("java.math.BigDecimal;");
        }else if(sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text")){
            result.add("String");
            result.add("java.lang.String;");
        }else if(sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("timestamp") || sqlType.equalsIgnoreCase("date")){
            result.add("Date");
            result.add("java.util.Date;");
        }else{
            result.add("null");
            result.add("null");
        }
        return result;
    }

    /**
     * 模板文件的替换,也就是生成文件的内容
     */
    public static String replaceTemplateContent(String templateFileName,Map<String,String> replaceMap){
        try{
            String templateFile = new File("").getAbsolutePath()+ "/src/main/java/"+GenProperties.templateFilePath.replace(".", "/")+"/"+templateFileName;
            BufferedReader br = new BufferedReader(new FileReader(templateFile));
            StringBuffer sb = new StringBuffer("");
            String s = null;
            while((s = br.readLine()) != null){
                for(Map.Entry<String, String> data : replaceMap.entrySet()){
                    s = s.replace(data.getKey(),data.getValue());
                }
                sb.append(s);
                sb.append("\n");
            }
            return sb.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据导入到Set集合，得到导入类列表
     */
    public static String changeImportSetToString(Set<String> entityFullTypeSet){
        StringBuffer entityFullTypeString = new StringBuffer("");
        Iterator<String> it = entityFullTypeSet.iterator();
        while(it.hasNext()){
            String nt = it.next();
            entityFullTypeString.append("import ").append(nt).append("\n");
        }
        return entityFullTypeString.toString();
    }

    /**
     * 替换公共的内容
     * @return
     */
    public static Map<String,String> createReplaceMap(){
        Map<String,String> replaceMap = new HashMap<>();
        replaceMap.put("${entityName}",GenProperties.entityName);
        replaceMap.put("${entityObj}",GenProperties.objName);
        replaceMap.put("${inftServiceName}",GenProperties.inftServiceFileName);
        replaceMap.put("${implServiceName}", HumpUtils.toLowerCaseFirstOne(GenProperties.implServiceFileName));
        return replaceMap;
    }

    /**
     * 得到id列表数据
     */
    public static List<String> getTableIdList(){
        return GenProperties.tableColumInfoList.stream().filter(e -> e.getTableColumKey().equals("PRI")).map(e->e.getTableColumName()).collect(Collectors.toList());
    }

    /**
     * 得到entity_id列表数据
     */
    public static List<String> getEntityIdList(){
        return GenProperties.tableColumInfoList.stream().filter(e -> e.getTableColumKey().equals("PRI")).map(e->e.getEntityPropName()).collect(Collectors.toList());
    }

    /**
     * 得到实体id列表和类型
     */
    public static Map<String,String> getEntityIdMap(){
        return GenProperties.tableColumInfoList.stream().filter(e -> e.getTableColumKey().equals("PRI")).collect(Collectors.toMap(e->e.getEntityPropName(),e->e.getEntityType()));
    }

    /**
     * 得到表名称列表
     */
    public static List<String> getTableColNameList(){
        return GenProperties.tableColumInfoList.stream().map(e->e.getTableColumName()).collect(Collectors.toList());
    }

    /**
     * 得到实体名称列表
     */
    public static List<String> getEntityColNameList(){
        return GenProperties.tableColumInfoList.stream().map(e->e.getEntityPropName()).collect(Collectors.toList());
    }

    /**
     * 得到实体名称类型列表
     */
    public static Map<String,String> getEntityColNameAndTypeList(){
        return GenProperties.tableColumInfoList.stream().collect(Collectors.toMap(e->e.getEntityPropName(),e->e.getEntityType()));
    }

}
