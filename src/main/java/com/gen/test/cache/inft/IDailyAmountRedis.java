package com.gen.test.cache.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.gen.test.entity.DailyAmount;


/**
 * 缓存接口类 IDailyAmountRedis
 * @author lijun
 */
public interface IDailyAmountRedis {

    /**
     * 获取DailyAmount的ID
     * @return
     */
    Long getDailyAmountId();

    /**
     * 在Spring容器初始化的时候，初始化该实体ID的最大值。
     * @return
     */
    void initMaxDailyAmountId();

    /**
     * 得到redis的key值
     * @return
     * @throws Exception
     */
    String getDailyAmountKey(final Long daId);

    /**
     * 把值存储到redis中
     * 1.Key-Value对象
     * 2.把key存储到一个set中,方便删除操作
     */
    String setDailyAmount2Redis(DailyAmount dailyAmount, long expire);

    /**
     * 从缓存中得到值
     * @param daId
     * @return
     */
    DailyAmount getDailyAmountByKey(final Long daId);

    /**
     * 存储分页数量
     * @param dailyAmount
     * @param count
     * @param expire
     * @return
     */
    String setDailyAmountCount2Redis(DailyAmount dailyAmount, Long count, long expire);

    /**
     * 获取分页数量
     * @param dailyAmount
     * @return
     */
    Long getDailyAmountCountFromRedis(DailyAmount dailyAmount);

    /**
     * 存储分页列表
     * @param dailyAmount
     * @param dailyAmountList
     * @param expire
     * @return
     */
    String setDailyAmountList2Redis(DailyAmount dailyAmount, ArrayList<DailyAmount> dailyAmountList, long expire);

    /**
     * 获取分页列表
     * @param dailyAmount
     * @return
     */
    List<DailyAmount> getDailyAmountListFromRedis(DailyAmount dailyAmount);

    /**
     * Example 存储分页数量
     * @param queryExample
     * @param count
     * @param expire
     * @return
     */
    String setDailyAmountExampleCount2Redis(QueryExample queryExample, Long count, long expire);

    /**
     * Example 获取分页数量
     * @param queryExample
     * @return
     */
    Long getDailyAmountExampleCountFromRedis(QueryExample queryExample);

    /**
     * Example 存储分页列表
     * @param queryExample
     * @param list
     * @param expire
     * @return
     */
    String setDailyAmountExampleList2Redis(QueryExample queryExample, ArrayList<DailyAmount> list, long expire);

    /**
     * Example 获取分页列表
     * @param queryExample
     * @return
     */
    List<DailyAmount> getDailyAmountExampleListFromRedis(QueryExample queryExample);

    /**
     * 删除redis中相关值
     */
    void clearAllDailyAmount();
}

