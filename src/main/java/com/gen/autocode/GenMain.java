package com.gen.autocode;

import com.cloud.common.utils.HumpUtil;
import com.gen.autocode.GenProp.GenCommon;
import com.gen.autocode.GenProp.GenProperties;
import com.gen.autocode.entity.TableColumInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自动生成工具的入口函数
 * @author gen
 */
public class GenMain {

    public static void main(String[] args){
        String tablenames = GenProperties.tablenames;
        Arrays.stream(tablenames.split(",")).forEach(e->{
            initGenProperties(e);       //初始化配置
            new GenEntity();            //生成实体类
            new GenXml();               //生成XML
            new GenDao();               //生成DAO
            new GenService();           //生成SERVICE
            new GenCtrl();              //生成Controller
            new GenResp();              //生成resp对象文件
            if(GenProperties.useCache){
                new GenRedis();        //生成Redis对象文件
            }
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
                String entityPropName = HumpUtil.convertToJava(tableColumName);
                List<String> entityType = GenCommon.sqlType2JavaType(tableColumType);
                TableColumInfo info = new TableColumInfo(tableColumName,tableColumType,tableColumKey,tableColumComment,entityPropName,entityType.get(0),entityType.get(1));
                tableColumInfoList.add(info);
            }
            GenProperties.tableColumInfoList = tableColumInfoList;

            //设置：{表名、实体名、实体全路径、控制层名称、控制层全路径、dao接口名称、dao接口全路径、dao实现类名称、
            //     dao实现类全路径、service接口名称、service接口全路径、service实现类名称、service实现类全路径、xml文件名称}
            GenProperties.tablename = tablename;
            GenProperties.entityName = HumpUtil.toUpperCaseFirstOne(HumpUtil.convertToJava(tablename));
            GenProperties.objName = HumpUtil.convertToJava(tablename);
            GenProperties.entityFullPath = GenProperties.entityPackageOutPath.concat(".").concat(GenProperties.entityName);
            //控制层
            GenProperties.ctrlName = HumpUtil.toUpperCaseFirstOne(HumpUtil.convertToJava(tablename)).concat("Ctrl");
            GenProperties.ctrlFullPath = GenProperties.controllerPackageOutPath.concat(".").concat(GenProperties.ctrlName);
            //dao层
            GenProperties.inftDaoFileName = "I"+HumpUtil.toUpperCaseFirstOne(HumpUtil.convertToJava(tablename)).concat("Dao");
            GenProperties.inftDaoFullPath = GenProperties.daoPackageOutPath.concat(".inft.").concat(GenProperties.inftDaoFileName);
            GenProperties.implDaoFileName = HumpUtil.toUpperCaseFirstOne(HumpUtil.convertToJava(tablename)).concat("Dao");
            GenProperties.implDaoFullPath = GenProperties.daoPackageOutPath.concat(".impl.").concat(GenProperties.implDaoFileName);
            //service层
            GenProperties.inftServiceFileName = "I"+HumpUtil.toUpperCaseFirstOne(HumpUtil.convertToJava(tablename)).concat("Service");
            GenProperties.inftServiceFullPath = GenProperties.servicePackageOutPath.concat(".inft.").concat(GenProperties.inftServiceFileName);
            GenProperties.implServiceFileName = HumpUtil.toUpperCaseFirstOne(HumpUtil.convertToJava(tablename)).concat("ServiceImpl");
            GenProperties.implServiceFullPath = GenProperties.servicePackageOutPath.concat(".impl.").concat(GenProperties.implServiceFileName);
            //redis层
            GenProperties.inftRedisFileName = "I"+HumpUtil.toUpperCaseFirstOne(HumpUtil.convertToJava(tablename)).concat("Redis");
            GenProperties.inftRedisFullPath = GenProperties.redisPackageOutPath.concat(".inft.").concat(GenProperties.inftRedisFileName);
            GenProperties.implRedisFileName = HumpUtil.toUpperCaseFirstOne(HumpUtil.convertToJava(tablename)).concat("RedisImpl");
            GenProperties.implRedisFullPath = GenProperties.redisPackageOutPath.concat(".impl.").concat(GenProperties.implRedisFileName);
            //xml层
            GenProperties.xmlFileName = HumpUtil.toUpperCaseFirstOne(HumpUtil.convertToJava(tablename)).concat("Mapper");
            //resp层
            GenProperties.respFileName = GenProperties.entityName+"Resp";
            GenProperties.respFullPath = GenProperties.respPackageOutPath.concat(".").concat(GenProperties.respFileName);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
