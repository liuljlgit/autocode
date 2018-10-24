package com.gen.test.entity;

import com.cloud.common.simplequery.IntervalEntity;
import com.cloud.common.webcomm.PageEntity;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * DailyAmount 实体类
 * @author lijun
 */
public class DailyAmount extends PageEntity implements Serializable {
	/**
	* field comment: 日前报量管理ID 
	*/
	private Long daId;

	/**
	* field comment: 电企业ID：来源于cloud_sys.entity.entity_id 
	*/
	private Integer entityId;

	/**
	* field comment: 用电时间 
	*/
	private Date dateTime;

	/**
	* field comment: 用电时间 
	*/
	private transient Date dateTimeStart;

	/**
	* field comment: 用电时间 
	*/
	private transient Date dateTimeEnd;

	/**
	* field comment: 结算盈利 
	*/
	private BigDecimal settProfit;

	/**
	* field comment: 支出金额 
	*/
	private BigDecimal expendProfit;

	/**
	* field comment: 收入金额 
	*/
	private BigDecimal incomeProfit;

	/**
	* field comment: 盈利小时数 
	*/
	private Byte profitHours;

	/**
	* field comment: 亏损小时数 
	*/
	private Byte deficitHours;

	/**
	* field comment: 当天最大负偏差率(%) 
	*/
	private BigDecimal minusDeviation;

	/**
	* field comment: 当天最大正偏差率(%) 
	*/
	private BigDecimal positiveDeviation;

	/**
	* field comment: 状态 (0) 弃用 （1）正常 
	*/
	private Byte status;

	/**
	* field comment:  
	*/
	private Byte ce;

	/**
	* field comment: 创建时间 
	*/
	private Date createTime;

	/**
	* field comment: 创建时间 
	*/
	private transient Date createTimeStart;

	/**
	* field comment: 创建时间 
	*/
	private transient Date createTimeEnd;

	/**
	* field comment: 更新时间 
	*/
	private Date statusTime;

	/**
	* field comment: 更新时间 
	*/
	private transient Date statusTimeStart;

	/**
	* field comment: 更新时间 
	*/
	private transient Date statusTimeEnd;

	/**
	* field comment: order by 
	*/
	private transient String orderByClause;

	/**
	* field comment: and xxx in...列表 
	*/
	private transient List<IntervalEntity> inSql;

	/**
	* field comment: and xxx not in 列表 
	*/
	private transient List<IntervalEntity> notInSql;



	public static final transient String PROP_DA_ID = "daId";
	public static final transient String TABLE_DA_ID = "da_id";
	public static final transient String PROP_ENTITY_ID = "entityId";
	public static final transient String TABLE_ENTITY_ID = "entity_id";
	public static final transient String PROP_DATE_TIME = "dateTime";
	public static final transient String TABLE_DATE_TIME = "date_time";
	public static final transient String PROP_SETT_PROFIT = "settProfit";
	public static final transient String TABLE_SETT_PROFIT = "sett_profit";
	public static final transient String PROP_EXPEND_PROFIT = "expendProfit";
	public static final transient String TABLE_EXPEND_PROFIT = "expend_profit";
	public static final transient String PROP_INCOME_PROFIT = "incomeProfit";
	public static final transient String TABLE_INCOME_PROFIT = "income_profit";
	public static final transient String PROP_PROFIT_HOURS = "profitHours";
	public static final transient String TABLE_PROFIT_HOURS = "profit_hours";
	public static final transient String PROP_DEFICIT_HOURS = "deficitHours";
	public static final transient String TABLE_DEFICIT_HOURS = "deficit_hours";
	public static final transient String PROP_MINUS_DEVIATION = "minusDeviation";
	public static final transient String TABLE_MINUS_DEVIATION = "minus_deviation";
	public static final transient String PROP_POSITIVE_DEVIATION = "positiveDeviation";
	public static final transient String TABLE_POSITIVE_DEVIATION = "positive_deviation";
	public static final transient String PROP_STATUS = "status";
	public static final transient String TABLE_STATUS = "status";
	public static final transient String PROP_CE = "ce";
	public static final transient String TABLE_CE = "ce";
	public static final transient String PROP_CREATE_TIME = "createTime";
	public static final transient String TABLE_CREATE_TIME = "create_time";
	public static final transient String PROP_STATUS_TIME = "statusTime";
	public static final transient String TABLE_STATUS_TIME = "status_time";

	public Long getDaId() { return daId; }

	public void setDaId(Long daId) { this.daId = daId; }

	public Integer getEntityId() { return entityId; }

	public void setEntityId(Integer entityId) { this.entityId = entityId; }

