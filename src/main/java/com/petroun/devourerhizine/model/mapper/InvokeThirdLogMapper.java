package com.petroun.devourerhizine.model.mapper;

import com.petroun.devourerhizine.model.entity.InvokeThirdLog;
import com.petroun.devourerhizine.model.entity.InvokeThirdLogExample;
import com.petroun.devourerhizine.model.entity.InvokeThirdLogWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InvokeThirdLogMapper {
    long countByExample(InvokeThirdLogExample example);

    int deleteByExample(InvokeThirdLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InvokeThirdLogWithBLOBs record);

    int insertSelective(InvokeThirdLogWithBLOBs record);

    List<InvokeThirdLogWithBLOBs> selectByExampleWithBLOBs(InvokeThirdLogExample example);

    List<InvokeThirdLog> selectByExample(InvokeThirdLogExample example);

    InvokeThirdLogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InvokeThirdLogWithBLOBs record, @Param("example") InvokeThirdLogExample example);

    int updateByExampleWithBLOBs(@Param("record") InvokeThirdLogWithBLOBs record, @Param("example") InvokeThirdLogExample example);

    int updateByExample(@Param("record") InvokeThirdLog record, @Param("example") InvokeThirdLogExample example);

    int updateByPrimaryKeySelective(InvokeThirdLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(InvokeThirdLogWithBLOBs record);

    int updateByPrimaryKey(InvokeThirdLog record);
}