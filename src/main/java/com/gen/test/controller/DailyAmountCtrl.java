package com.gen.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.base.BaseController;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.common.webcomm.ReqEntity;
import com.gen.test.entity.DailyAmount;
import com.gen.test.service.inft.IDailyAmountService;
import com.gen.test.webentity.DailyAmountResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


/**
 * DailyAmountCtrl 控制层方法
 * @author lijun
 */
@RestController
public class DailyAmountCtrl extends BaseController {

    @Autowired
    private IDailyAmountService dailyAmountService;

   /**
   * DailyAmount 根据主键获取单个数据
   * @return
   * @throws Exception
   */
   @GetMapping(value = "/dailyAmount/{daId}")
   public String getDailyAmount(@PathVariable(value="daId") Long daId) throws Exception {
      if(Objects.isNull(daId)){
         throw new Exception("请输入要获取的数据的ID") ;
      }
      DailyAmount dailyAmount = dailyAmountService.loadDailyAmountByKey(daId);
      JSONObject resp = new JSONObject();
      resp.put("dailyAmount",new DailyAmountResp(dailyAmount));
      return formatResponseParams(EXEC_OK,resp);
   }

  /**
   * DailyAmount 根据实体对象查询列表
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/dailyAmount/list")
   public String getDailyAmountList(@RequestBody ReqEntity reqEntity) throws Exception {
       DailyAmount dailyAmount = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), DailyAmount.class);
       JSONObject resp = dailyAmountService.getDailyAmountList(dailyAmount,true);
       return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * 复杂查询
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/dailyAmount/criteria/list")
   public String getDailyAmountListByExample(@RequestBody ReqEntity reqEntity) throws Exception {
       DailyAmount dailyAmount = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), DailyAmount.class);
       QueryExample queryExample = new QueryExample();
       JSONObject resp = dailyAmountService.getDailyAmountListByExample(queryExample,true);
       return formatResponseParams(EXEC_OK, resp);
   }

  /**
   * DailyAmount 新增或者修改记录，根据主键判断，主键为空则新增，否则修改。
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/dailyAmount")
   public String saveDailyAmount(@RequestBody ReqEntity reqEntity) throws  Exception{
       DailyAmount dailyAmount = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), DailyAmount.class);
       dailyAmountService.saveDailyAmount(dailyAmount);
       return formatResponseParams(EXEC_OK,null);
   }

   /**
    * DailyAmount 根据主键删除数据
    * @return
    * @throws Exception
    */
    @DeleteMapping(value = "/dailyAmount/{daId}")
    public String deleteDailyAmount(@PathVariable(value="daId") Long daId) throws  Exception{
        if(Objects.isNull(daId)){
           throw new Exception("入参请求异常") ;
        }
        dailyAmountService.deleteDailyAmountByKey(daId);
        return formatResponseParams(EXEC_OK,null);
    }


}

