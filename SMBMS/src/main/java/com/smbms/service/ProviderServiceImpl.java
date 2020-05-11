package com.smbms.service;

import com.github.pagehelper.PageInfo;
import com.smbms.dao.ProviderMapper;
import com.smbms.pojo.Provider;
import com.smbms.pojo.ProviderExample;
import com.smbms.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jz
 * @date 2020/5/7 - 12:08
 */
@Service
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderMapper providerMapper;

    //查询供应商列表
    @Override
    public PageInfo<Provider> getProList(String queryProCode, String queryProName) throws Exception {
        ProviderExample example = new ProviderExample();
        ProviderExample.Criteria criteria = example.createCriteria();

        if(queryProCode!=null){
            criteria.andProCodeLike("%"+queryProCode+"%");
        }

        if(queryProName!=null){
            criteria.andProNameLike("%"+queryProName+"%");
        }

        //调用sql语句 查询供应商列表
        List<Provider> providerList = providerMapper.selectByExample(example);

        //存入
        PageInfo<Provider> pageInfo = new PageInfo<>(providerList);
        return pageInfo;
    }

    //添加供应商
    @Override
    public Integer insertProvider(Provider provider) throws Exception {
        return providerMapper.insertSelective(provider);
    }

    //根据id查询供应商信息
    @Override
    public Provider findProviderById(Long id) throws Exception {
        Provider provider = providerMapper.selectByPrimaryKey(id);
        return provider;
    }

    //根据id修改供应商信息
    @Override
    public Integer updateProvider(Long id, Provider provider) throws Exception {
        //存入id
        provider.setId(id);
        //根据id更新数据
        int i = providerMapper.updateByPrimaryKeySelective(provider);
        return i;
    }

    //根据id删除供应商
    @Override
    public Integer deleteProvider(Long id) throws Exception {
        return providerMapper.deleteByPrimaryKey(id);
    }
}
