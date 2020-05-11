package com.smbms.dao;

import com.smbms.pojo.Provider;
import com.smbms.pojo.ProviderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderMapper {
    long countByExample(ProviderExample example);

    int deleteByExample(ProviderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Provider record);

    int insertSelective(Provider record);

    List<Provider> selectByExample(ProviderExample example);

    Provider selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Provider record, @Param("example") ProviderExample example);

    int updateByExample(@Param("record") Provider record, @Param("example") ProviderExample example);

    int updateByPrimaryKeySelective(Provider record);

    int updateByPrimaryKey(Provider record);
}