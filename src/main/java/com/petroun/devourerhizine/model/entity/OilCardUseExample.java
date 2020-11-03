package com.petroun.devourerhizine.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OilCardUseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public OilCardUseExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCardNoIsNull() {
            addCriterion("card_no is null");
            return (Criteria) this;
        }

        public Criteria andCardNoIsNotNull() {
            addCriterion("card_no is not null");
            return (Criteria) this;
        }

        public Criteria andCardNoEqualTo(String value) {
            addCriterion("card_no =", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotEqualTo(String value) {
            addCriterion("card_no <>", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThan(String value) {
            addCriterion("card_no >", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThanOrEqualTo(String value) {
            addCriterion("card_no >=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThan(String value) {
            addCriterion("card_no <", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThanOrEqualTo(String value) {
            addCriterion("card_no <=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLike(String value) {
            addCriterion("card_no like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotLike(String value) {
            addCriterion("card_no not like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoIn(List<String> values) {
            addCriterion("card_no in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotIn(List<String> values) {
            addCriterion("card_no not in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoBetween(String value1, String value2) {
            addCriterion("card_no between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotBetween(String value1, String value2) {
            addCriterion("card_no not between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andValidityTimeIsNull() {
            addCriterion("validity_time is null");
            return (Criteria) this;
        }

        public Criteria andValidityTimeIsNotNull() {
            addCriterion("validity_time is not null");
            return (Criteria) this;
        }

        public Criteria andValidityTimeEqualTo(Date value) {
            addCriterion("validity_time =", value, "validityTime");
            return (Criteria) this;
        }

        public Criteria andValidityTimeNotEqualTo(Date value) {
            addCriterion("validity_time <>", value, "validityTime");
            return (Criteria) this;
        }

        public Criteria andValidityTimeGreaterThan(Date value) {
            addCriterion("validity_time >", value, "validityTime");
            return (Criteria) this;
        }

        public Criteria andValidityTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("validity_time >=", value, "validityTime");
            return (Criteria) this;
        }

        public Criteria andValidityTimeLessThan(Date value) {
            addCriterion("validity_time <", value, "validityTime");
            return (Criteria) this;
        }

        public Criteria andValidityTimeLessThanOrEqualTo(Date value) {
            addCriterion("validity_time <=", value, "validityTime");
            return (Criteria) this;
        }

        public Criteria andValidityTimeIn(List<Date> values) {
            addCriterion("validity_time in", values, "validityTime");
            return (Criteria) this;
        }

        public Criteria andValidityTimeNotIn(List<Date> values) {
            addCriterion("validity_time not in", values, "validityTime");
            return (Criteria) this;
        }

        public Criteria andValidityTimeBetween(Date value1, Date value2) {
            addCriterion("validity_time between", value1, value2, "validityTime");
            return (Criteria) this;
        }

        public Criteria andValidityTimeNotBetween(Date value1, Date value2) {
            addCriterion("validity_time not between", value1, value2, "validityTime");
            return (Criteria) this;
        }

        public Criteria andBusinessIdIsNull() {
            addCriterion("business_id is null");
            return (Criteria) this;
        }

        public Criteria andBusinessIdIsNotNull() {
            addCriterion("business_id is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessIdEqualTo(String value) {
            addCriterion("business_id =", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotEqualTo(String value) {
            addCriterion("business_id <>", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdGreaterThan(String value) {
            addCriterion("business_id >", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdGreaterThanOrEqualTo(String value) {
            addCriterion("business_id >=", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdLessThan(String value) {
            addCriterion("business_id <", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdLessThanOrEqualTo(String value) {
            addCriterion("business_id <=", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdLike(String value) {
            addCriterion("business_id like", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotLike(String value) {
            addCriterion("business_id not like", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdIn(List<String> values) {
            addCriterion("business_id in", values, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotIn(List<String> values) {
            addCriterion("business_id not in", values, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdBetween(String value1, String value2) {
            addCriterion("business_id between", value1, value2, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotBetween(String value1, String value2) {
            addCriterion("business_id not between", value1, value2, "businessId");
            return (Criteria) this;
        }

        public Criteria andFlowidIsNull() {
            addCriterion("flowid is null");
            return (Criteria) this;
        }

        public Criteria andFlowidIsNotNull() {
            addCriterion("flowid is not null");
            return (Criteria) this;
        }

        public Criteria andFlowidEqualTo(String value) {
            addCriterion("flowid =", value, "flowid");
            return (Criteria) this;
        }

        public Criteria andFlowidNotEqualTo(String value) {
            addCriterion("flowid <>", value, "flowid");
            return (Criteria) this;
        }

        public Criteria andFlowidGreaterThan(String value) {
            addCriterion("flowid >", value, "flowid");
            return (Criteria) this;
        }

        public Criteria andFlowidGreaterThanOrEqualTo(String value) {
            addCriterion("flowid >=", value, "flowid");
            return (Criteria) this;
        }

        public Criteria andFlowidLessThan(String value) {
            addCriterion("flowid <", value, "flowid");
            return (Criteria) this;
        }

        public Criteria andFlowidLessThanOrEqualTo(String value) {
            addCriterion("flowid <=", value, "flowid");
            return (Criteria) this;
        }

        public Criteria andFlowidLike(String value) {
            addCriterion("flowid like", value, "flowid");
            return (Criteria) this;
        }

        public Criteria andFlowidNotLike(String value) {
            addCriterion("flowid not like", value, "flowid");
            return (Criteria) this;
        }

        public Criteria andFlowidIn(List<String> values) {
            addCriterion("flowid in", values, "flowid");
            return (Criteria) this;
        }

        public Criteria andFlowidNotIn(List<String> values) {
            addCriterion("flowid not in", values, "flowid");
            return (Criteria) this;
        }

        public Criteria andFlowidBetween(String value1, String value2) {
            addCriterion("flowid between", value1, value2, "flowid");
            return (Criteria) this;
        }

        public Criteria andFlowidNotBetween(String value1, String value2) {
            addCriterion("flowid not between", value1, value2, "flowid");
            return (Criteria) this;
        }

        public Criteria andFaceIsNull() {
            addCriterion("face is null");
            return (Criteria) this;
        }

        public Criteria andFaceIsNotNull() {
            addCriterion("face is not null");
            return (Criteria) this;
        }

        public Criteria andFaceEqualTo(Integer value) {
            addCriterion("face =", value, "face");
            return (Criteria) this;
        }

        public Criteria andFaceNotEqualTo(Integer value) {
            addCriterion("face <>", value, "face");
            return (Criteria) this;
        }

        public Criteria andFaceGreaterThan(Integer value) {
            addCriterion("face >", value, "face");
            return (Criteria) this;
        }

        public Criteria andFaceGreaterThanOrEqualTo(Integer value) {
            addCriterion("face >=", value, "face");
            return (Criteria) this;
        }

        public Criteria andFaceLessThan(Integer value) {
            addCriterion("face <", value, "face");
            return (Criteria) this;
        }

        public Criteria andFaceLessThanOrEqualTo(Integer value) {
            addCriterion("face <=", value, "face");
            return (Criteria) this;
        }

        public Criteria andFaceIn(List<Integer> values) {
            addCriterion("face in", values, "face");
            return (Criteria) this;
        }

        public Criteria andFaceNotIn(List<Integer> values) {
            addCriterion("face not in", values, "face");
            return (Criteria) this;
        }

        public Criteria andFaceBetween(Integer value1, Integer value2) {
            addCriterion("face between", value1, value2, "face");
            return (Criteria) this;
        }

        public Criteria andFaceNotBetween(Integer value1, Integer value2) {
            addCriterion("face not between", value1, value2, "face");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Integer value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Integer value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Integer value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Integer value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Integer value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Integer> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Integer> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Integer value1, Integer value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andMerchantIsNull() {
            addCriterion("merchant is null");
            return (Criteria) this;
        }

        public Criteria andMerchantIsNotNull() {
            addCriterion("merchant is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantEqualTo(String value) {
            addCriterion("merchant =", value, "merchant");
            return (Criteria) this;
        }

        public Criteria andMerchantNotEqualTo(String value) {
            addCriterion("merchant <>", value, "merchant");
            return (Criteria) this;
        }

        public Criteria andMerchantGreaterThan(String value) {
            addCriterion("merchant >", value, "merchant");
            return (Criteria) this;
        }

        public Criteria andMerchantGreaterThanOrEqualTo(String value) {
            addCriterion("merchant >=", value, "merchant");
            return (Criteria) this;
        }

        public Criteria andMerchantLessThan(String value) {
            addCriterion("merchant <", value, "merchant");
            return (Criteria) this;
        }

        public Criteria andMerchantLessThanOrEqualTo(String value) {
            addCriterion("merchant <=", value, "merchant");
            return (Criteria) this;
        }

        public Criteria andMerchantLike(String value) {
            addCriterion("merchant like", value, "merchant");
            return (Criteria) this;
        }

        public Criteria andMerchantNotLike(String value) {
            addCriterion("merchant not like", value, "merchant");
            return (Criteria) this;
        }

        public Criteria andMerchantIn(List<String> values) {
            addCriterion("merchant in", values, "merchant");
            return (Criteria) this;
        }

        public Criteria andMerchantNotIn(List<String> values) {
            addCriterion("merchant not in", values, "merchant");
            return (Criteria) this;
        }

        public Criteria andMerchantBetween(String value1, String value2) {
            addCriterion("merchant between", value1, value2, "merchant");
            return (Criteria) this;
        }

        public Criteria andMerchantNotBetween(String value1, String value2) {
            addCriterion("merchant not between", value1, value2, "merchant");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNull() {
            addCriterion("balance is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNotNull() {
            addCriterion("balance is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceEqualTo(Integer value) {
            addCriterion("balance =", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotEqualTo(Integer value) {
            addCriterion("balance <>", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThan(Integer value) {
            addCriterion("balance >", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThanOrEqualTo(Integer value) {
            addCriterion("balance >=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThan(Integer value) {
            addCriterion("balance <", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThanOrEqualTo(Integer value) {
            addCriterion("balance <=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceIn(List<Integer> values) {
            addCriterion("balance in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotIn(List<Integer> values) {
            addCriterion("balance not in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceBetween(Integer value1, Integer value2) {
            addCriterion("balance between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotBetween(Integer value1, Integer value2) {
            addCriterion("balance not between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andRiseIsNull() {
            addCriterion("rise is null");
            return (Criteria) this;
        }

        public Criteria andRiseIsNotNull() {
            addCriterion("rise is not null");
            return (Criteria) this;
        }

        public Criteria andRiseEqualTo(Integer value) {
            addCriterion("rise =", value, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseNotEqualTo(Integer value) {
            addCriterion("rise <>", value, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseGreaterThan(Integer value) {
            addCriterion("rise >", value, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseGreaterThanOrEqualTo(Integer value) {
            addCriterion("rise >=", value, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseLessThan(Integer value) {
            addCriterion("rise <", value, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseLessThanOrEqualTo(Integer value) {
            addCriterion("rise <=", value, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseIn(List<Integer> values) {
            addCriterion("rise in", values, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseNotIn(List<Integer> values) {
            addCriterion("rise not in", values, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseBetween(Integer value1, Integer value2) {
            addCriterion("rise between", value1, value2, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseNotBetween(Integer value1, Integer value2) {
            addCriterion("rise not between", value1, value2, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseAfterIsNull() {
            addCriterion("rise_after is null");
            return (Criteria) this;
        }

        public Criteria andRiseAfterIsNotNull() {
            addCriterion("rise_after is not null");
            return (Criteria) this;
        }

        public Criteria andRiseAfterEqualTo(Integer value) {
            addCriterion("rise_after =", value, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterNotEqualTo(Integer value) {
            addCriterion("rise_after <>", value, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterGreaterThan(Integer value) {
            addCriterion("rise_after >", value, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterGreaterThanOrEqualTo(Integer value) {
            addCriterion("rise_after >=", value, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterLessThan(Integer value) {
            addCriterion("rise_after <", value, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterLessThanOrEqualTo(Integer value) {
            addCriterion("rise_after <=", value, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterIn(List<Integer> values) {
            addCriterion("rise_after in", values, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterNotIn(List<Integer> values) {
            addCriterion("rise_after not in", values, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterBetween(Integer value1, Integer value2) {
            addCriterion("rise_after between", value1, value2, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterNotBetween(Integer value1, Integer value2) {
            addCriterion("rise_after not between", value1, value2, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andOilPriceIsNull() {
            addCriterion("oil_price is null");
            return (Criteria) this;
        }

        public Criteria andOilPriceIsNotNull() {
            addCriterion("oil_price is not null");
            return (Criteria) this;
        }

        public Criteria andOilPriceEqualTo(Integer value) {
            addCriterion("oil_price =", value, "oilPrice");
            return (Criteria) this;
        }

        public Criteria andOilPriceNotEqualTo(Integer value) {
            addCriterion("oil_price <>", value, "oilPrice");
            return (Criteria) this;
        }

        public Criteria andOilPriceGreaterThan(Integer value) {
            addCriterion("oil_price >", value, "oilPrice");
            return (Criteria) this;
        }

        public Criteria andOilPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("oil_price >=", value, "oilPrice");
            return (Criteria) this;
        }

        public Criteria andOilPriceLessThan(Integer value) {
            addCriterion("oil_price <", value, "oilPrice");
            return (Criteria) this;
        }

        public Criteria andOilPriceLessThanOrEqualTo(Integer value) {
            addCriterion("oil_price <=", value, "oilPrice");
            return (Criteria) this;
        }

        public Criteria andOilPriceIn(List<Integer> values) {
            addCriterion("oil_price in", values, "oilPrice");
            return (Criteria) this;
        }

        public Criteria andOilPriceNotIn(List<Integer> values) {
            addCriterion("oil_price not in", values, "oilPrice");
            return (Criteria) this;
        }

        public Criteria andOilPriceBetween(Integer value1, Integer value2) {
            addCriterion("oil_price between", value1, value2, "oilPrice");
            return (Criteria) this;
        }

        public Criteria andOilPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("oil_price not between", value1, value2, "oilPrice");
            return (Criteria) this;
        }

        public Criteria andTransactionTimeIsNull() {
            addCriterion("transaction_time is null");
            return (Criteria) this;
        }

        public Criteria andTransactionTimeIsNotNull() {
            addCriterion("transaction_time is not null");
            return (Criteria) this;
        }

        public Criteria andTransactionTimeEqualTo(Date value) {
            addCriterionForJDBCDate("transaction_time =", value, "transactionTime");
            return (Criteria) this;
        }

        public Criteria andTransactionTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("transaction_time <>", value, "transactionTime");
            return (Criteria) this;
        }

        public Criteria andTransactionTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("transaction_time >", value, "transactionTime");
            return (Criteria) this;
        }

        public Criteria andTransactionTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("transaction_time >=", value, "transactionTime");
            return (Criteria) this;
        }

        public Criteria andTransactionTimeLessThan(Date value) {
            addCriterionForJDBCDate("transaction_time <", value, "transactionTime");
            return (Criteria) this;
        }

        public Criteria andTransactionTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("transaction_time <=", value, "transactionTime");
            return (Criteria) this;
        }

        public Criteria andTransactionTimeIn(List<Date> values) {
            addCriterionForJDBCDate("transaction_time in", values, "transactionTime");
            return (Criteria) this;
        }

        public Criteria andTransactionTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("transaction_time not in", values, "transactionTime");
            return (Criteria) this;
        }

        public Criteria andTransactionTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("transaction_time between", value1, value2, "transactionTime");
            return (Criteria) this;
        }

        public Criteria andTransactionTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("transaction_time not between", value1, value2, "transactionTime");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNull() {
            addCriterion("created_at is null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNotNull() {
            addCriterion("created_at is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtEqualTo(Date value) {
            addCriterion("created_at =", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotEqualTo(Date value) {
            addCriterion("created_at <>", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThan(Date value) {
            addCriterion("created_at >", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("created_at >=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThan(Date value) {
            addCriterion("created_at <", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThanOrEqualTo(Date value) {
            addCriterion("created_at <=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIn(List<Date> values) {
            addCriterion("created_at in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotIn(List<Date> values) {
            addCriterion("created_at not in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtBetween(Date value1, Date value2) {
            addCriterion("created_at between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotBetween(Date value1, Date value2) {
            addCriterion("created_at not between", value1, value2, "createdAt");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}