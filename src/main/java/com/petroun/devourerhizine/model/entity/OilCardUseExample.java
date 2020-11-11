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

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
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

        public Criteria andCardMobileIsNull() {
            addCriterion("card_mobile is null");
            return (Criteria) this;
        }

        public Criteria andCardMobileIsNotNull() {
            addCriterion("card_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andCardMobileEqualTo(String value) {
            addCriterion("card_mobile =", value, "cardMobile");
            return (Criteria) this;
        }

        public Criteria andCardMobileNotEqualTo(String value) {
            addCriterion("card_mobile <>", value, "cardMobile");
            return (Criteria) this;
        }

        public Criteria andCardMobileGreaterThan(String value) {
            addCriterion("card_mobile >", value, "cardMobile");
            return (Criteria) this;
        }

        public Criteria andCardMobileGreaterThanOrEqualTo(String value) {
            addCriterion("card_mobile >=", value, "cardMobile");
            return (Criteria) this;
        }

        public Criteria andCardMobileLessThan(String value) {
            addCriterion("card_mobile <", value, "cardMobile");
            return (Criteria) this;
        }

        public Criteria andCardMobileLessThanOrEqualTo(String value) {
            addCriterion("card_mobile <=", value, "cardMobile");
            return (Criteria) this;
        }

        public Criteria andCardMobileLike(String value) {
            addCriterion("card_mobile like", value, "cardMobile");
            return (Criteria) this;
        }

        public Criteria andCardMobileNotLike(String value) {
            addCriterion("card_mobile not like", value, "cardMobile");
            return (Criteria) this;
        }

        public Criteria andCardMobileIn(List<String> values) {
            addCriterion("card_mobile in", values, "cardMobile");
            return (Criteria) this;
        }

        public Criteria andCardMobileNotIn(List<String> values) {
            addCriterion("card_mobile not in", values, "cardMobile");
            return (Criteria) this;
        }

        public Criteria andCardMobileBetween(String value1, String value2) {
            addCriterion("card_mobile between", value1, value2, "cardMobile");
            return (Criteria) this;
        }

        public Criteria andCardMobileNotBetween(String value1, String value2) {
            addCriterion("card_mobile not between", value1, value2, "cardMobile");
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

        public Criteria andQrcodeIsNull() {
            addCriterion("qrcode is null");
            return (Criteria) this;
        }

        public Criteria andQrcodeIsNotNull() {
            addCriterion("qrcode is not null");
            return (Criteria) this;
        }

        public Criteria andQrcodeEqualTo(String value) {
            addCriterion("qrcode =", value, "qrcode");
            return (Criteria) this;
        }

        public Criteria andQrcodeNotEqualTo(String value) {
            addCriterion("qrcode <>", value, "qrcode");
            return (Criteria) this;
        }

        public Criteria andQrcodeGreaterThan(String value) {
            addCriterion("qrcode >", value, "qrcode");
            return (Criteria) this;
        }

        public Criteria andQrcodeGreaterThanOrEqualTo(String value) {
            addCriterion("qrcode >=", value, "qrcode");
            return (Criteria) this;
        }

        public Criteria andQrcodeLessThan(String value) {
            addCriterion("qrcode <", value, "qrcode");
            return (Criteria) this;
        }

        public Criteria andQrcodeLessThanOrEqualTo(String value) {
            addCriterion("qrcode <=", value, "qrcode");
            return (Criteria) this;
        }

        public Criteria andQrcodeLike(String value) {
            addCriterion("qrcode like", value, "qrcode");
            return (Criteria) this;
        }

        public Criteria andQrcodeNotLike(String value) {
            addCriterion("qrcode not like", value, "qrcode");
            return (Criteria) this;
        }

        public Criteria andQrcodeIn(List<String> values) {
            addCriterion("qrcode in", values, "qrcode");
            return (Criteria) this;
        }

        public Criteria andQrcodeNotIn(List<String> values) {
            addCriterion("qrcode not in", values, "qrcode");
            return (Criteria) this;
        }

        public Criteria andQrcodeBetween(String value1, String value2) {
            addCriterion("qrcode between", value1, value2, "qrcode");
            return (Criteria) this;
        }

        public Criteria andQrcodeNotBetween(String value1, String value2) {
            addCriterion("qrcode not between", value1, value2, "qrcode");
            return (Criteria) this;
        }

        public Criteria andQrcodeAmountIsNull() {
            addCriterion("qrcode_amount is null");
            return (Criteria) this;
        }

        public Criteria andQrcodeAmountIsNotNull() {
            addCriterion("qrcode_amount is not null");
            return (Criteria) this;
        }

        public Criteria andQrcodeAmountEqualTo(Integer value) {
            addCriterion("qrcode_amount =", value, "qrcodeAmount");
            return (Criteria) this;
        }

        public Criteria andQrcodeAmountNotEqualTo(Integer value) {
            addCriterion("qrcode_amount <>", value, "qrcodeAmount");
            return (Criteria) this;
        }

        public Criteria andQrcodeAmountGreaterThan(Integer value) {
            addCriterion("qrcode_amount >", value, "qrcodeAmount");
            return (Criteria) this;
        }

        public Criteria andQrcodeAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("qrcode_amount >=", value, "qrcodeAmount");
            return (Criteria) this;
        }

        public Criteria andQrcodeAmountLessThan(Integer value) {
            addCriterion("qrcode_amount <", value, "qrcodeAmount");
            return (Criteria) this;
        }

        public Criteria andQrcodeAmountLessThanOrEqualTo(Integer value) {
            addCriterion("qrcode_amount <=", value, "qrcodeAmount");
            return (Criteria) this;
        }

        public Criteria andQrcodeAmountIn(List<Integer> values) {
            addCriterion("qrcode_amount in", values, "qrcodeAmount");
            return (Criteria) this;
        }

        public Criteria andQrcodeAmountNotIn(List<Integer> values) {
            addCriterion("qrcode_amount not in", values, "qrcodeAmount");
            return (Criteria) this;
        }

        public Criteria andQrcodeAmountBetween(Integer value1, Integer value2) {
            addCriterion("qrcode_amount between", value1, value2, "qrcodeAmount");
            return (Criteria) this;
        }

        public Criteria andQrcodeAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("qrcode_amount not between", value1, value2, "qrcodeAmount");
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

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSendUrlIsNull() {
            addCriterion("send_url is null");
            return (Criteria) this;
        }

        public Criteria andSendUrlIsNotNull() {
            addCriterion("send_url is not null");
            return (Criteria) this;
        }

        public Criteria andSendUrlEqualTo(String value) {
            addCriterion("send_url =", value, "sendUrl");
            return (Criteria) this;
        }

        public Criteria andSendUrlNotEqualTo(String value) {
            addCriterion("send_url <>", value, "sendUrl");
            return (Criteria) this;
        }

        public Criteria andSendUrlGreaterThan(String value) {
            addCriterion("send_url >", value, "sendUrl");
            return (Criteria) this;
        }

        public Criteria andSendUrlGreaterThanOrEqualTo(String value) {
            addCriterion("send_url >=", value, "sendUrl");
            return (Criteria) this;
        }

        public Criteria andSendUrlLessThan(String value) {
            addCriterion("send_url <", value, "sendUrl");
            return (Criteria) this;
        }

        public Criteria andSendUrlLessThanOrEqualTo(String value) {
            addCriterion("send_url <=", value, "sendUrl");
            return (Criteria) this;
        }

        public Criteria andSendUrlLike(String value) {
            addCriterion("send_url like", value, "sendUrl");
            return (Criteria) this;
        }

        public Criteria andSendUrlNotLike(String value) {
            addCriterion("send_url not like", value, "sendUrl");
            return (Criteria) this;
        }

        public Criteria andSendUrlIn(List<String> values) {
            addCriterion("send_url in", values, "sendUrl");
            return (Criteria) this;
        }

        public Criteria andSendUrlNotIn(List<String> values) {
            addCriterion("send_url not in", values, "sendUrl");
            return (Criteria) this;
        }

        public Criteria andSendUrlBetween(String value1, String value2) {
            addCriterion("send_url between", value1, value2, "sendUrl");
            return (Criteria) this;
        }

        public Criteria andSendUrlNotBetween(String value1, String value2) {
            addCriterion("send_url not between", value1, value2, "sendUrl");
            return (Criteria) this;
        }

        public Criteria andSendCountIsNull() {
            addCriterion("send_count is null");
            return (Criteria) this;
        }

        public Criteria andSendCountIsNotNull() {
            addCriterion("send_count is not null");
            return (Criteria) this;
        }

        public Criteria andSendCountEqualTo(Integer value) {
            addCriterion("send_count =", value, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountNotEqualTo(Integer value) {
            addCriterion("send_count <>", value, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountGreaterThan(Integer value) {
            addCriterion("send_count >", value, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_count >=", value, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountLessThan(Integer value) {
            addCriterion("send_count <", value, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountLessThanOrEqualTo(Integer value) {
            addCriterion("send_count <=", value, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountIn(List<Integer> values) {
            addCriterion("send_count in", values, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountNotIn(List<Integer> values) {
            addCriterion("send_count not in", values, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountBetween(Integer value1, Integer value2) {
            addCriterion("send_count between", value1, value2, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendCountNotBetween(Integer value1, Integer value2) {
            addCriterion("send_count not between", value1, value2, "sendCount");
            return (Criteria) this;
        }

        public Criteria andSendStatusIsNull() {
            addCriterion("send_status is null");
            return (Criteria) this;
        }

        public Criteria andSendStatusIsNotNull() {
            addCriterion("send_status is not null");
            return (Criteria) this;
        }

        public Criteria andSendStatusEqualTo(Byte value) {
            addCriterion("send_status =", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotEqualTo(Byte value) {
            addCriterion("send_status <>", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusGreaterThan(Byte value) {
            addCriterion("send_status >", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("send_status >=", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusLessThan(Byte value) {
            addCriterion("send_status <", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusLessThanOrEqualTo(Byte value) {
            addCriterion("send_status <=", value, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusIn(List<Byte> values) {
            addCriterion("send_status in", values, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotIn(List<Byte> values) {
            addCriterion("send_status not in", values, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusBetween(Byte value1, Byte value2) {
            addCriterion("send_status between", value1, value2, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andSendStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("send_status not between", value1, value2, "sendStatus");
            return (Criteria) this;
        }

        public Criteria andValiditySedIsNull() {
            addCriterion("validity_sed is null");
            return (Criteria) this;
        }

        public Criteria andValiditySedIsNotNull() {
            addCriterion("validity_sed is not null");
            return (Criteria) this;
        }

        public Criteria andValiditySedEqualTo(Integer value) {
            addCriterion("validity_sed =", value, "validitySed");
            return (Criteria) this;
        }

        public Criteria andValiditySedNotEqualTo(Integer value) {
            addCriterion("validity_sed <>", value, "validitySed");
            return (Criteria) this;
        }

        public Criteria andValiditySedGreaterThan(Integer value) {
            addCriterion("validity_sed >", value, "validitySed");
            return (Criteria) this;
        }

        public Criteria andValiditySedGreaterThanOrEqualTo(Integer value) {
            addCriterion("validity_sed >=", value, "validitySed");
            return (Criteria) this;
        }

        public Criteria andValiditySedLessThan(Integer value) {
            addCriterion("validity_sed <", value, "validitySed");
            return (Criteria) this;
        }

        public Criteria andValiditySedLessThanOrEqualTo(Integer value) {
            addCriterion("validity_sed <=", value, "validitySed");
            return (Criteria) this;
        }

        public Criteria andValiditySedIn(List<Integer> values) {
            addCriterion("validity_sed in", values, "validitySed");
            return (Criteria) this;
        }

        public Criteria andValiditySedNotIn(List<Integer> values) {
            addCriterion("validity_sed not in", values, "validitySed");
            return (Criteria) this;
        }

        public Criteria andValiditySedBetween(Integer value1, Integer value2) {
            addCriterion("validity_sed between", value1, value2, "validitySed");
            return (Criteria) this;
        }

        public Criteria andValiditySedNotBetween(Integer value1, Integer value2) {
            addCriterion("validity_sed not between", value1, value2, "validitySed");
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

        public Criteria andBusinessNameIsNull() {
            addCriterion("business_name is null");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIsNotNull() {
            addCriterion("business_name is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessNameEqualTo(String value) {
            addCriterion("business_name =", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotEqualTo(String value) {
            addCriterion("business_name <>", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameGreaterThan(String value) {
            addCriterion("business_name >", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameGreaterThanOrEqualTo(String value) {
            addCriterion("business_name >=", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLessThan(String value) {
            addCriterion("business_name <", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLessThanOrEqualTo(String value) {
            addCriterion("business_name <=", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLike(String value) {
            addCriterion("business_name like", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotLike(String value) {
            addCriterion("business_name not like", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIn(List<String> values) {
            addCriterion("business_name in", values, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotIn(List<String> values) {
            addCriterion("business_name not in", values, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameBetween(String value1, String value2) {
            addCriterion("business_name between", value1, value2, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotBetween(String value1, String value2) {
            addCriterion("business_name not between", value1, value2, "businessName");
            return (Criteria) this;
        }

        public Criteria andStationIsNull() {
            addCriterion("station is null");
            return (Criteria) this;
        }

        public Criteria andStationIsNotNull() {
            addCriterion("station is not null");
            return (Criteria) this;
        }

        public Criteria andStationEqualTo(String value) {
            addCriterion("station =", value, "station");
            return (Criteria) this;
        }

        public Criteria andStationNotEqualTo(String value) {
            addCriterion("station <>", value, "station");
            return (Criteria) this;
        }

        public Criteria andStationGreaterThan(String value) {
            addCriterion("station >", value, "station");
            return (Criteria) this;
        }

        public Criteria andStationGreaterThanOrEqualTo(String value) {
            addCriterion("station >=", value, "station");
            return (Criteria) this;
        }

        public Criteria andStationLessThan(String value) {
            addCriterion("station <", value, "station");
            return (Criteria) this;
        }

        public Criteria andStationLessThanOrEqualTo(String value) {
            addCriterion("station <=", value, "station");
            return (Criteria) this;
        }

        public Criteria andStationLike(String value) {
            addCriterion("station like", value, "station");
            return (Criteria) this;
        }

        public Criteria andStationNotLike(String value) {
            addCriterion("station not like", value, "station");
            return (Criteria) this;
        }

        public Criteria andStationIn(List<String> values) {
            addCriterion("station in", values, "station");
            return (Criteria) this;
        }

        public Criteria andStationNotIn(List<String> values) {
            addCriterion("station not in", values, "station");
            return (Criteria) this;
        }

        public Criteria andStationBetween(String value1, String value2) {
            addCriterion("station between", value1, value2, "station");
            return (Criteria) this;
        }

        public Criteria andStationNotBetween(String value1, String value2) {
            addCriterion("station not between", value1, value2, "station");
            return (Criteria) this;
        }

        public Criteria andStationNameIsNull() {
            addCriterion("station_name is null");
            return (Criteria) this;
        }

        public Criteria andStationNameIsNotNull() {
            addCriterion("station_name is not null");
            return (Criteria) this;
        }

        public Criteria andStationNameEqualTo(String value) {
            addCriterion("station_name =", value, "stationName");
            return (Criteria) this;
        }

        public Criteria andStationNameNotEqualTo(String value) {
            addCriterion("station_name <>", value, "stationName");
            return (Criteria) this;
        }

        public Criteria andStationNameGreaterThan(String value) {
            addCriterion("station_name >", value, "stationName");
            return (Criteria) this;
        }

        public Criteria andStationNameGreaterThanOrEqualTo(String value) {
            addCriterion("station_name >=", value, "stationName");
            return (Criteria) this;
        }

        public Criteria andStationNameLessThan(String value) {
            addCriterion("station_name <", value, "stationName");
            return (Criteria) this;
        }

        public Criteria andStationNameLessThanOrEqualTo(String value) {
            addCriterion("station_name <=", value, "stationName");
            return (Criteria) this;
        }

        public Criteria andStationNameLike(String value) {
            addCriterion("station_name like", value, "stationName");
            return (Criteria) this;
        }

        public Criteria andStationNameNotLike(String value) {
            addCriterion("station_name not like", value, "stationName");
            return (Criteria) this;
        }

        public Criteria andStationNameIn(List<String> values) {
            addCriterion("station_name in", values, "stationName");
            return (Criteria) this;
        }

        public Criteria andStationNameNotIn(List<String> values) {
            addCriterion("station_name not in", values, "stationName");
            return (Criteria) this;
        }

        public Criteria andStationNameBetween(String value1, String value2) {
            addCriterion("station_name between", value1, value2, "stationName");
            return (Criteria) this;
        }

        public Criteria andStationNameNotBetween(String value1, String value2) {
            addCriterion("station_name not between", value1, value2, "stationName");
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

        public Criteria andRiseEqualTo(String value) {
            addCriterion("rise =", value, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseNotEqualTo(String value) {
            addCriterion("rise <>", value, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseGreaterThan(String value) {
            addCriterion("rise >", value, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseGreaterThanOrEqualTo(String value) {
            addCriterion("rise >=", value, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseLessThan(String value) {
            addCriterion("rise <", value, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseLessThanOrEqualTo(String value) {
            addCriterion("rise <=", value, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseLike(String value) {
            addCriterion("rise like", value, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseNotLike(String value) {
            addCriterion("rise not like", value, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseIn(List<String> values) {
            addCriterion("rise in", values, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseNotIn(List<String> values) {
            addCriterion("rise not in", values, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseBetween(String value1, String value2) {
            addCriterion("rise between", value1, value2, "rise");
            return (Criteria) this;
        }

        public Criteria andRiseNotBetween(String value1, String value2) {
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

        public Criteria andRiseAfterEqualTo(String value) {
            addCriterion("rise_after =", value, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterNotEqualTo(String value) {
            addCriterion("rise_after <>", value, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterGreaterThan(String value) {
            addCriterion("rise_after >", value, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterGreaterThanOrEqualTo(String value) {
            addCriterion("rise_after >=", value, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterLessThan(String value) {
            addCriterion("rise_after <", value, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterLessThanOrEqualTo(String value) {
            addCriterion("rise_after <=", value, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterLike(String value) {
            addCriterion("rise_after like", value, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterNotLike(String value) {
            addCriterion("rise_after not like", value, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterIn(List<String> values) {
            addCriterion("rise_after in", values, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterNotIn(List<String> values) {
            addCriterion("rise_after not in", values, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterBetween(String value1, String value2) {
            addCriterion("rise_after between", value1, value2, "riseAfter");
            return (Criteria) this;
        }

        public Criteria andRiseAfterNotBetween(String value1, String value2) {
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