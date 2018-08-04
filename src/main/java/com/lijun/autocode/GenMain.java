package com.lijun.autocode;

import com.lijun.autocode.util.HumpUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自动生成工具的入口函数
 * @author lijun
 */
public class GenMain {

    public static void main(String[] args){
        String tablenames = GenProperties.tablenames;
        Arrays.stream(tablenames.split(",")).forEach(e->{
            initGenProperties(e);         //初始化配置
            new GenEntityMysql();        //生成实体类
            new GenMybatisXml();         //生成XML
            new GenEntityDao();          //生成DAO
            new GenEntityService();      //生成SERVICE
            new GenEntityCtrl();         //生成Controller
        });
    }

    /**
     * 连接数据库
     */
    public static void initGenProperties(String tablename){
        try{
            Connection con = DriverManager.getConnection(GenProperties.URL,GenProperties.NAME,GenProperties.PASS);
            ResultSet rs = con.createStatement().executeQuery("show full columns from " + tablename);
            List<TableColumInfo> tableColumInfoList = new ArrayList<>();
            while (rs.next()) {
                String tableColumName = rs.getString("Field");
                String tableColumType = rs.getString("Type");
                if(tableColumType.indexOf("(") != -1){
                    tableColumType = tableColumType.substring(0,tableColumType.indexOf("("));
                }
                String tableColumKey = rs.getString("key");
                String tableColumComment = rs.getString("Comment");
                String entityPropName = HumpUtils.convertToJava(tableColumName);
                List<String> entityType = GenCommon.sqlType2JavaType(tableColumType);
                TableColumInfo info = new TableColumInfo(tableColumName,tableColumType,tableColumKey,tableColumComment,entityPropName,entityType.get(0),entityType.get(1));
                tableColumInfoList.add(info);
            }
            GenProperties.tableColumInfoList = tableColumInfoList;

            //设置：{表名、实体名、实体全路径、控制层名称、控制层全路径、dao接口名称、dao接口全路径、dao实现类名称、
            //     dao实现类全路径、service接口名称、service接口全路径、service实现类名称、service实现类全路径、xml文件名称}
            GenProperties.tablename = tablename;
            GenProperties.entityName = HumpUtils.toUpperCaseFirstOne(HumpUtils.convertToJava(tablename));
            GenProperties.objName = HumpUtils.convertToJava(tablename);
            GenProperties.entityFullPath = GenProperties.entityPackageOutPath.concat(".").concat(GenProperties.entityName);
            GenProperties.ctrlName = HumpUtils.toUpperCaseFirstOne(HumpUtils.convertToJava(tablename)).concat("Controller");
            GenProperties.ctrlFullPath = GenProperties.controllerPackageOutPath.concat(".").concat(GenProperties.ctrlName);
            GenProperties.inftDaoFileName = "I"+HumpUtils.toUpperCaseFirstOne(HumpUtils.convertToJava(tablename)).concat("Dao");
            GenProperties.inftDaoFullPath = GenProperties.daoPackageOutPath.concat(".inft.").concat(GenProperties.inftDaoFileName);
            GenProperties.implDaoFileName = HumpUtils.toUpperCaseFirstOne(HumpUtils.convertToJava(tablename)).concat("Dao");
            GenProperties.implDaoFullPath = GenProperties.daoPackageOutPath.concat(".impl.").concat(GenProperties.implDaoFileName);
            GenProperties.inftServiceFileName = "I"+HumpUtils.toUpperCaseFirstOne(HumpUtils.convertToJava(tablename)).concat("Service");
            GenProperties.inftServiceFullPath = GenProperties.servicePackageOutPath.concat(".inft.").concat(GenProperties.inftServiceFileName);
            GenProperties.implServiceFileName = HumpUtils.toUpperCaseFirstOne(HumpUtils.convertToJava(tablename)).concat("Service");
            GenProperties.implServiceFullPath = GenProperties.servicePackageOutPath.concat(".impl.").concat(GenProperties.implServiceFileName);
            GenProperties.xmlFileName = HumpUtils.toUpperCaseFirstOne(HumpUtils.convertToJava(tablename));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
