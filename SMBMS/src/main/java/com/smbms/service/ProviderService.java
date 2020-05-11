package com.smbms.service;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.Provider;
import com.smbms.pojo.Role;
import com.smbms.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jz
 * @date 2020/4/30 - 20:42
 */
@Service
public interface ProviderService {

    //获取供应商列表
    PageInfo<Provider> getProList(String queryProCode, String queryProName) throws Exception;

    //添加供应商
    Integer insertProvider(Provider provider) throws Exception;

    //根据id查询供应商信息
    Provider findProviderById(Long id) throws Exception;

    //根据id修改供应商信息
    Integer updateProvider(Long id,Provider provider) throws Exception;

    //根据id删除供应商
    Integer deleteProvider(Long id) throws Exception;
}
