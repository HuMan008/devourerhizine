package com.petroun.devourerhizine.model.mapper;

import com.petroun.devourerhizine.model.entity.OilMobileCardDetail;
import com.petroun.devourerhizine.model.entity.OilMobileCardDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OilMobileCardDetailMapper {
    long countByExample(OilMobileCardDetailExample example);

    int deleteByExample(OilMobileCardDetailExample example);

    int deleteByPrimaryKey(String cardNo);

    int insert(OilMobileCardDetail record);

    int insertSelective(OilMobileCardDetail record);

    List<OilMobileCardDetail> selectByExample(OilMobileCardDetailExample example);

    OilMobileCardDetail selectByPrimaryKey(String cardNo);

    int updateByExampleSelective(@Param("record") OilMobileCardDetail record, @Param("example") OilMobileCardDetailExample example);

    int updateByExample(@Param("record") OilMobileCardDetail record, @Param("example") OilMobileCardDetailExample example);

    int updateByPrimaryKeySelective(OilMobileCardDetail record);

    int updateByPrimaryKey(OilMobileCardDetail record);
}