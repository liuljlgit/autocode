package com.gen.test.webentity;

import com.alibaba.fastjson.annotation.JSONType;
import com.cloud.common.utils.CommonUtil;
import com.gen.test.entity.DailyAmount;

@JSONType(includes = {
    DailyAmount.PROP_DA_ID,
	DailyAmount.PROP_ENTITY_ID,
	DailyAmount.PROP_DATE_TIME,
	DailyAmount.PROP_SETT_PROFIT,
	DailyAmount.PROP_EXPEND_PROFIT,
	DailyAmount.PROP_INCOME_PROFIT,
	DailyAmount.PROP_PROFIT_HOURS,
	DailyAmount.PROP_DEFICIT_HOURS,
	DailyAmount.PROP_MINUS_DEVIATION,
	DailyAmount.PROP_POSITIVE_DEVIATION,
	DailyAmount.PROP_STATUS,
	DailyAmount.PROP_CREATE_TIME,
	DailyAmount.PROP_STATUS_TIME
})
public class DailyAmountResp extends DailyAmount{

	public DailyAmountResp(DailyAmount dailyAmount){
		CommonUtil.copyPropertiesIgnoreNull(dailyAmount,this);
	}

    /*@Override
    @JSONField(format = DateUtil.DEFAUL_FORMAT)
    public Date getDateTime() {
        return super.getDateTime();
    }*/

}

