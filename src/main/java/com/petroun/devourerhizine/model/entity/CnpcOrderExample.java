package com.petroun.devourerhizine.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CnpcOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public CnpcOrderExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDirectorIsNull() {
            addCriterion("director is null");
            return (Criteria) this;
        }

        public Criteria andDirectorIsNotNull() {
            addCriterion("director is not null");
            return (Criteria) this;
        }

        public Criteria andDirectorEqualTo(Byte value) {
            addCriterion("director =", value, "director");
            return (Criteria) this;
        }

        public Criteria andDirectorNotEqualTo(Byte value) {
            addCriterion("director <>", value, "director");
            return (Criteria) this;
        }

        public Criteria andDirectorGreaterThan(Byte value) {
            addCriterion("director >", value, "director");
            return (Criteria) this;
        }

        public Criteria andDirectorGreaterThanOrEqualTo(Byte value) {
            addCriterion("director >=", value, "director");
            return (Criteria) this;
        }

        public Criteria andDirectorLessThan(Byte value) {
            addCriterion("director <", value, "director");
            return (Criteria) this;
        }

        public Criteria andDirectorLessThanOrEqualTo(Byte value) {
            addCriterion("director <=", value, "director");
            return (Criteria) this;
        }

        public Criteria andDirectorIn(List<Byte> values) {
            addCriterion("director in", values, "director");
            return (Criteria) this;
        }

        public Criteria andDirectorNotIn(List<Byte> values) {
            addCriterion("director not in", values, "director");
            return (Criteria) this;
        }

        public Criteria andDirectorBetween(Byte value1, Byte value2) {
            addCriterion("director between", value1, value2, "director");
            return (Criteria) this;
        }

        public Criteria andDirectorNotBetween(Byte value1, Byte value2) {
            addCriterion("director not between", value1, value2, "director");
            return (Criteria) this;
        }

        public Criteria andDirectorCardIsNull() {
            addCriterion("director_card is null");
            return (Criteria) this;
        }

        public Criteria andDirectorCardIsNotNull() {
            addCriterion("director_card is not null");
            return (Criteria) this;
        }

        public Criteria andDirectorCardEqualTo(String value) {
            addCriterion("director_card =", value, "directorCard");
            return (Criteria) this;
        }

        public Criteria andDirectorCardNotEqualTo(String value) {
            addCriterion("director_card <>", value, "directorCard");
            return (Criteria) this;
        }

        public Criteria andDirectorCardGreaterThan(String value) {
            addCriterion("director_card >", value, "directorCard");
            return (Criteria) this;
        }

        public Criteria andDirectorCardGreaterThanOrEqualTo(String value) {
            addCriterion("director_card >=", value, "directorCard");
            return (Criteria) this;
        }

        public Criteria andDirectorCardLessThan(String value) {
            addCriterion("director_card <", value, "directorCard");
            return (Criteria) this;
        }

        public Criteria andDirectorCardLessThanOrEqualTo(String value) {
            addCriterion("director_card <=", value, "directorCard");
            return (Criteria) this;
        }

        public Criteria andDirectorCardLike(String value) {
            addCriterion("director_card like", value, "directorCard");
            return (Criteria) this;
        }

        public Criteria andDirectorCardNotLike(String value) {
            addCriterion("director_card not like", value, "directorCard");
            return (Criteria) this;
        }

        public Criteria andDirectorCardIn(List<String> values) {
            addCriterion("director_card in", values, "directorCard");
            return (Criteria) this;
        }

        public Criteria andDirectorCardNotIn(List<String> values) {
            addCriterion("director_card not in", values, "directorCard");
            return (Criteria) this;
        }

        public Criteria andDirectorCardBetween(String value1, String value2) {
            addCriterion("director_card between", value1, value2, "directorCard");
            return (Criteria) this;
        }

        public Criteria andDirectorCardNotBetween(String value1, String value2) {
            addCriterion("director_card not between", value1, value2, "directorCard");
            return (Criteria) this;
        }

        public Criteria andDirectorOrderIsNull() {
            addCriterion("director_order is null");
            return (Criteria) this;
        }

        public Criteria andDirectorOrderIsNotNull() {
            addCriterion("director_order is not null");
            return (Criteria) this;
        }

        public Criteria andDirectorOrderEqualTo(String value) {
            addCriterion("director_order =", value, "directorOrder");
            return (Criteria) this;
        }

        public Criteria andDirectorOrderNotEqualTo(String value) {
            addCriterion("director_order <>", value, "directorOrder");
            return (Criteria) this;
        }

        public Criteria andDirectorOrderGreaterThan(String value) {
            addCriterion("director_order >", value, "directorOrder");
            return (Criteria) this;
        }

        public Criteria andDirectorOrderGreaterThanOrEqualTo(String value) {
            addCriterion("director_order >=", value, "directorOrder");
            return (Criteria) this;
        }

        public Criteria andDirectorOrderLessThan(String value) {
            addCriterion("director_order <", value, "directorOrder");
            return (Criteria) this;
        }

        public Criteria andDirectorOrderLessThanOrEqualTo(String value) {
            addCriterion("director_order <=", value, "directorOrder");
            return (Criteria) this;
        }

        public Criteria andDirectorOrderLike(String value) {
            addCriterion("director_order like", value, "directorOrder");
            return (Criteria) this;
        }

        public Criteria andDirectorOrderNotLike(String value) {
            addCriterion("director_order not like", value, "directorOrder");
            return (Criteria) this;
        }

        public Criteria andDirectorOrderIn(List<String> values) {
            addCriterion("director_order in", values, "directorOrder");
            return (Criteria) this;
        }

        public Criteria andDirectorOrderNotIn(List<String> values) {
            addCriterion("director_order not in", values, "directorOrder");
            return (Criteria) this;
        }

        public Criteria andDirectorOrderBetween(String value1, String value2) {
            addCriterion("director_order between", value1, value2, "directorOrder");
            return (Criteria) this;
        }

        public Criteria andDirectorOrderNotBetween(String value1, String value2) {
            addCriterion("director_order not between", value1, value2, "directorOrder");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Long value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Long value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Long value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Long value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Long value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Long value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Long> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Long> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Long value1, Long value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Long value1, Long value2) {
            addCriterion("uid not between", value1, value2, "uid");
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

        public Criteria andFeeIsNull() {
            addCriterion("fee is null");
            return (Criteria) this;
        }

        public Criteria andFeeIsNotNull() {
            addCriterion("fee is not null");
            return (Criteria) this;
        }

        public Criteria andFeeEqualTo(Integer value) {
            addCriterion("fee =", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotEqualTo(Integer value) {
            addCriterion("fee <>", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeGreaterThan(Integer value) {
            addCriterion("fee >", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeGreaterThanOrEqualTo(Integer value) {
            addCriterion("fee >=", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeLessThan(Integer value) {
            addCriterion("fee <", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeLessThanOrEqualTo(Integer value) {
            addCriterion("fee <=", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeIn(List<Integer> values) {
            addCriterion("fee in", values, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotIn(List<Integer> values) {
            addCriterion("fee not in", values, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeBetween(Integer value1, Integer value2) {
            addCriterion("fee between", value1, value2, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotBetween(Integer value1, Integer value2) {
            addCriterion("fee not between", value1, value2, "fee");
            return (Criteria) this;
        }

        public Criteria andPromoListIsNull() {
            addCriterion("promo_list is null");
            return (Criteria) this;
        }

        public Criteria andPromoListIsNotNull() {
            addCriterion("promo_list is not null");
            return (Criteria) this;
        }

        public Criteria andPromoListEqualTo(String value) {
            addCriterion("promo_list =", value, "promoList");
            return (Criteria) this;
        }

        public Criteria andPromoListNotEqualTo(String value) {
            addCriterion("promo_list <>", value, "promoList");
            return (Criteria) this;
        }

        public Criteria andPromoListGreaterThan(String value) {
            addCriterion("promo_list >", value, "promoList");
            return (Criteria) this;
        }

        public Criteria andPromoListGreaterThanOrEqualTo(String value) {
            addCriterion("promo_list >=", value, "promoList");
            return (Criteria) this;
        }

        public Criteria andPromoListLessThan(String value) {
            addCriterion("promo_list <", value, "promoList");
            return (Criteria) this;
        }

        public Criteria andPromoListLessThanOrEqualTo(String value) {
            addCriterion("promo_list <=", value, "promoList");
            return (Criteria) this;
        }

        public Criteria andPromoListLike(String value) {
            addCriterion("promo_list like", value, "promoList");
            return (Criteria) this;
        }

        public Criteria andPromoListNotLike(String value) {
            addCriterion("promo_list not like", value, "promoList");
            return (Criteria) this;
        }

        public Criteria andPromoListIn(List<String> values) {
            addCriterion("promo_list in", values, "promoList");
            return (Criteria) this;
        }

        public Criteria andPromoListNotIn(List<String> values) {
            addCriterion("promo_list not in", values, "promoList");
            return (Criteria) this;
        }

        public Criteria andPromoListBetween(String value1, String value2) {
            addCriterion("promo_list between", value1, value2, "promoList");
            return (Criteria) this;
        }

        public Criteria andPromoListNotBetween(String value1, String value2) {
            addCriterion("promo_list not between", value1, value2, "promoList");
            return (Criteria) this;
        }

        public Criteria andUflowIsNull() {
            addCriterion("uflow is null");
            return (Criteria) this;
        }

        public Criteria andUflowIsNotNull() {
            addCriterion("uflow is not null");
            return (Criteria) this;
        }

        public Criteria andUflowEqualTo(String value) {
            addCriterion("uflow =", value, "uflow");
            return (Criteria) this;
        }

        public Criteria andUflowNotEqualTo(String value) {
            addCriterion("uflow <>", value, "uflow");
            return (Criteria) this;
        }

        public Criteria andUflowGreaterThan(String value) {
            addCriterion("uflow >", value, "uflow");
            return (Criteria) this;
        }

        public Criteria andUflowGreaterThanOrEqualTo(String value) {
            addCriterion("uflow >=", value, "uflow");
            return (Criteria) this;
        }

        public Criteria andUflowLessThan(String value) {
            addCriterion("uflow <", value, "uflow");
            return (Criteria) this;
        }

        public Criteria andUflowLessThanOrEqualTo(String value) {
            addCriterion("uflow <=", value, "uflow");
            return (Criteria) this;
        }

        public Criteria andUflowLike(String value) {
            addCriterion("uflow like", value, "uflow");
            return (Criteria) this;
        }

        public Criteria andUflowNotLike(String value) {
            addCriterion("uflow not like", value, "uflow");
            return (Criteria) this;
        }

        public Criteria andUflowIn(List<String> values) {
            addCriterion("uflow in", values, "uflow");
            return (Criteria) this;
        }

        public Criteria andUflowNotIn(List<String> values) {
            addCriterion("uflow not in", values, "uflow");
            return (Criteria) this;
        }

        public Criteria andUflowBetween(String value1, String value2) {
            addCriterion("uflow between", value1, value2, "uflow");
            return (Criteria) this;
        }

        public Criteria andUflowNotBetween(String value1, String value2) {
            addCriterion("uflow not between", value1, value2, "uflow");
            return (Criteria) this;
        }

        public Criteria andUextraIsNull() {
            addCriterion("uextra is null");
            return (Criteria) this;
        }

        public Criteria andUextraIsNotNull() {
            addCriterion("uextra is not null");
            return (Criteria) this;
        }

        public Criteria andUextraEqualTo(String value) {
            addCriterion("uextra =", value, "uextra");
            return (Criteria) this;
        }

        public Criteria andUextraNotEqualTo(String value) {
            addCriterion("uextra <>", value, "uextra");
            return (Criteria) this;
        }

        public Criteria andUextraGreaterThan(String value) {
            addCriterion("uextra >", value, "uextra");
            return (Criteria) this;
        }

        public Criteria andUextraGreaterThanOrEqualTo(String value) {
            addCriterion("uextra >=", value, "uextra");
            return (Criteria) this;
        }

        public Criteria andUextraLessThan(String value) {
            addCriterion("uextra <", value, "uextra");
            return (Criteria) this;
        }

        public Criteria andUextraLessThanOrEqualTo(String value) {
            addCriterion("uextra <=", value, "uextra");
            return (Criteria) this;
        }

        public Criteria andUextraLike(String value) {
            addCriterion("uextra like", value, "uextra");
            return (Criteria) this;
        }

        public Criteria andUextraNotLike(String value) {
            addCriterion("uextra not like", value, "uextra");
            return (Criteria) this;
        }

        public Criteria andUextraIn(List<String> values) {
            addCriterion("uextra in", values, "uextra");
            return (Criteria) this;
        }

        public Criteria andUextraNotIn(List<String> values) {
            addCriterion("uextra not in", values, "uextra");
            return (Criteria) this;
        }

        public Criteria andUextraBetween(String value1, String value2) {
            addCriterion("uextra between", value1, value2, "uextra");
            return (Criteria) this;
        }

        public Criteria andUextraNotBetween(String value1, String value2) {
            addCriterion("uextra not between", value1, value2, "uextra");
            return (Criteria) this;
        }

        public Criteria andRhiFlowIsNull() {
            addCriterion("rhi_flow is null");
            return (Criteria) this;
        }

        public Criteria andRhiFlowIsNotNull() {
            addCriterion("rhi_flow is not null");
            return (Criteria) this;
        }

        public Criteria andRhiFlowEqualTo(Long value) {
            addCriterion("rhi_flow =", value, "rhiFlow");
            return (Criteria) this;
        }

        public Criteria andRhiFlowNotEqualTo(Long value) {
            addCriterion("rhi_flow <>", value, "rhiFlow");
            return (Criteria) this;
        }

        public Criteria andRhiFlowGreaterThan(Long value) {
            addCriterion("rhi_flow >", value, "rhiFlow");
            return (Criteria) this;
        }

        public Criteria andRhiFlowGreaterThanOrEqualTo(Long value) {
            addCriterion("rhi_flow >=", value, "rhiFlow");
            return (Criteria) this;
        }

        public Criteria andRhiFlowLessThan(Long value) {
            addCriterion("rhi_flow <", value, "rhiFlow");
            return (Criteria) this;
        }

        public Criteria andRhiFlowLessThanOrEqualTo(Long value) {
            addCriterion("rhi_flow <=", value, "rhiFlow");
            return (Criteria) this;
        }

        public Criteria andRhiFlowIn(List<Long> values) {
            addCriterion("rhi_flow in", values, "rhiFlow");
            return (Criteria) this;
        }

        public Criteria andRhiFlowNotIn(List<Long> values) {
            addCriterion("rhi_flow not in", values, "rhiFlow");
            return (Criteria) this;
        }

        public Criteria andRhiFlowBetween(Long value1, Long value2) {
            addCriterion("rhi_flow between", value1, value2, "rhiFlow");
            return (Criteria) this;
        }

        public Criteria andRhiFlowNotBetween(Long value1, Long value2) {
            addCriterion("rhi_flow not between", value1, value2, "rhiFlow");
            return (Criteria) this;
        }

        public Criteria andRhiThroughsIsNull() {
            addCriterion("rhi_throughs is null");
            return (Criteria) this;
        }

        public Criteria andRhiThroughsIsNotNull() {
            addCriterion("rhi_throughs is not null");
            return (Criteria) this;
        }

        public Criteria andRhiThroughsEqualTo(Integer value) {
            addCriterion("rhi_throughs =", value, "rhiThroughs");
            return (Criteria) this;
        }

        public Criteria andRhiThroughsNotEqualTo(Integer value) {
            addCriterion("rhi_throughs <>", value, "rhiThroughs");
            return (Criteria) this;
        }

        public Criteria andRhiThroughsGreaterThan(Integer value) {
            addCriterion("rhi_throughs >", value, "rhiThroughs");
            return (Criteria) this;
        }

        public Criteria andRhiThroughsGreaterThanOrEqualTo(Integer value) {
            addCriterion("rhi_throughs >=", value, "rhiThroughs");
            return (Criteria) this;
        }

        public Criteria andRhiThroughsLessThan(Integer value) {
            addCriterion("rhi_throughs <", value, "rhiThroughs");
            return (Criteria) this;
        }

        public Criteria andRhiThroughsLessThanOrEqualTo(Integer value) {
            addCriterion("rhi_throughs <=", value, "rhiThroughs");
            return (Criteria) this;
        }

        public Criteria andRhiThroughsIn(List<Integer> values) {
            addCriterion("rhi_throughs in", values, "rhiThroughs");
            return (Criteria) this;
        }

        public Criteria andRhiThroughsNotIn(List<Integer> values) {
            addCriterion("rhi_throughs not in", values, "rhiThroughs");
            return (Criteria) this;
        }

        public Criteria andRhiThroughsBetween(Integer value1, Integer value2) {
            addCriterion("rhi_throughs between", value1, value2, "rhiThroughs");
            return (Criteria) this;
        }

        public Criteria andRhiThroughsNotBetween(Integer value1, Integer value2) {
            addCriterion("rhi_throughs not between", value1, value2, "rhiThroughs");
            return (Criteria) this;
        }

        public Criteria andChannelIsNull() {
            addCriterion("channel is null");
            return (Criteria) this;
        }

        public Criteria andChannelIsNotNull() {
            addCriterion("channel is not null");
            return (Criteria) this;
        }

        public Criteria andChannelEqualTo(Integer value) {
            addCriterion("channel =", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotEqualTo(Integer value) {
            addCriterion("channel <>", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThan(Integer value) {
            addCriterion("channel >", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThanOrEqualTo(Integer value) {
            addCriterion("channel >=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThan(Integer value) {
            addCriterion("channel <", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThanOrEqualTo(Integer value) {
            addCriterion("channel <=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelIn(List<Integer> values) {
            addCriterion("channel in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotIn(List<Integer> values) {
            addCriterion("channel not in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelBetween(Integer value1, Integer value2) {
            addCriterion("channel between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotBetween(Integer value1, Integer value2) {
            addCriterion("channel not between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelOrderIsNull() {
            addCriterion("channel_order is null");
            return (Criteria) this;
        }

        public Criteria andChannelOrderIsNotNull() {
            addCriterion("channel_order is not null");
            return (Criteria) this;
        }

        public Criteria andChannelOrderEqualTo(String value) {
            addCriterion("channel_order =", value, "channelOrder");
            return (Criteria) this;
        }

        public Criteria andChannelOrderNotEqualTo(String value) {
            addCriterion("channel_order <>", value, "channelOrder");
            return (Criteria) this;
        }

        public Criteria andChannelOrderGreaterThan(String value) {
            addCriterion("channel_order >", value, "channelOrder");
            return (Criteria) this;
        }

        public Criteria andChannelOrderGreaterThanOrEqualTo(String value) {
            addCriterion("channel_order >=", value, "channelOrder");
            return (Criteria) this;
        }

        public Criteria andChannelOrderLessThan(String value) {
            addCriterion("channel_order <", value, "channelOrder");
            return (Criteria) this;
        }

        public Criteria andChannelOrderLessThanOrEqualTo(String value) {
            addCriterion("channel_order <=", value, "channelOrder");
            return (Criteria) this;
        }

        public Criteria andChannelOrderLike(String value) {
            addCriterion("channel_order like", value, "channelOrder");
            return (Criteria) this;
        }

        public Criteria andChannelOrderNotLike(String value) {
            addCriterion("channel_order not like", value, "channelOrder");
            return (Criteria) this;
        }

        public Criteria andChannelOrderIn(List<String> values) {
            addCriterion("channel_order in", values, "channelOrder");
            return (Criteria) this;
        }

        public Criteria andChannelOrderNotIn(List<String> values) {
            addCriterion("channel_order not in", values, "channelOrder");
            return (Criteria) this;
        }

        public Criteria andChannelOrderBetween(String value1, String value2) {
            addCriterion("channel_order between", value1, value2, "channelOrder");
            return (Criteria) this;
        }

        public Criteria andChannelOrderNotBetween(String value1, String value2) {
            addCriterion("channel_order not between", value1, value2, "channelOrder");
            return (Criteria) this;
        }

        public Criteria andChannelThroughsIsNull() {
            addCriterion("channel_throughs is null");
            return (Criteria) this;
        }

        public Criteria andChannelThroughsIsNotNull() {
            addCriterion("channel_throughs is not null");
            return (Criteria) this;
        }

        public Criteria andChannelThroughsEqualTo(Integer value) {
            addCriterion("channel_throughs =", value, "channelThroughs");
            return (Criteria) this;
        }

        public Criteria andChannelThroughsNotEqualTo(Integer value) {
            addCriterion("channel_throughs <>", value, "channelThroughs");
            return (Criteria) this;
        }

        public Criteria andChannelThroughsGreaterThan(Integer value) {
            addCriterion("channel_throughs >", value, "channelThroughs");
            return (Criteria) this;
        }

        public Criteria andChannelThroughsGreaterThanOrEqualTo(Integer value) {
            addCriterion("channel_throughs >=", value, "channelThroughs");
            return (Criteria) this;
        }

        public Criteria andChannelThroughsLessThan(Integer value) {
            addCriterion("channel_throughs <", value, "channelThroughs");
            return (Criteria) this;
        }

        public Criteria andChannelThroughsLessThanOrEqualTo(Integer value) {
            addCriterion("channel_throughs <=", value, "channelThroughs");
            return (Criteria) this;
        }

        public Criteria andChannelThroughsIn(List<Integer> values) {
            addCriterion("channel_throughs in", values, "channelThroughs");
            return (Criteria) this;
        }

        public Criteria andChannelThroughsNotIn(List<Integer> values) {
            addCriterion("channel_throughs not in", values, "channelThroughs");
            return (Criteria) this;
        }

        public Criteria andChannelThroughsBetween(Integer value1, Integer value2) {
            addCriterion("channel_throughs between", value1, value2, "channelThroughs");
            return (Criteria) this;
        }

        public Criteria andChannelThroughsNotBetween(Integer value1, Integer value2) {
            addCriterion("channel_throughs not between", value1, value2, "channelThroughs");
            return (Criteria) this;
        }

        public Criteria andInquiresIsNull() {
            addCriterion("inquires is null");
            return (Criteria) this;
        }

        public Criteria andInquiresIsNotNull() {
            addCriterion("inquires is not null");
            return (Criteria) this;
        }

        public Criteria andInquiresEqualTo(Integer value) {
            addCriterion("inquires =", value, "inquires");
            return (Criteria) this;
        }

        public Criteria andInquiresNotEqualTo(Integer value) {
            addCriterion("inquires <>", value, "inquires");
            return (Criteria) this;
        }

        public Criteria andInquiresGreaterThan(Integer value) {
            addCriterion("inquires >", value, "inquires");
            return (Criteria) this;
        }

        public Criteria andInquiresGreaterThanOrEqualTo(Integer value) {
            addCriterion("inquires >=", value, "inquires");
            return (Criteria) this;
        }

        public Criteria andInquiresLessThan(Integer value) {
            addCriterion("inquires <", value, "inquires");
            return (Criteria) this;
        }

        public Criteria andInquiresLessThanOrEqualTo(Integer value) {
            addCriterion("inquires <=", value, "inquires");
            return (Criteria) this;
        }

        public Criteria andInquiresIn(List<Integer> values) {
            addCriterion("inquires in", values, "inquires");
            return (Criteria) this;
        }

        public Criteria andInquiresNotIn(List<Integer> values) {
            addCriterion("inquires not in", values, "inquires");
            return (Criteria) this;
        }

        public Criteria andInquiresBetween(Integer value1, Integer value2) {
            addCriterion("inquires between", value1, value2, "inquires");
            return (Criteria) this;
        }

        public Criteria andInquiresNotBetween(Integer value1, Integer value2) {
            addCriterion("inquires not between", value1, value2, "inquires");
            return (Criteria) this;
        }

        public Criteria andThroughMaskIsNull() {
            addCriterion("through_mask is null");
            return (Criteria) this;
        }

        public Criteria andThroughMaskIsNotNull() {
            addCriterion("through_mask is not null");
            return (Criteria) this;
        }

        public Criteria andThroughMaskEqualTo(Integer value) {
            addCriterion("through_mask =", value, "throughMask");
            return (Criteria) this;
        }

        public Criteria andThroughMaskNotEqualTo(Integer value) {
            addCriterion("through_mask <>", value, "throughMask");
            return (Criteria) this;
        }

        public Criteria andThroughMaskGreaterThan(Integer value) {
            addCriterion("through_mask >", value, "throughMask");
            return (Criteria) this;
        }

        public Criteria andThroughMaskGreaterThanOrEqualTo(Integer value) {
            addCriterion("through_mask >=", value, "throughMask");
            return (Criteria) this;
        }

        public Criteria andThroughMaskLessThan(Integer value) {
            addCriterion("through_mask <", value, "throughMask");
            return (Criteria) this;
        }

        public Criteria andThroughMaskLessThanOrEqualTo(Integer value) {
            addCriterion("through_mask <=", value, "throughMask");
            return (Criteria) this;
        }

        public Criteria andThroughMaskIn(List<Integer> values) {
            addCriterion("through_mask in", values, "throughMask");
            return (Criteria) this;
        }

        public Criteria andThroughMaskNotIn(List<Integer> values) {
            addCriterion("through_mask not in", values, "throughMask");
            return (Criteria) this;
        }

        public Criteria andThroughMaskBetween(Integer value1, Integer value2) {
            addCriterion("through_mask between", value1, value2, "throughMask");
            return (Criteria) this;
        }

        public Criteria andThroughMaskNotBetween(Integer value1, Integer value2) {
            addCriterion("through_mask not between", value1, value2, "throughMask");
            return (Criteria) this;
        }

        public Criteria andRegainIsNull() {
            addCriterion("regain is null");
            return (Criteria) this;
        }

        public Criteria andRegainIsNotNull() {
            addCriterion("regain is not null");
            return (Criteria) this;
        }

        public Criteria andRegainEqualTo(Integer value) {
            addCriterion("regain =", value, "regain");
            return (Criteria) this;
        }

        public Criteria andRegainNotEqualTo(Integer value) {
            addCriterion("regain <>", value, "regain");
            return (Criteria) this;
        }

        public Criteria andRegainGreaterThan(Integer value) {
            addCriterion("regain >", value, "regain");
            return (Criteria) this;
        }

        public Criteria andRegainGreaterThanOrEqualTo(Integer value) {
            addCriterion("regain >=", value, "regain");
            return (Criteria) this;
        }

        public Criteria andRegainLessThan(Integer value) {
            addCriterion("regain <", value, "regain");
            return (Criteria) this;
        }

        public Criteria andRegainLessThanOrEqualTo(Integer value) {
            addCriterion("regain <=", value, "regain");
            return (Criteria) this;
        }

        public Criteria andRegainIn(List<Integer> values) {
            addCriterion("regain in", values, "regain");
            return (Criteria) this;
        }

        public Criteria andRegainNotIn(List<Integer> values) {
            addCriterion("regain not in", values, "regain");
            return (Criteria) this;
        }

        public Criteria andRegainBetween(Integer value1, Integer value2) {
            addCriterion("regain between", value1, value2, "regain");
            return (Criteria) this;
        }

        public Criteria andRegainNotBetween(Integer value1, Integer value2) {
            addCriterion("regain not between", value1, value2, "regain");
            return (Criteria) this;
        }

        public Criteria andRegainAtIsNull() {
            addCriterion("regain_at is null");
            return (Criteria) this;
        }

        public Criteria andRegainAtIsNotNull() {
            addCriterion("regain_at is not null");
            return (Criteria) this;
        }

        public Criteria andRegainAtEqualTo(Date value) {
            addCriterion("regain_at =", value, "regainAt");
            return (Criteria) this;
        }

        public Criteria andRegainAtNotEqualTo(Date value) {
            addCriterion("regain_at <>", value, "regainAt");
            return (Criteria) this;
        }

        public Criteria andRegainAtGreaterThan(Date value) {
            addCriterion("regain_at >", value, "regainAt");
            return (Criteria) this;
        }

        public Criteria andRegainAtGreaterThanOrEqualTo(Date value) {
            addCriterion("regain_at >=", value, "regainAt");
            return (Criteria) this;
        }

        public Criteria andRegainAtLessThan(Date value) {
            addCriterion("regain_at <", value, "regainAt");
            return (Criteria) this;
        }

        public Criteria andRegainAtLessThanOrEqualTo(Date value) {
            addCriterion("regain_at <=", value, "regainAt");
            return (Criteria) this;
        }

        public Criteria andRegainAtIn(List<Date> values) {
            addCriterion("regain_at in", values, "regainAt");
            return (Criteria) this;
        }

        public Criteria andRegainAtNotIn(List<Date> values) {
            addCriterion("regain_at not in", values, "regainAt");
            return (Criteria) this;
        }

        public Criteria andRegainAtBetween(Date value1, Date value2) {
            addCriterion("regain_at between", value1, value2, "regainAt");
            return (Criteria) this;
        }

        public Criteria andRegainAtNotBetween(Date value1, Date value2) {
            addCriterion("regain_at not between", value1, value2, "regainAt");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andFailureReasonIsNull() {
            addCriterion("failure_reason is null");
            return (Criteria) this;
        }

        public Criteria andFailureReasonIsNotNull() {
            addCriterion("failure_reason is not null");
            return (Criteria) this;
        }

        public Criteria andFailureReasonEqualTo(String value) {
            addCriterion("failure_reason =", value, "failureReason");
            return (Criteria) this;
        }

        public Criteria andFailureReasonNotEqualTo(String value) {
            addCriterion("failure_reason <>", value, "failureReason");
            return (Criteria) this;
        }

        public Criteria andFailureReasonGreaterThan(String value) {
            addCriterion("failure_reason >", value, "failureReason");
            return (Criteria) this;
        }

        public Criteria andFailureReasonGreaterThanOrEqualTo(String value) {
            addCriterion("failure_reason >=", value, "failureReason");
            return (Criteria) this;
        }

        public Criteria andFailureReasonLessThan(String value) {
            addCriterion("failure_reason <", value, "failureReason");
            return (Criteria) this;
        }

        public Criteria andFailureReasonLessThanOrEqualTo(String value) {
            addCriterion("failure_reason <=", value, "failureReason");
            return (Criteria) this;
        }

        public Criteria andFailureReasonLike(String value) {
            addCriterion("failure_reason like", value, "failureReason");
            return (Criteria) this;
        }

        public Criteria andFailureReasonNotLike(String value) {
            addCriterion("failure_reason not like", value, "failureReason");
            return (Criteria) this;
        }

        public Criteria andFailureReasonIn(List<String> values) {
            addCriterion("failure_reason in", values, "failureReason");
            return (Criteria) this;
        }

        public Criteria andFailureReasonNotIn(List<String> values) {
            addCriterion("failure_reason not in", values, "failureReason");
            return (Criteria) this;
        }

        public Criteria andFailureReasonBetween(String value1, String value2) {
            addCriterion("failure_reason between", value1, value2, "failureReason");
            return (Criteria) this;
        }

        public Criteria andFailureReasonNotBetween(String value1, String value2) {
            addCriterion("failure_reason not between", value1, value2, "failureReason");
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