package com.petroun.devourerhizine.model.mapper;

import com.petroun.devourerhizine.model.entity.OilMobileCardInfo;
import com.petroun.devourerhizine.model.entity.OilMobileCardInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OilMobileCardInfoMapper {
    long countByExample(OilMobileCardInfoExample example);

    int deleteByExample(OilMobileCardInfoExample example);

    int deleteByPrimaryKey(String mobile);

    int insert(OilMobileCardInfo record);

    int insertSelective(OilMobileCardInfo record);

    List<OilMobileCardInfo> selectByExample(OilMobileCardInfoExample example);

    OilMobileCardInfo selectByPrimaryKey(String mobile);

    int updateByExampleSelective(@Param("record") OilMobileCardInfo record, @Param("example") OilMobileCardInfoExample example);

    int updateByExample(@Param("record") OilMobileCardInfo record, @Param("example") OilMobileCardInfoExample example);

    int updateByPrimaryKeySelective(OilMobileCardInfo record);

    int updateByPrimaryKey(OilMobileCardInfo record);
}