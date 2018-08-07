package com.lijun.autocode.entity;

/**
 * 数据库表的列相关信息
 */
public class TableColumInfo {
    /**
     * 列名
     */
    private String tableColumName;

    /**
     * 列值
     */
    private String tableColumType;

    /**
     * 列键信息
     */
    private String tableColumKey;

    /**
     * 列注释
     */
    private String tableColumComment;

    /**
     * 转换成对象的属性名
     */
    private String entityPropName;

    /**
     * 转换成Java对象中的类型
     */
    private String entityType;

    /**
     * 转换成Java对象中类型的全路径
     */
    private String entityFullType;

    public TableColumInfo() {
    }

    public TableColumInfo(String tableColumName, String tableColumType, String tableColumKey, String tableColumComment, String entityPropName, String entityType, String entityFullType) {
        this.tableColumName = tableColumName;
        this.tableColumType = tableColumType;
        this.tableColumKey = tableColumKey;
        this.tableColumComment = tableColumComment;
        this.entityPropName = entityPropName;
        this.entityType = entityType;
        this.entityFullType = entityFullType;
    }

    public String getTableColumName() {
        return tableColumName;
    }

    public void setTableColumName(String tableColumName) {
        this.tableColumName = tableColumName;
    }

    public String getTableColumType() {
        return tableColumType;
    }

    public void setTableColumType(String tableColumType) {
        this.tableColumType = tableColumType;
    }

    public String getTableColumKey() {
        return tableColumKey;
    }

    public void setTableColumKey(String tableColumKey) {
        this.tableColumKey = tableColumKey;
    }

    public String getTableColumComment() {
        return tableColumComment;
    }

    public void setTableColumComment(String tableColumComment) {
        this.tableColumComment = tableColumComment;
    }

    public String getEntityPropName() {
        return entityPropName;
    }

    public void setEntityPropName(String entityPropName) {
        this.entityPropName = entityPropName;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityFullType() {
        return entityFullType;
    }

    public void setEntityFullType(String entityFullType) {
        this.entityFullType = entityFullType;
    }
}
