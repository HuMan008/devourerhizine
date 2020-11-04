package com.petroun.devourerhizine.model.mapper;

import com.petroun.devourerhizine.model.entity.OilCardUse;
import com.petroun.devourerhizine.model.entity.OilCardUseExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OilCardUseMapper {
    long countByExample(OilCardUseExample example);

    int deleteByExample(OilCardUseExample example);

    int deleteByPrimaryKey(String id);

    int insert(OilCardUse record);

    int insertSelective(OilCardUse record);

    List<OilCardUse> selectByExample(OilCardUseExample example);

    OilCardUse selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OilCardUse record, @Param("example") OilCardUseExample example);

    int updateByExample(@Param("record") OilCardUse record, @Param("example") OilCardUseExample example);

    int updateByPrimaryKeySelective(OilCardUse record);

    int updateByPrimaryKey(OilCardUse record);
}