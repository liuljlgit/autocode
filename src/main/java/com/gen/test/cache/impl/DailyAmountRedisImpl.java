package com.gen.test.cache.impl;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import org.springframework.data.redis.core.RedisCallback;
import com.cloud.common.redis.BaseRedis;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gen.test.entity.DailyAmount;
import com.gen.test.cache.inft.IDailyAmountRedis;
import com.gen.test.dao.inft.IDailyAmountDao;


/**
 * 缓存实现类 DailyAmountRedisImpl
 * @author lijun
 */
@Repository("dailyAmountRedis")
public class DailyAmountRedisImpl extends BaseRedis<String, DailyAmount> implements IDailyAmountRedis{

    private static final Logger logger = LoggerFactory.getLogger(DailyAmountRedisImpl.class);

    @Autowired
    private IDailyAmountDao dailyAmountDao;

    public final static String PRE_KEY = DailyAmount.class.getSimpleName();
    private final static String SET_DAILYAMOUNT_KEY = "SET:".concat(PRE_KEY);
    private final static String SEQ_DAILYAMOUNT_KEY = "SEQ:".concat(PRE_KEY);
    private final static String PAGE_QUERY_DAILYAMOUNT_KEY = "PAGE_QUERY:".concat(PRE_KEY);
    private final static String PAGE_COUNT_DAILYAMOUNT_KEY = "PAGE_COUNT:".concat(PRE_KEY);
    private final static String CUSTOM_DAILYAMOUNT_KEY = "CUSTOM:".concat(PRE_KEY);
    private final static Long START_DAILYAMOUNTID = 1000000L;

    /**
     * 获取DailyAmount的ID
     * @return
     */
    @Override
    public Long getDailyAmountId(){
        return redisTemplate.execute((RedisCallback<Long>) connection->{
            if ( !connection.exists(SEQ_DAILYAMOUNT_KEY.getBytes())){
                Long id = dailyAmountDao.selectMaxDailyAmountId();
                id = ( null == id || id == 0) ?  START_DAILYAMOUNTID +  Long.valueOf("1") : ++ id;
                if ( connection.setNX(SEQ_DAILYAMOUNT_KEY.getBytes(), String.valueOf(id).getBytes())){
                    return  id;
                }
            }
            return connection.incr(redisTemplate.getStringSerializer().serialize(SEQ_DAILYAMOUNT_KEY));
        });
    }

    /**
     * 在Spring容器初始化的时候，初始化该实体ID的最大值。
     * @return
     */
    @PostConstruct
    @Override
    public void initMaxDailyAmountId(){
        redisTemplate.execute((RedisCallback<Long>) connection->{
            Long id = 0L;
            if ( !connection.exists(SEQ_DAILYAMOUNT_KEY.getBytes())){
                id = dailyAmountDao.selectMaxDailyAmountId();
                if ( null == id || id == 0){
                    id = START_DAILYAMOUNTID;
                }
                connection.setNX(SEQ_DAILYAMOUNT_KEY.getBytes(), String.valueOf(id).getBytes());
            }
            return id;
        });
    }

    /**
     * 得到redis的key值
     * @return
     * @throws Exception
     */
    public String getDailyAmountKey(final Long daId){
        return PRE_KEY.concat(":").concat(String.valueOf(daId));
    }

    /**
     * 把值存储到redis中
     * 1.Key-Value对象
     * 2.把key存储到一个set中,方便删除操作
     */
    @Override
    public String setDailyAmount2Redis(DailyAmount dailyAmount, long expire){
        String primaryKey = getDailyAmountKey(dailyAmount.getDaId());
        sadd(SET_DAILYAMOUNT_KEY, primaryKey);
        set(primaryKey, dailyAmount, expire);
        return primaryKey ;
    }

    /**
     * 从缓存中得到值
     * @param daId
     * @return
     */
    @Override
    public DailyAmount getDailyAmountByKey(final Long daId){
        String primaryKey = getDailyAmountKey(daId);
        DailyAmount dailyAmount = get(primaryKey,DailyAmount.class);
        return dailyAmount;
    }

