package com.gen.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloud.common.complexquery.QueryExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import org.springframework.util.CollectionUtils;
import org.springframework.cache.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.stream.Collectors;
import com.alibaba.fastjson.JSONObject;
import com.gen.test.service.inft.IDailyAmountService;
import com.gen.test.entity.DailyAmount;
import com.gen.test.cache.inft.IDailyAmountRedis;
import com.gen.test.dao.inft.IDailyAmountDao;


/**
 * IDailyAmountService service接口类
 * @author lijun
 */
@Service("dailyAmountService")
public class DailyAmountServiceImpl implements IDailyAmountService{

    private static final Logger logger = LoggerFactory.getLogger(DailyAmountServiceImpl.class);

    @Autowired
    private IDailyAmountDao dailyAmountDao;
    @Autowired
    private IDailyAmountRedis dailyAmountRedis;

    /**
     * 根据主键获取对象
     * @param daId
     * @return
     * @throws Exception
     */
    @Override
    public DailyAmount loadDailyAmountByKey(Long daId) throws Exception {
        if(Objects.isNull(daId)){
            throw new Exception("请输入要获取的数据的ID");
        }
        DailyAmount dailyAmount;
        dailyAmount = dailyAmountRedis.getDailyAmountByKey(daId);
        if(Objects.nonNull(dailyAmount)){
            logger.info("===> fetch daId = "+daId+" entity from redis");
            return dailyAmount;
        }
        logger.info("===> fetch daId = "+daId+" entity from database");
        dailyAmount = dailyAmountDao.loadDailyAmountByKey(daId);
        if(Objects.isNull(dailyAmount)){
            throw new Exception("没有符合条件的记录！") ;
        }
        dailyAmountRedis.setDailyAmount2Redis(dailyAmount,0L);
        return dailyAmount;
    }

