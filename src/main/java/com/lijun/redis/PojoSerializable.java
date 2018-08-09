package com.lijun.redis;

import com.lijun.autocode.util.CommonUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * 自定义redis序列化类2
 * @param <T>
 */
import java.io.Serializable;

public class PojoSerializable<T extends Serializable> implements RedisSerializer<T> {

    private Class<T> clazz;

    public PojoSerializable(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        return CommonUtil.transObj2ByteArray(t);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return CommonUtil.transByteArray2Obj(bytes, clazz);
    }

}
