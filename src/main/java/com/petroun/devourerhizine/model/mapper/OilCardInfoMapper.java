package com.petroun.devourerhizine.model.mapper;

import com.petroun.devourerhizine.model.entity.OilCardInfo;
import com.petroun.devourerhizine.model.entity.OilCardInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OilCardInfoMapper {
    long countByExample(OilCardInfoExample example);

    int deleteByExample(OilCardInfoExample example);

    int deleteByPrimaryKey(String cardNo);

    int insert(OilCardInfo record);

    int insertSelective(OilCardInfo record);

    List<OilCardInfo> selectByExample(OilCardInfoExample example);

    OilCardInfo selectByPrimaryKey(String cardNo);

    int updateByExampleSelective(@Param("record") OilCardInfo record, @Param("example") OilCardInfoExample example);

    int updateByExample(@Param("record") OilCardInfo record, @Param("example") OilCardInfoExample example);

    int updateByPrimaryKeySelective(OilCardInfo record);

    int updateByPrimaryKey(OilCardInfo record);
}