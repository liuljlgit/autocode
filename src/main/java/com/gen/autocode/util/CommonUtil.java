package com.gen.autocode.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 公共工具类
 * @author gen
 */
public class CommonUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 复制属性，忽略空值
     * @param src
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * 得到空值的属性名称
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet();
        PropertyDescriptor[] var4 = pds;
        int var5 = pds.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor pd = var4[var6];
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 生成redis的key
     * 根据不为空的属性和为Boolean类型值为true的属性构成
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String createRedisKey(T t,boolean isEncrypt){
        List<Field> allField = getAllField(t.getClass(), null);
        List<Field> filterField = allField.stream().filter((Field e) -> {
            try {
                e.setAccessible(true);
                Object obj = e.get(t);
                if (Objects.isNull(obj)) {
                    return false;
                }
                if (obj instanceof Boolean && obj == Boolean.FALSE) {
                    return false;
                }
                if(e.getName().startsWith("PROP_")){
                    return false;
                }
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
            return true;
        }).collect(Collectors.toList());
        List<String> list = new ArrayList<>();
        for(Field f : filterField){
            try {
                f.setAccessible(true);
                Object obj = f.get(t);
                if(obj instanceof Collection){
                    Object collect = ((Collection) obj).stream().sorted().collect(Collectors.toList());
                    String name = f.getName();
                    String str = name+":"+JSON.toJSONString(collect);
                    list.add(str);
                    continue;
                }
                String name = f.getName();
                String str = name+":"+JSON.toJSONString(obj);
                list.add(str);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(isEncrypt){
            return SecureUtil.md5X16Str(String.join("_",list),"utf-8");
        }else{
            return String.join("_",list);
        }
    }

    /**
     * 把对象转换成一个redis的key值
     * @param clazz
     * @param fields
     * @return
     */
    public static List<Field> getAllField(Class clazz, List<Field> fields) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        Field[] allFields = clazz.getDeclaredFields();
        for (Field field : allFields) {
            fields.add(field);
        }
        if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Object.class)) {
            getAllField(clazz.getSuperclass(), fields);
        }
        return fields;
    }

    /**
     * 对象转数组
     *
     * @param input
     * @return
     */
    public static byte[] transObj2ByteArray(Serializable input) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(input);
            return baos.toByteArray();
        } catch (IOException e) {
            logger.debug("序列化失败", e);
            return null;
        } finally {
            closeOutputStream(oos);
            closeOutputStream(baos);
        }
    }

    /**
     * 数组转换成对象
     *
     * @param input
     * @param clazz
     * @return
     */
    public static <T extends Serializable> T transByteArray2Obj(byte[] input, Class<T> clazz) {
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        //基本数据类型判断，否则会报错
        if(clazz.equals(Long.class)){
            try{
                T l=clazz.cast(Long.parseLong(new String(input)));
                return l;
            }catch (Exception ex){
            }
        }
        try {
            bis = new ByteArrayInputStream(input);
            ois = new ObjectInputStream(bis);
            Object obj = ois.readObject();
            return clazz.cast(obj);
        } catch (NullPointerException ne) {
        } catch (Exception e) {
            logger.debug("反序列化失败", e);
        } finally {
            closeInpputStream(bis);
            closeInpputStream(ois);
        }
        return null;
    }

    /**
     * 关闭输出流代码
     *
     * @author shenxufei
     * @param is
     */
    public static void closeInpputStream(InputStream is) {
        try {
            is.close();
        } catch (Exception e) {
        }
    }

    /**
     * 关闭输出流代码
     *
     * @author shenxufei
     * @param os
     */
    public static void closeOutputStream(OutputStream os) {
        try {
            os.close();
        } catch (Exception e) {
        }
    }
}
