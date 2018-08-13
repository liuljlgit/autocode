package com.lijun.autocode.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 公共工具类
 * @author lijun
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
     * 把对象转换成一个redis的key值
     * @param t
     * @param bWithSupperFields
     * @param <T>
     * @return
     */
    public static <T> String getAttrVal4ForRedisKey(T t, boolean bWithSupperFields) {
        StringBuffer sb = new StringBuffer();
        Field[] thisClazzFields = null;
        Field[] finalFields = new Field[0];
        Class thisClazz = t.getClass();

        Integer orinLength;
        while(thisClazz != null) {
            thisClazzFields = thisClazz.getDeclaredFields();
            if (!Objects.isNull(thisClazzFields)) {
                orinLength = finalFields.length;
                finalFields = (Field[]) Arrays.copyOf(finalFields, finalFields.length + thisClazzFields.length);
                System.arraycopy(thisClazzFields, 0, finalFields, orinLength, thisClazzFields.length);
            }

            if (bWithSupperFields) {
                thisClazz = thisClazz.getSuperclass();
            } else {
                thisClazz = null;
            }
        }

        orinLength = null;

        try {
            for(int i = 0; i < finalFields.length; ++i) {
                Field field = finalFields[i];
                String fieldName = field.getName();
                if (!"page".equals(fieldName)) {
                    Object val = null;
                    StringBuffer fieldGet = new StringBuffer("get");
                    fieldGet.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));

                    try {
                        Method getMethod = t.getClass().getMethod(fieldGet.toString());
                        val = getMethod.invoke(t);
                    } catch (NoSuchMethodException var12) {
                        field.setAccessible(true);
                        val = field.get(t);
                    } catch (InvocationTargetException var13) {
                        var13.printStackTrace();
                    }

                    if (val != null) {
                        sb.append(field.getName()).append(val);
                    }
                }
            }
        } catch (IllegalArgumentException var14) {
            logger.error(var14.getMessage());
        } catch (IllegalAccessException var15) {
            logger.error(var15.getMessage());
        }

        String val = SecureUtil.md5X16Str(sb.toString(), "utf-8");
        sb = (new StringBuffer(t.getClass().getSimpleName())).append(":total:").append(val);
        return sb.toString();
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
