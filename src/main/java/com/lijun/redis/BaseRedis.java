package com.lijun.redis;

import com.lijun.autocode.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class BaseRedis<K, V> {
    private Logger logger = LoggerFactory.getLogger(BaseRedis.class);
    protected static final Integer LOAD_PAGE_SIZE = 1000;
    @Autowired
    protected RedisTemplate<K, V> redisTemplate;
    @Autowired
    protected RedisTemplate<K, V> transRedisTemplate;

    public BaseRedis() {}

    protected Long incr(String key) {
        Long result = (Long)this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            return connection.incr(byteKey);
        });
        return result;
    }

    protected Long decr(String key) {
        Long result = (Long)this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            return connection.decr(byteKey);
        });
        return result;
    }

    protected Long incrBy(String key, Long totalValue) {
        Long result = (Long)this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            return connection.incrBy(byteKey, totalValue);
        });
        return result;
    }

    protected Long decrBy(String key, Long totalValue) {
        Long result = (Long)this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            return connection.decrBy(byteKey, totalValue);
        });
        return result;
    }

    protected boolean set(String key, String value, long seconds) {
        return this.set(key, value, seconds, false);
    }

    protected boolean set(String key, String value, long seconds, boolean suppressTran) {
        boolean result = (Boolean)this.getRedisTemplate(suppressTran).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[] byteValue = serializer.serialize(value);
            if (seconds > 0L) {
                connection.setEx(byteKey, seconds, byteValue);
            } else {
                connection.set(byteKey, byteValue);
            }

            return true;
        });
        return result;
    }

    protected boolean set(String key, Serializable value, long seconds) {
        boolean result = (Boolean)this.transRedisTemplate.execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[] byteValue = CommonUtil.transObj2ByteArray(value);
            if (seconds > 0L) {
                connection.setEx(byteKey, seconds, byteValue);
            } else {
                connection.set(byteKey, byteValue);
            }

            return true;
        });
        return result;
    }

    protected Object get(String key) {
        Object result = this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[] value = connection.get(byteKey);
            if (value == null) {
                return null;
            } else {
                Object dataObj = serializer.deserialize(value);
                return dataObj;
            }
        });
        return result;
    }

    protected <T extends Serializable> T get(String key, Class<T> clazz) {
        Serializable result = (Serializable)this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[] value = connection.get(byteKey);
            if (value == null) {
                return null;
            } else {
                T dataObj = CommonUtil.transByteArray2Obj(value, clazz);
                return dataObj;
            }
        });
        return (T)result;
    }

    protected Object mget(List<String> keys) {
        Object result = this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            List<byte[]> listByteKeys = new ArrayList();
            Iterator var5 = keys.iterator();

            while(var5.hasNext()) {
                String key = (String)var5.next();
                byte[] byteKey = serializer.serialize(key);
                listByteKeys.add(byteKey);
            }

            List<byte[]> listByteValues = connection.mGet((byte[][])listByteKeys.toArray(new byte[listByteKeys.size()][]));
            List<Object> listValue = new ArrayList();
            Iterator var11 = listByteValues.iterator();

            while(var11.hasNext()) {
                byte[] value = (byte[])var11.next();
                listValue.add(serializer.deserialize(value));
            }

            return listValue;
        });
        return result;
    }

    protected Object keys(String argKey) {
        Object result = this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(argKey);
            Set<byte[]> setBytes = connection.keys(byteKey);
            List<Object> listValue = new ArrayList();
            Iterator var7 = setBytes.iterator();

            while(var7.hasNext()) {
                byte[] value = (byte[])var7.next();
                listValue.add(serializer.deserialize(value));
            }

            return listValue;
        });
        return result;
    }

    protected <T extends Serializable> List<T> mget(List<String> keys, Class<T> clazz) {
        List result = (List)this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            List<byte[]> listByteKeys = new ArrayList();
            Iterator var6 = keys.iterator();

            while(var6.hasNext()) {
                String key = (String)var6.next();
                byte[] byteKey = serializer.serialize(key);
                listByteKeys.add(byteKey);
            }

            List<byte[]> listByteValues = connection.mGet((byte[][])listByteKeys.toArray(new byte[listByteKeys.size()][]));
            List<T> listValue = new ArrayList();
            Iterator var12 = listByteValues.iterator();

            while(var12.hasNext()) {
                byte[] value = (byte[])var12.next();
                listValue.add(CommonUtil.transByteArray2Obj(value, clazz));
            }

            return listValue;
        });
        return (List<T>) result;
    }

    protected boolean exists(String key) {
        boolean result = (Boolean)this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            return connection.exists(byteKey);
        });
        return result;
    }

    protected Long ttl(String key) {
        Long result = (Long)this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            return connection.ttl(byteKey);
        });
        return result;
    }

    protected boolean sadd(String key, String value) {
        return this.sadd(key, value, false);
    }

    protected boolean sadd(String key, String value, boolean suppressTran) {
        boolean result = (Boolean)this.getRedisTemplate(suppressTran).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[] byteValue = serializer.serialize(value);
            connection.sAdd(byteKey, new byte[][]{byteValue});
            return true;
        });
        return result;
    }


    private RedisTemplate<K, V> getRedisTemplate(Boolean suppTrans) {
        this.redisTemplate.setEnableTransactionSupport(false);
        this.transRedisTemplate.setEnableTransactionSupport(true);
        return suppTrans ? this.redisTemplate : this.transRedisTemplate;
    }
}
