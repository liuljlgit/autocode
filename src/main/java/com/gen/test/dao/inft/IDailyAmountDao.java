package com.gen.test.dao.inft;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.cloud.common.complexquery.QueryExample;
import com.gen.test.entity.DailyAmount;


/**
  * 接口类 IDailyAmountDao
  * @author lijun
  */
@Repository
public interface IDailyAmountDao {

    /**
     * 根据主键获取对象
     * @param daId
     * @return
     */
    DailyAmount loadDailyAmountByKey(@Param("daId") Long daId);

    /**
     * 新增对象
     * @param dailyAmount
     * @return
     */
    Integer insertDailyAmount(DailyAmount dailyAmount);

    /**
     * 批量新增对象
     * @param list
     */
    void insertDailyAmountList(List<DailyAmount> list);

    /**
     * 更新对象
     * @param dailyAmount
     * @return
     */
    Integer updateDailyAmount(DailyAmount dailyAmount);

    /**
     * 批量更新
     * @param list
     */
    void updateDailyAmountList(List<DailyAmount> list);

    /**
     * 删除对象
     * @param daId
     * @return
     */
    Integer deleteDailyAmountByKey(@Param("daId") Long daId);

    /**
     * 批量删除对象
     * @param list
     */
    void deleteDailyAmountList(List<DailyAmount> list);

    /**
     * 查询记录总数
     * @param dailyAmount
     * @return
     */
    Long getDailyAmountCount(DailyAmount dailyAmount);

    /**
     * 分页查询列表
     * @param dailyAmount
     * @return
     */
    List<DailyAmount> getDailyAmountList(DailyAmount dailyAmount);

    /**
     * 获取表的最大ID
     * @return
     */
     Long selectMaxDailyAmountId();

    /**
     * 查询记录总数
     * @param queryExample
     * @return
     */
     Long getDailyAmountCountByExample(QueryExample queryExample);

    /**
     * 分页查询列表
     * @param queryExample
     * @return
     */
     List<DailyAmount> getDailyAmountListByExample(QueryExample queryExample);
}