    /**
     * 新增对象
     * @param dailyAmount
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertDailyAmount(DailyAmount dailyAmount) throws Exception {
        if(Objects.isNull(dailyAmount)){
            throw new Exception("插入对象不能为空");
        }
        Integer result =  dailyAmountDao.insertDailyAmount(dailyAmount);
        dailyAmountRedis.clearAllDailyAmount();
        return result;
    }

    /**
     * 批量新增对象
     * @param dailyAmountList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertDailyAmountList(List<DailyAmount> dailyAmountList) throws Exception {
        if(CollectionUtils.isEmpty(dailyAmountList)){
            throw new Exception("插入对象不能为空");
        }
        dailyAmountDao.insertDailyAmountList(dailyAmountList);
        dailyAmountRedis.clearAllDailyAmount();
    }

    /**
     * 更新对象
     * @param dailyAmount
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateDailyAmount(DailyAmount dailyAmount) throws Exception {
        if(Objects.isNull(dailyAmount)){
            throw new Exception("更新对象不能为空");
        }
        Integer result = dailyAmountDao.updateDailyAmount(dailyAmount);
        dailyAmountRedis.clearAllDailyAmount();
        return result;
    }

    /**
     * 批量更新
     * @param dailyAmountList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDailyAmountList(List<DailyAmount> dailyAmountList) throws Exception {
        if(CollectionUtils.isEmpty(dailyAmountList)){
            throw new Exception("更新对象不能为空");
        }
        dailyAmountDao.updateDailyAmountList(dailyAmountList);
        dailyAmountRedis.clearAllDailyAmount();
    }

    /**
     * 删除对象
     * @param daId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteDailyAmountByKey(Long daId) throws Exception {
        if(Objects.isNull(daId)){
            throw new Exception("请输入要删除的数据的ID");
        }
        Integer result = dailyAmountDao.deleteDailyAmountByKey(daId);
        dailyAmountRedis.clearAllDailyAmount();
        return result;
    }

    /**
     * 批量删除对象
     * @param dailyAmountList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDailyAmountList(List<DailyAmount> dailyAmountList) throws Exception {
        if(CollectionUtils.isEmpty(dailyAmountList)){
            throw new Exception("请输入要删除的数据的ID列表");
        }
        dailyAmountDao.deleteDailyAmountList(dailyAmountList);
        dailyAmountRedis.clearAllDailyAmount();
    }

    /**
     * 查询记录总数
     * @param dailyAmount
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public Long getDailyAmountCount(DailyAmount dailyAmount,Boolean useCache) throws Exception{
        if(Objects.isNull(dailyAmount)){
            throw new Exception("请求参数不能为空");
        }
        Long count;
        if(useCache){
            count = dailyAmountRedis.getDailyAmountCountFromRedis(dailyAmount);
            if(Objects.nonNull(count)){
                logger.info("===> fetch count = "+count+" entity from redis");
                return count;
            }
            logger.info("===> fetch count value from database");
            count = dailyAmountDao.getDailyAmountCount(dailyAmount);
            dailyAmountRedis.setDailyAmountCount2Redis(dailyAmount,count,0L);
        }else{
            count = dailyAmountDao.getDailyAmountCount(dailyAmount);
        }
        return count;
    }

    /**
     * 分页查询列表
     * @param dailyAmount
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getDailyAmountList(DailyAmount dailyAmount,Boolean useCache) throws Exception{
        if(Objects.isNull(dailyAmount)){
            throw new Exception("请求参数不能为空");
        }
        JSONObject resp = new JSONObject();
        List<DailyAmount> list;
        if(-1 != dailyAmount.getPage()){
            Long total = getDailyAmountCount(dailyAmount, useCache);
            dailyAmount.setTotal(total.intValue());
            resp.put("total",dailyAmount.getTotal());
            resp.put("totalPage",dailyAmount.getTotalPage());
        }
        if(useCache){
            list = dailyAmountRedis.getDailyAmountListFromRedis(dailyAmount);
            if(Objects.nonNull(list)){
                logger.info("===> fetch page list from redis");
                resp.put("list",list);
                return resp;
            }
            logger.info("===> fetch page list from database");
            list = dailyAmountDao.getDailyAmountList(dailyAmount);
            resp.put("list",list);
            dailyAmountRedis.setDailyAmountList2Redis(dailyAmount,new ArrayList<>(list),0L);
        }else{
            logger.info("===> fetch page list from database");
            list = dailyAmountDao.getDailyAmountList(dailyAmount);
            resp.put("list",list);
        }
        return resp;
    }

    /**
     * 查询列表
     * @param dailyAmount
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public List<DailyAmount> findDailyAmountList(DailyAmount dailyAmount, Boolean useCache) throws Exception{
        JSONObject dailyAmountList = getDailyAmountList(dailyAmount, useCache);
        List<DailyAmount> list = JSONObject.parseArray(dailyAmountList.getJSONArray("list").toJSONString(), DailyAmount.class);
        if(CollectionUtils.isEmpty(list)){
            return Collections.EMPTY_LIST;
        }
        return list;
    }

    /**
     * 查询总记录数
     * @param queryExample
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public Long getDailyAmountCountByExample(QueryExample queryExample,Boolean useCache) throws Exception {
        if(Objects.isNull(queryExample)){
            throw new Exception("请求参数不能为空");
        }
        Long count;
        if(useCache){
            count = dailyAmountRedis.getDailyAmountExampleCountFromRedis(queryExample);
            if(Objects.nonNull(count)){
                logger.info("===> fetch count = "+count+" entity from redis");
                return count;
            }
            logger.info("===> fetch count value from database");
            count = dailyAmountDao.getDailyAmountCountByExample(queryExample);
            dailyAmountRedis.setDailyAmountExampleCount2Redis(queryExample,count,0L);
        }else{
            count = dailyAmountDao.getDailyAmountCountByExample(queryExample);
        }
        return count;
    }

    /**
     * 分页查询列表
     * @param queryExample
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getDailyAmountListByExample(QueryExample queryExample,Boolean useCache) throws Exception {
        if(Objects.isNull(queryExample)){
            throw new Exception("请求参数不能为空");
        }
        JSONObject resp = new JSONObject();
        List<DailyAmount> list;
        if(Objects.nonNull(queryExample.getIndex()) && Objects.nonNull(queryExample.getPageSize())){
            Long total = getDailyAmountCountByExample(queryExample, useCache);
            resp.put("total",total);
            resp.put("totalPage",(total-1)/queryExample.getPageSize()+1);
        }
        if(useCache){
            list = dailyAmountRedis.getDailyAmountExampleListFromRedis(queryExample);
            if(Objects.nonNull(list)){
                logger.info("===> fetch page list from redis");
                resp.put("list",list);
                return resp;
            }
            logger.info("===> fetch page list from database");
            list = dailyAmountDao.getDailyAmountListByExample(queryExample);
            resp.put("list",list);
            dailyAmountRedis.setDailyAmountExampleList2Redis(queryExample,new ArrayList<>(list),0L);
        }else{
            logger.info("===> fetch page list from database");
            list = dailyAmountDao.getDailyAmountListByExample(queryExample);
            resp.put("list",list);
        }
        return resp;
    }

    /**
     * 保存记录
     * @param dailyAmount
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDailyAmount(DailyAmount dailyAmount) throws Exception {
        if(Objects.isNull(dailyAmount)){
           throw new Exception("请求参数不能为空");
        }
        if(Objects.isNull(dailyAmount.getDaId())){
            dailyAmount.setDaId(dailyAmountRedis.getDailyAmountId());
            insertDailyAmount(dailyAmount);
        }else{
            updateDailyAmount(dailyAmount);
        }
    }

    /**
     * 批量保存记录
     * @param dailyAmountList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDailyAmountList(List<DailyAmount> dailyAmountList) throws Exception {
        if(CollectionUtils.isEmpty(dailyAmountList)){
            return ;
        }
        List<DailyAmount> insertList = dailyAmountList.stream().filter(e -> Objects.isNull(e.getDaId())).collect(Collectors.toList());
        List<DailyAmount> updateList = dailyAmountList.stream().filter(e -> !Objects.isNull(e.getDaId())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(insertList)){
            insertList = insertList.stream().map(e->{
                e.setDaId(dailyAmountRedis.getDailyAmountId());
                return e;
            }).collect(Collectors.toList());
            insertDailyAmountList(insertList);
        }
        if(!CollectionUtils.isEmpty(updateList)){
            updateDailyAmountList(updateList);
        }
    }
}

