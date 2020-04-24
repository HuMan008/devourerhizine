package com.petroun.devourerhizine.model.mapper;

import com.petroun.devourerhizine.model.entity.CnpcOrderInvokeLog;
import com.petroun.devourerhizine.model.entity.CnpcOrderInvokeLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CnpcOrderInvokeLogMapper {
    long countByExample(CnpcOrderInvokeLogExample example);

    int deleteByExample(CnpcOrderInvokeLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CnpcOrderInvokeLog record);

    int insertSelective(CnpcOrderInvokeLog record);

    List<CnpcOrderInvokeLog> selectByExample(CnpcOrderInvokeLogExample example);

    CnpcOrderInvokeLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CnpcOrderInvokeLog record, @Param("example") CnpcOrderInvokeLogExample example);

    int updateByExample(@Param("record") CnpcOrderInvokeLog record, @Param("example") CnpcOrderInvokeLogExample example);

    int updateByPrimaryKeySelective(CnpcOrderInvokeLog record);

    int updateByPrimaryKey(CnpcOrderInvokeLog record);
}