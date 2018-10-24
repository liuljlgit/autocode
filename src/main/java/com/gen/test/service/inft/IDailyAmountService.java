package com.gen.test.service.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.gen.test.entity.DailyAmount;


/**
 * IDailyAmountService service接口类
 * @author lijun
 */
public interface IDailyAmountService {

    /**
     * 根据主键获取对象
     * @param daId
     * @return
     * @throws Exception
     */
    DailyAmount loadDailyAmountByKey(Long daId) throws Exception;

    /**
     * 新增对象
     * @param dailyAmount
     * @return
     * @throws Exception
     */
    Integer insertDailyAmount(DailyAmount dailyAmount) throws Exception;

    /**
     * 批量新增对象
     * @param dailyAmountList
     * @throws Exception
     */
    void insertDailyAmountList(List<DailyAmount> dailyAmountList) throws Exception;

    /**
     * 更新对象
     * @param dailyAmount
     * @return
     * @throws Exception
     */
    Integer updateDailyAmount(DailyAmount dailyAmount) throws Exception;

    /**
     * 批量更新
     * @param dailyAmountList
     * @throws Exception
     */
    void updateDailyAmountList(List<DailyAmount> dailyAmountList) throws Exception;

    /**
     * 删除对象
     * @param daId
     * @return
     * @throws Exception
     */
    Integer deleteDailyAmountByKey(Long daId) throws Exception;

    /**
     * 批量删除对象
     * @param dailyAmountList
     * @throws Exception
     */
    void deleteDailyAmountList(List<DailyAmount> dailyAmountList) throws Exception;

    /**
     * 查询记录总数
     * @param dailyAmount
     * @param useCache
     * @return
     * @throws Exception
     */
    Long getDailyAmountCount(DailyAmount dailyAmount,Boolean useCache) throws Exception;

    /**
     * 分页查询列表
     * @param dailyAmount
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getDailyAmountList(DailyAmount dailyAmount,Boolean useCache) throws Exception;

    /**
     * 查询列表
     * @param dailyAmount
     * @param useCache
     * @return
     * @throws Exception
     */
    List<DailyAmount> findDailyAmountList(DailyAmount dailyAmount, Boolean useCache) throws Exception;

    /**
     * 查询总记录数
     * @param queryExample
     * @param useCache
     * @return
     * @throws Exception
     */
    Long getDailyAmountCountByExample(QueryExample queryExample,Boolean useCache) throws Exception;

    /**
     * 分页查询列表
     * @param queryExample
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getDailyAmountListByExample(QueryExample queryExample,Boolean useCache) throws Exception;

    /**
     * 保存记录
     * @param dailyAmount
     * @throws Exception
     */
     void saveDailyAmount(DailyAmount dailyAmount) throws Exception;

    /**
     * 批量保存记录
     * @param dailyAmountList
     * @throws Exception
     */
     void saveDailyAmountList(List<DailyAmount> dailyAmountList) throws Exception;
}