	public Date getDateTime() { return dateTime; }

	public void setDateTime(Date dateTime) { this.dateTime = dateTime; }

	public Date getDateTimeStart() { return dateTimeStart; }

	public void setDateTimeStart(Date dateTimeStart) { this.dateTimeStart = dateTimeStart; }

	public Date getDateTimeEnd() { return dateTimeEnd; }

	public void setDateTimeEnd(Date dateTimeEnd) { this.dateTimeEnd = dateTimeEnd; }

	public BigDecimal getSettProfit() { return settProfit; }

	public void setSettProfit(BigDecimal settProfit) { this.settProfit = settProfit; }

	public BigDecimal getExpendProfit() { return expendProfit; }

	public void setExpendProfit(BigDecimal expendProfit) { this.expendProfit = expendProfit; }

	public BigDecimal getIncomeProfit() { return incomeProfit; }

	public void setIncomeProfit(BigDecimal incomeProfit) { this.incomeProfit = incomeProfit; }

	public Byte getProfitHours() { return profitHours; }

	public void setProfitHours(Byte profitHours) { this.profitHours = profitHours; }

	public Byte getDeficitHours() { return deficitHours; }

	public void setDeficitHours(Byte deficitHours) { this.deficitHours = deficitHours; }

	public BigDecimal getMinusDeviation() { return minusDeviation; }

	public void setMinusDeviation(BigDecimal minusDeviation) { this.minusDeviation = minusDeviation; }

	public BigDecimal getPositiveDeviation() { return positiveDeviation; }

	public void setPositiveDeviation(BigDecimal positiveDeviation) { this.positiveDeviation = positiveDeviation; }

	public Byte getStatus() { return status; }

	public void setStatus(Byte status) { this.status = status; }

	public Byte getCe() { return ce; }

	public void setCe(Byte ce) { this.ce = ce; }

	public Date getCreateTime() { return createTime; }

	public void setCreateTime(Date createTime) { this.createTime = createTime; }

	public Date getCreateTimeStart() { return createTimeStart; }

	public void setCreateTimeStart(Date createTimeStart) { this.createTimeStart = createTimeStart; }

	public Date getCreateTimeEnd() { return createTimeEnd; }

	public void setCreateTimeEnd(Date createTimeEnd) { this.createTimeEnd = createTimeEnd; }

	public Date getStatusTime() { return statusTime; }

	public void setStatusTime(Date statusTime) { this.statusTime = statusTime; }

	public Date getStatusTimeStart() { return statusTimeStart; }

	public void setStatusTimeStart(Date statusTimeStart) { this.statusTimeStart = statusTimeStart; }

	public Date getStatusTimeEnd() { return statusTimeEnd; }

	public void setStatusTimeEnd(Date statusTimeEnd) { this.statusTimeEnd = statusTimeEnd; }

	public String getOrderByClause() { return orderByClause; }

	public void setOrderByClause(String orderByClause) { this.orderByClause = orderByClause; }

	public List<IntervalEntity> getInSql() { return inSql; }

	public void setInSql(List<IntervalEntity> inSql) { this.inSql = inSql; }

	public List<IntervalEntity> getNotInSql() { return notInSql; }

	public void setNotInSql(List<IntervalEntity> notInSql) { this.notInSql = notInSql; }



    /* 增加in列表 */
    public void addInSql(String name,Object list) {
        if(CollectionUtils.isEmpty(inSql)){
            inSql = new ArrayList<>();
        }
        IntervalEntity intervalEntity = new IntervalEntity();
        intervalEntity.setName(name);
        /*此处进行排序,方便生成redisKey的值*/
        List collect = (List)((List) list).stream().sorted().collect(Collectors.toList());
        Collections.sort(collect);
        intervalEntity.setList(collect);
        inSql.add(intervalEntity);
        inSql = inSql.stream().sorted(Comparator.comparing(IntervalEntity::getName)).collect(Collectors.toList());
    }
    /* 增加not in列表 */
    public void addNotInSql(String name,Object list) {
        if(CollectionUtils.isEmpty(notInSql)){
            notInSql = new ArrayList<>();
        }
        IntervalEntity intervalEntity = new IntervalEntity();
        /*此处进行排序,方便生成redisKey的值*/
        List collect = (List)((List) list).stream().sorted().collect(Collectors.toList());
        Collections.sort(collect);
        intervalEntity.setList(collect);
        intervalEntity.setName(name);
        notInSql.add(intervalEntity);
        notInSql = notInSql.stream().sorted(Comparator.comparing(IntervalEntity::getName)).collect(Collectors.toList());
    }

}

