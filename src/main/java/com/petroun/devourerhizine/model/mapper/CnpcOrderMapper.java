package com.petroun.devourerhizine.model.mapper;

import com.petroun.devourerhizine.model.entity.CnpcOrder;
import com.petroun.devourerhizine.model.entity.CnpcOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CnpcOrderMapper {
    long countByExample(CnpcOrderExample example);

    int deleteByExample(CnpcOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CnpcOrder record);

    int insertSelective(CnpcOrder record);

    List<CnpcOrder> selectByExample(CnpcOrderExample example);

    CnpcOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CnpcOrder record, @Param("example") CnpcOrderExample example);

    int updateByExample(@Param("record") CnpcOrder record, @Param("example") CnpcOrderExample example);

    int updateByPrimaryKeySelective(CnpcOrder record);

    int updateByPrimaryKey(CnpcOrder record);
}