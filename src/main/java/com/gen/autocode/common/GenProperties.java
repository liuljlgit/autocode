package com.gen.autocode.common;

import com.gen.autocode.entity.TableColumInfo;

import java.util.List;

/**
 * 自动生成工具的属性
 * @author gen
 */
public class GenProperties {

    /**
     * 数据库配置
     */
    public static String URL;
    public static String NAME;
    public static String PASS;
    public static String DRIVER;

    /**
     * 表名，使用逗号分隔
     */
    public static String tablenames;

    /**
     * 是否使用缓存
     */
    public static Boolean useCache;

    /**
     * 作者名称
     */
    public static String authorName = "lijun";

    /**
     * entity路径配置
     */
    public static String entityPackageOutPath;

    /**
     * xml路径配置
     */
    public static String xmlPackageOutPath;

    /**
     * dao路径配置
     */
    public static String daoPackageOutPath;

    /**
     * service路径配置
     */
    public static String servicePackageOutPath;

    /**
     * redis路径配置
     */
    public static String redisPackageOutPath;

    /**
     * controller路径配置
     */
    public static String controllerPackageOutPath;

    /**
     * resp对象路径配置
     */
    public static String respPackageOutPath;

    /**
     * entity路径配置
     */
    public static String templatePath;

    /**
     * 当前表名
     */
    public static String tablename;

    /**
     * 表的列信息
     */
    public static List<TableColumInfo> tableColumInfoList;

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
