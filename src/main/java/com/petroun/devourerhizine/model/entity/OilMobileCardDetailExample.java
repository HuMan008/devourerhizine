package com.petroun.devourerhizine.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OilMobileCardDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public OilMobileCardDetailExample() {
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

        public Criteria andCardTypeIsNull() {
            addCriterion("card_type is null");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNotNull() {
            addCriterion("card_type is not null");
            return (Criteria) this;
        }

        public Criteria andCardTypeEqualTo(Byte value) {
            addCriterion("card_type =", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotEqualTo(Byte value) {
            addCriterion("card_type <>", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThan(Byte value) {
            addCriterion("card_type >", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("card_type >=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThan(Byte value) {
            addCriterion("card_type <", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThanOrEqualTo(Byte value) {
            addCriterion("card_type <=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeIn(List<Byte> values) {
            addCriterion("card_type in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotIn(List<Byte> values) {
            addCriterion("card_type not in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeBetween(Byte value1, Byte value2) {
            addCriterion("card_type between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("card_type not between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andUserUidIsNull() {
            addCriterion("user_uid is null");
            return (Criteria) this;
        }

        public Criteria andUserUidIsNotNull() {
            addCriterion("user_uid is not null");
            return (Criteria) this;
        }

        public Criteria andUserUidEqualTo(String value) {
            addCriterion("user_uid =", value, "userUid");
            return (Criteria) this;
        }

        public Criteria andUserUidNotEqualTo(String value) {
            addCriterion("user_uid <>", value, "userUid");
            return (Criteria) this;
        }

        public Criteria andUserUidGreaterThan(String value) {
            addCriterion("user_uid >", value, "userUid");
            return (Criteria) this;
        }

        public Criteria andUserUidGreaterThanOrEqualTo(String value) {
            addCriterion("user_uid >=", value, "userUid");
            return (Criteria) this;
        }

        public Criteria andUserUidLessThan(String value) {
            addCriterion("user_uid <", value, "userUid");
            return (Criteria) this;
        }

        public Criteria andUserUidLessThanOrEqualTo(String value) {
            addCriterion("user_uid <=", value, "userUid");
            return (Criteria) this;
        }

        public Criteria andUserUidLike(String value) {
            addCriterion("user_uid like", value, "userUid");
            return (Criteria) this;
        }

        public Criteria andUserUidNotLike(String value) {
            addCriterion("user_uid not like", value, "userUid");
            return (Criteria) this;
        }

        public Criteria andUserUidIn(List<String> values) {
            addCriterion("user_uid in", values, "userUid");
            return (Criteria) this;
        }

        public Criteria andUserUidNotIn(List<String> values) {
            addCriterion("user_uid not in", values, "userUid");
            return (Criteria) this;
        }

        public Criteria andUserUidBetween(String value1, String value2) {
            addCriterion("user_uid between", value1, value2, "userUid");
            return (Criteria) this;
        }

        public Criteria andUserUidNotBetween(String value1, String value2) {
            addCriterion("user_uid not between", value1, value2, "userUid");
            return (Criteria) this;
        }

        public Criteria andOilNumberIsNull() {
            addCriterion("oil_number is null");
            return (Criteria) this;
        }

        public Criteria andOilNumberIsNotNull() {
            addCriterion("oil_number is not null");
            return (Criteria) this;
        }

        public Criteria andOilNumberEqualTo(String value) {
            addCriterion("oil_number =", value, "oilNumber");
            return (Criteria) this;
        }

        public Criteria andOilNumberNotEqualTo(String value) {
            addCriterion("oil_number <>", value, "oilNumber");
            return (Criteria) this;
        }

        public Criteria andOilNumberGreaterThan(String value) {
            addCriterion("oil_number >", value, "oilNumber");
            return (Criteria) this;
        }

        public Criteria andOilNumberGreaterThanOrEqualTo(String value) {
            addCriterion("oil_number >=", value, "oilNumber");
            return (Criteria) this;
        }

        public Criteria andOilNumberLessThan(String value) {
            addCriterion("oil_number <", value, "oilNumber");
            return (Criteria) this;
        }

        public Criteria andOilNumberLessThanOrEqualTo(String value) {
            addCriterion("oil_number <=", value, "oilNumber");
            return (Criteria) this;
        }

        public Criteria andOilNumberLike(String value) {
            addCriterion("oil_number like", value, "oilNumber");
            return (Criteria) this;
        }

        public Criteria andOilNumberNotLike(String value) {
            addCriterion("oil_number not like", value, "oilNumber");
            return (Criteria) this;
        }

        public Criteria andOilNumberIn(List<String> values) {
            addCriterion("oil_number in", values, "oilNumber");
            return (Criteria) this;
        }

        public Criteria andOilNumberNotIn(List<String> values) {
            addCriterion("oil_number not in", values, "oilNumber");
            return (Criteria) this;
        }

        public Criteria andOilNumberBetween(String value1, String value2) {
            addCriterion("oil_number between", value1, value2, "oilNumber");
            return (Criteria) this;
        }

        public Criteria andOilNumberNotBetween(String value1, String value2) {
            addCriterion("oil_number not between", value1, value2, "oilNumber");
            return (Criteria) this;
        }

        public Criteria andCardStatusIsNull() {
            addCriterion("card_status is null");
            return (Criteria) this;
        }

        public Criteria andCardStatusIsNotNull() {
            addCriterion("card_status is not null");
            return (Criteria) this;
        }

        public Criteria andCardStatusEqualTo(Byte value) {
            addCriterion("card_status =", value, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusNotEqualTo(Byte value) {
            addCriterion("card_status <>", value, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusGreaterThan(Byte value) {
            addCriterion("card_status >", value, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("card_status >=", value, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusLessThan(Byte value) {
            addCriterion("card_status <", value, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusLessThanOrEqualTo(Byte value) {
            addCriterion("card_status <=", value, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusIn(List<Byte> values) {
            addCriterion("card_status in", values, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusNotIn(List<Byte> values) {
            addCriterion("card_status not in", values, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusBetween(Byte value1, Byte value2) {
            addCriterion("card_status between", value1, value2, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("card_status not between", value1, value2, "cardStatus");
            return (Criteria) this;
        }

        public Criteria andCardBalanceIsNull() {
            addCriterion("card_balance is null");
            return (Criteria) this;
        }

        public Criteria andCardBalanceIsNotNull() {
            addCriterion("card_balance is not null");
            return (Criteria) this;
        }

        public Criteria andCardBalanceEqualTo(Integer value) {
            addCriterion("card_balance =", value, "cardBalance");
            return (Criteria) this;
        }

        public Criteria andCardBalanceNotEqualTo(Integer value) {
            addCriterion("card_balance <>", value, "cardBalance");
            return (Criteria) this;
        }

        public Criteria andCardBalanceGreaterThan(Integer value) {
            addCriterion("card_balance >", value, "cardBalance");
            return (Criteria) this;
        }

        public Criteria andCardBalanceGreaterThanOrEqualTo(Integer value) {
            addCriterion("card_balance >=", value, "cardBalance");
            return (Criteria) this;
        }

        public Criteria andCardBalanceLessThan(Integer value) {
            addCriterion("card_balance <", value, "cardBalance");
            return (Criteria) this;
        }

        public Criteria andCardBalanceLessThanOrEqualTo(Integer value) {
            addCriterion("card_balance <=", value, "cardBalance");
            return (Criteria) this;
        }

        public Criteria andCardBalanceIn(List<Integer> values) {
            addCriterion("card_balance in", values, "cardBalance");
            return (Criteria) this;
        }

        public Criteria andCardBalanceNotIn(List<Integer> values) {
            addCriterion("card_balance not in", values, "cardBalance");
            return (Criteria) this;
        }

        public Criteria andCardBalanceBetween(Integer value1, Integer value2) {
            addCriterion("card_balance between", value1, value2, "cardBalance");
            return (Criteria) this;
        }

        public Criteria andCardBalanceNotBetween(Integer value1, Integer value2) {
            addCriterion("card_balance not between", value1, value2, "cardBalance");
            return (Criteria) this;
        }

        public Criteria andPayBalanceIsNull() {
            addCriterion("pay_balance is null");
            return (Criteria) this;
        }

        public Criteria andPayBalanceIsNotNull() {
            addCriterion("pay_balance is not null");
            return (Criteria) this;
        }

        public Criteria andPayBalanceEqualTo(Integer value) {
            addCriterion("pay_balance =", value, "payBalance");
            return (Criteria) this;
        }

        public Criteria andPayBalanceNotEqualTo(Integer value) {
            addCriterion("pay_balance <>", value, "payBalance");
            return (Criteria) this;
        }

        public Criteria andPayBalanceGreaterThan(Integer value) {
            addCriterion("pay_balance >", value, "payBalance");
            return (Criteria) this;
        }

        public Criteria andPayBalanceGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_balance >=", value, "payBalance");
            return (Criteria) this;
        }

        public Criteria andPayBalanceLessThan(Integer value) {
            addCriterion("pay_balance <", value, "payBalance");
            return (Criteria) this;
        }

        public Criteria andPayBalanceLessThanOrEqualTo(Integer value) {
            addCriterion("pay_balance <=", value, "payBalance");
            return (Criteria) this;
        }

        public Criteria andPayBalanceIn(List<Integer> values) {
            addCriterion("pay_balance in", values, "payBalance");
            return (Criteria) this;
        }

        public Criteria andPayBalanceNotIn(List<Integer> values) {
            addCriterion("pay_balance not in", values, "payBalance");
            return (Criteria) this;
        }

        public Criteria andPayBalanceBetween(Integer value1, Integer value2) {
            addCriterion("pay_balance between", value1, value2, "payBalance");
            return (Criteria) this;
        }

        public Criteria andPayBalanceNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_balance not between", value1, value2, "payBalance");
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

        public Criteria andUpdatedAtIsNull() {
            addCriterion("updated_at is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNotNull() {
            addCriterion("updated_at is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtEqualTo(Date value) {
            addCriterion("updated_at =", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotEqualTo(Date value) {
            addCriterion("updated_at <>", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThan(Date value) {
            addCriterion("updated_at >", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("updated_at >=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThan(Date value) {
            addCriterion("updated_at <", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThanOrEqualTo(Date value) {
            addCriterion("updated_at <=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIn(List<Date> values) {
            addCriterion("updated_at in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotIn(List<Date> values) {
            addCriterion("updated_at not in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtBetween(Date value1, Date value2) {
            addCriterion("updated_at between", value1, value2, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotBetween(Date value1, Date value2) {
            addCriterion("updated_at not between", value1, value2, "updatedAt");
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