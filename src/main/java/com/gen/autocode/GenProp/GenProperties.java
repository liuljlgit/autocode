package com.gen.autocode.GenProp;

import com.gen.autocode.entity.TableColumInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动生成工具的属性
 * @author gen
 */
public class GenProperties {

    /**
     * 数据库配置
     */
    public static String URL = "jdbc:mysql://192.168.1.135:3306/test";
    public static String NAME = "root";
    public static String PASS = "root";
    public static String DRIVER = "com.mysql.jdbc.Driver";

    /**
     * 表名，使用逗号分隔
     */
    public static String tablenames = "daily_amount";

    /**
     * 是否是视图
     */
    public static Boolean isTableView = Boolean.FALSE;

    /**
     * 是否不使用缓存
     */
    public static Boolean isNoUseCache = Boolean.FALSE;

    /**
     * 作者名称
     */
    public static String authorName = "lijun";

    /**
     * entity路径配置
     */
    public static String entityPackageOutPath = "com.gen.test.entity";

    /**
     * xml路径配置
     */
    public static String xmlPackageOutPath = "mybatis.test";

    /**
     * dao路径配置
     */
    public static String daoPackageOutPath = "com.gen.test.dao";

    /**
     * service路径配置
     */
    public static String servicePackageOutPath = "com.gen.test.service";

    /**
     * redis路径配置
     */
    public static String redisPackageOutPath = "com.gen.test.cache";

    /**
     * controller路径配置
     */
    public static String controllerPackageOutPath = "com.gen.test.controller";

    /**
     * resp对象路径配置
     */
    public static String respPackageOutPath = "com.gen.test.webentity";

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

    /**
     * resp文件名
     */
    public static String respFileName;

    /**
     * resp文件路径
     */
    public static String respFullPath;

}
