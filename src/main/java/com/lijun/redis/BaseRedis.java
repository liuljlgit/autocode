package com.lijun.redis;

import com.lijun.autocode.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;
import java.util.*;

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

    protected boolean sadd(String key, Serializable value) {
        return this.sadd(key, value, false);
    }

    protected boolean sadd(String key, Serializable value, boolean suppressTran) {
        boolean result = (Boolean)this.getRedisTemplate(suppressTran).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[] byteValue = CommonUtil.transObj2ByteArray(value);
            connection.sAdd(byteKey, new byte[][]{byteValue});
            return true;
        });
        return result;
    }

    protected boolean sadd(String key, Collection<? extends Serializable> values) {
        return this.sadd(key, values, false);
    }

    protected boolean sadd(String key, Collection<? extends Serializable> values, boolean suppressTran) {
        boolean result = (Boolean)this.getRedisTemplate(suppressTran).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[][] byteValues = new byte[values.size()][];
            int i = 0;

            Serializable v;
            for(Iterator var8 = values.iterator(); var8.hasNext(); byteValues[i++] = CommonUtil.transObj2ByteArray(v)) {
                v = (Serializable)var8.next();
            }

            connection.sAdd(byteKey, byteValues);
            return true;
        });
        return result;
    }

    protected Long scard(String key) {
        Object result = this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            Long count = connection.sCard(byteKey);
            return count;
        });
        return Long.parseLong(String.valueOf(result));
    }

    protected Object smembers(String key) {
        Object result = this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            Set setValue = connection.sMembers(byteKey);
            Iterator iter = setValue.iterator();
            ArrayList listObj = new ArrayList();

            while(iter.hasNext()) {
                Object obj = serializer.deserialize((byte[])((byte[])iter.next()));
                listObj.add(obj);
            }

            return listObj;
        });
        return result;
    }

    protected <T extends Serializable> Set<T> smembers(String key, Class<T> clazz) {
        Set result = (Set)this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            Set<byte[]> setValue = connection.sMembers(byteKey);
            Iterator<byte[]> iter = setValue.iterator();
            HashSet result1 = new HashSet();

            while(iter.hasNext()) {
                T tmp = CommonUtil.transByteArray2Obj((byte[])iter.next(), clazz);
                result1.add(tmp);
            }

            return result1.size() == 0 ? null : result1;
        });
        return (Set<T>) result;
    }

    protected Set<String> smembersString(String key) {
        Set result = (Set)this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            Set<byte[]> setValue = connection.sMembers(byteKey);
            Iterator<byte[]> iter = setValue.iterator();
            HashSet setStr = new HashSet();

            while(iter.hasNext()) {
                String obj = (String)serializer.deserialize((byte[])iter.next());
                setStr.add(obj);
            }

            return setStr;
        });
        return (Set<String>)result;
    }

    protected boolean srem(String key, String value) {
        return this.srem(key, value, false);
    }

    protected boolean srem(String key, String value, boolean suppressTran) {
        boolean result = (Boolean)this.getRedisTemplate(suppressTran).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[] byteValue = serializer.serialize(value);
            Long index = connection.sRem(byteKey, new byte[][]{byteValue});
            return index == null ? false : index > 0L;
        });
        return result;
    }

    protected boolean srem(String key, Serializable value) {
        return this.srem(key, value, false);
    }

    protected boolean srem(String key, Serializable value, boolean suppressTran) {
        boolean result = (Boolean)this.getRedisTemplate(suppressTran).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[] byteValue = CommonUtil.transObj2ByteArray(value);
            Long index = connection.sRem(byteKey, new byte[][]{byteValue});
            return index == null ? false : index > 0L;
        });
        return result;
    }

    protected boolean srem(String key, List<String> values) {
        return this.srem(key, values, false);
    }

    protected boolean srem(String key, List<String> values, boolean suppressTran) {
        boolean result = (Boolean)this.getRedisTemplate(suppressTran).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[][] byteValues = new byte[values.size()][];

            for(int i = 0; i < values.size(); ++i) {
                byteValues[i] = serializer.serialize(values.get(i));
            }

            connection.sRem(byteKey, byteValues);
            return true;
        });
        return result;
    }

    protected boolean zadd(String key, Double score, String value) {
        return this.zadd(key, score, value, false);
    }

    protected boolean zadd(String key, Double score, String value, boolean suppressTran) {
        boolean result = (Boolean)this.getRedisTemplate(suppressTran).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[] byteValue = serializer.serialize(value);
            connection.zAdd(byteKey, score, byteValue);
            return true;
        });
        return result;
    }

    protected boolean zrem(String key, String value) {
        return this.zrem(key, value, false);
    }

    protected boolean zrem(String key, String value, boolean suppressTran) {
        boolean result = (Boolean)this.getRedisTemplate(suppressTran).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[] byteValue = serializer.serialize(value);
            connection.zRem(byteKey, new byte[][]{byteValue});
            return true;
        });
        return result;
    }

    protected Object zrange(String key, Long begin, Long end) {
        Object result = this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            Set<byte[]> setValue = connection.zRange(byteKey, begin, end);
            Iterator<byte[]> iter = setValue.iterator();
            ArrayList listObj = new ArrayList();

            while(iter.hasNext()) {
                Object obj = serializer.deserialize((byte[])iter.next());
                listObj.add(obj);
            }

            return listObj;
        });
        return result;
    }

    protected Object zrangebyscore(String key, Double begin, Double end) {
        Object result = this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            Set setValue = connection.zRangeByScore(byteKey, begin, end);
            Iterator iter = setValue.iterator();
            ArrayList listObj = new ArrayList();

            while(iter.hasNext()) {
                Object obj = serializer.deserialize((byte[])((byte[])iter.next()));
                listObj.add(obj);
            }

            return listObj;
        });
        return result;
    }

    protected boolean hset(String key, String field, Serializable value, Long seconds) {
        return this.hset(key, field, value, seconds, false);
    }

    protected boolean hset(String key, String field, Serializable value, Long seconds, boolean suppressTran) {
        boolean result = (Boolean)this.getRedisTemplate(suppressTran).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[] byteField = serializer.serialize(field);
            byte[] byteValue = CommonUtil.transObj2ByteArray(value);
            connection.hSet(byteKey, byteField, byteValue);
            if (seconds.equals(0L)) {
                connection.persist(byteKey);
            } else {
                connection.expire(byteKey, seconds);
            }

            return true;
        });
        return result;
    }

    protected Object hget(String key, String field) {
        Object result = this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[] byteField = serializer.serialize(field);
            byte[] value = connection.hGet(byteKey, byteField);
            if (value == null) {
                this.logger.error("未找到redis中保存的数据" + new String(byteKey));
                return null;
            } else {
                Object dataObj = serializer.deserialize(value);
                return dataObj;
            }
        });
        return result;
    }

    protected <T extends Serializable> T hget(String key, String field, Class<T> clazz) {
        Serializable result = (Serializable)this.getRedisTemplate(true).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[] byteField = serializer.serialize(field);
            byte[] value = connection.hGet(byteKey, byteField);
            if (value == null) {
                return null;
            } else {
                T dataObj = CommonUtil.transByteArray2Obj(value, clazz);
                return dataObj;
            }
        });
        return (T)result;
    }

    protected Long hincrBy(String key, String field, Long incrBy) {
        return this.hincrBy(key, field, incrBy, false);
    }

    protected Long hincrBy(String key, String field, Long incrBy, boolean suppressTran) {
        Long result = (Long)this.getRedisTemplate(suppressTran).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[] byteField = serializer.serialize(field);
            Long value = connection.hIncrBy(byteKey, byteField, incrBy);
            return value == null ? 0L : value;
        });
        return result;
    }

    protected Double hincrFloatBy(String key, String field, Double incrBy) {
        return this.hincrFloatBy(key, field, incrBy, false);
    }

    protected Double hincrFloatBy(String key, String field, Double incrBy, boolean suppressTran) {
        Double result = (Double)this.getRedisTemplate(suppressTran).execute((RedisCallback<Object>) connection -> {
            RedisSerializer<String> serializer = this.getRedisTemplate(true).getStringSerializer();
            byte[] byteKey = serializer.serialize(key);
            byte[] byteField = serializer.serialize(field);
            Double value = connection.hIncrBy(byteKey, byteField, incrBy);
            return value == null ? 0.0D : value;
        });
        return result;
    }


    private RedisTemplate<K, V> getRedisTemplate(Boolean suppTrans) {
        this.redisTemplate.setEnableTransactionSupport(false);
        this.transRedisTemplate.setEnableTransactionSupport(true);
        return suppTrans ? this.redisTemplate : this.transRedisTemplate;
    }
}