    /**
     * 存储分页数量
     * @param dailyAmount
     * @param count
     * @param expire
     * @return
     */
    @Override
    public String setDailyAmountCount2Redis(DailyAmount dailyAmount, Long count, long expire){
        String rsKey = CommonUtil.createRedisKey(dailyAmount,true,true);
        hset(PAGE_COUNT_DAILYAMOUNT_KEY,rsKey,count,expire);
        return rsKey ;
    }

    /**
     * 获取分页数量
     * @param dailyAmount
     * @return
     */
    @Override
    public Long getDailyAmountCountFromRedis(DailyAmount dailyAmount){
        String rsKey = CommonUtil.createRedisKey(dailyAmount,true,true);
        Long count = hget(PAGE_COUNT_DAILYAMOUNT_KEY, rsKey, Long.class);
        return count;
    }

    /**
     * 存储分页列表
     * @param dailyAmount
     * @param list
     * @param expire
     * @return
     */
    @Override
    public String setDailyAmountList2Redis(DailyAmount dailyAmount, ArrayList<DailyAmount> list, long expire){
        String rsKey = CommonUtil.createRedisKey(dailyAmount,true,false);
        hset(PAGE_QUERY_DAILYAMOUNT_KEY,rsKey,list,expire);
        return rsKey ;
    }

    /**
     * 获取分页列表
     * @param dailyAmount
     * @return
     */
    @Override
    public List<DailyAmount> getDailyAmountListFromRedis(DailyAmount dailyAmount){
        String rsKey = CommonUtil.createRedisKey(dailyAmount,true,false);
        ArrayList<DailyAmount> list = (ArrayList<DailyAmount>) hget(PAGE_QUERY_DAILYAMOUNT_KEY, rsKey, ArrayList.class);
        return list;
    }

    /**
     * Example 存储分页数量
     * @param queryExample
     * @param count
     * @param expire
     * @return
     */
    @Override
    public String setDailyAmountExampleCount2Redis(QueryExample queryExample, Long count, long expire){
        String rsKey = CommonUtil.createExampleRedisKey(JSONObject.parseObject(JSON.toJSONString(queryExample)),true,true);
        hset(PAGE_COUNT_DAILYAMOUNT_KEY,rsKey,count,expire);
        return rsKey ;
    }

    /**
     * Example 获取分页数量
     * @param queryExample
     * @return
     */
    @Override
    public Long getDailyAmountExampleCountFromRedis(QueryExample queryExample){
        String rsKey = CommonUtil.createExampleRedisKey(JSONObject.parseObject(JSON.toJSONString(queryExample)),true,true);
        Long count = hget(PAGE_COUNT_DAILYAMOUNT_KEY, rsKey, Long.class);
        return count;
    }

    /**
     * Example 存储分页列表
     * @param queryExample
     * @param list
     * @param expire
     * @return
     */
    @Override
    public String setDailyAmountExampleList2Redis(QueryExample queryExample, ArrayList<DailyAmount> list, long expire){
        String rsKey = CommonUtil.createExampleRedisKey(JSONObject.parseObject(JSON.toJSONString(queryExample)),true,false);
        hset(PAGE_QUERY_DAILYAMOUNT_KEY,rsKey,list,expire);
        return rsKey ;
    }

    /**
     * Example 获取分页列表
     * @param queryExample
     * @return
     */
    @Override
    public List<DailyAmount> getDailyAmountExampleListFromRedis(QueryExample queryExample){
        String rsKey = CommonUtil.createExampleRedisKey(JSONObject.parseObject(JSON.toJSONString(queryExample)),true,false);
        ArrayList<DailyAmount> list = (ArrayList<DailyAmount>) hget(PAGE_QUERY_DAILYAMOUNT_KEY, rsKey, ArrayList.class);
        return list;
    }

    /**
     * 删除redis中相关值
     */
    @Override
    public void clearAllDailyAmount() {
        @SuppressWarnings("unchecked")
        List<String> listKey = (List<String>)smembers(SET_DAILYAMOUNT_KEY) ;
        listKey.add(SET_DAILYAMOUNT_KEY) ;
        if(listKey.size() > 0){
            delete(listKey) ;
        }
        delete(PAGE_QUERY_DAILYAMOUNT_KEY,true);
        delete(PAGE_COUNT_DAILYAMOUNT_KEY,true);
        delete(CUSTOM_DAILYAMOUNT_KEY,true);
    }

}

