package com.lijun.autocode.GenProp;

import com.lijun.autocode.entity.TableColumInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动生成工具的属性
 * @author lijun
 */
public class GenProperties {

    /**
     * 数据库配置
     */
    public static final String URL = "jdbc:mysql://localhost:3306/yjhd";
    public static final String NAME = "root";
    public static final String PASS = "root";
    public static final String DRIVER = "com.mysql.jdbc.Driver";

    /**
     * 表名，使用逗号分隔
     */
    public static String tablenames = "sys_dict";

    /**
     * 作者名称
     */
    public static String authorName = "lijun";

    /**
     * entity路径配置
     */
    public static String entityPackageOutPath = "com.lijun.test.entity";

    /**
     * xml路径配置
     */
    public static String xmlPackageOutPath = "mybatis.test";

    /**
     * dao路径配置
     */
    public static String daoPackageOutPath = "com.lijun.test.dao";

    /**
     * service路径配置
     */
    public static String servicePackageOutPath = "com.lijun.test.service";

    /**
     * redis路径配置
     */
    public static String redisPackageOutPath = "com.lijun.test.cache";

    /**
     * controller路径配置
     */
    public static String controllerPackageOutPath = "com.lijun.test.controller";

    /**
     * resp对象路径配置
     */
    public static String respPackageOutPath = "com.lijun.test.webentity";

    /**
     * CommonUtil包路径
     */
    public static String commonUtilPath = "com.lijun.autocode.util.CommonUtil;";

    /**
     * 所有模板文件路径
     */
    public static String templateFilePath = "com.lijun.autocode.template";

    /**
     * autocode路径
     */
    public static String autocodePath = "com.lijun.autocode";

    /**
     * baseredis路径
     */
    public static String BaseRedisPath = "com.lijun.redis";


    /**
     * 当前表名
     */
    public static String tablename;

    /**
     * 表的列信息
     */
    public static List<TableColumInfo> tableColumInfoList = new ArrayList<>();

    /**
     * 实体类名称
     */
    public static String entityName;

    /**
     * 实体类对象名称
     */
    public static String objName;

    /**
     * 实体类全路径
     */
    public static String entityFullPath;

    /**
     * 控制层名称
     */
    public static String ctrlName;

    /**
     * 控制层全路径
     */
    public static String ctrlFullPath;

    /**
     * dao接口名称
     */
    public static String inftDaoFileName;

    /**
     * dao接口全路径
     */
    public static String inftDaoFullPath;

    /**
     * dao实现类名称
     */
    public static String implDaoFileName;

    /**
     * dao实现类全路径
     */
    public static String implDaoFullPath;

    /**
     * service接口名称
     */
    public static String inftServiceFileName;

    /**
     * service接口全路径
     */
    public static String inftServiceFullPath;

    /**
     * service实现类名称
     */
    public static String implServiceFileName;

    /**
     * service实现类全路径
     */
    public static String implServiceFullPath;

    /**
     * redis接口名称
     */
    public static String inftRedisFileName;

    /**
     * redis接口全路径
     */
    public static String inftRedisFullPath;

    /**
     * redis实现类名称
     */
    public static String implRedisFileName;

    /**
     * redis实现类全路径
     */
    public static String implRedisFullPath;

    /**
     * xml文件名
     */
    public static String xmlFileName;

}
