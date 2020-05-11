package com.smbms.service;

import com.github.pagehelper.PageInfo;
import com.smbms.dao.BillMapper;
import com.smbms.dao.ProviderMapper;
import com.smbms.pojo.Bill;
import com.smbms.pojo.BillExample;
import com.smbms.pojo.Provider;
import com.smbms.pojo.ProviderExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author jz
 * @date 2020/5/7 - 12:58
 */
@Service
public class BillServiceImpl implements BillService{

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private ProviderMapper providerMapper;
    //获取订单列表
    @Override
    public PageInfo<Bill> getBillList(Long queryProviderId,String queryProductName,Integer queryIsPayment) throws Exception {
        BillExample example = new BillExample();
        BillExample.Criteria criteria = example.createCriteria();

        if(queryProviderId!=null&&queryProviderId>0){
            criteria.andProviderIdEqualTo(queryProviderId);
        }

        if(queryProductName!=null){
            criteria.andProductNameLike("%"+queryProductName+"%");
        }

        if(queryIsPayment!=null&&queryIsPayment>0){
            criteria.andIsPaymentEqualTo(queryIsPayment);
        }

        List<Bill> billList = billMapper.selectByExample(example);

        PageInfo<Bill> pageInfo = new PageInfo<>(billList);
        return pageInfo;
    }

    //获取供应商名称
    @Override
    public List<Provider> getProName() throws Exception {
        ProviderExample example = new ProviderExample();
        ProviderExample.Criteria criteria = example.createCriteria();

        List<Provider> providerList = providerMapper.selectByExample(example);
        return providerList;
    }

    //添加订单
    @Override
    public Integer insertBill(Bill bill) throws Exception {
        bill.setCreationDate(new Date());
        return billMapper.insertSelective(bill);
    }

    //根据id查询订单信息
    @Override
    public Bill findBillById(Long id) throws Exception {
        return billMapper.selectByPrimaryKey(id);
    }

    //根据id修改订单信息
    @Override
    public Integer updateBillDetail(Long id, Bill bill) throws Exception {
        //存入id
        bill.setId(id);
        //调用
        int i = billMapper.updateByPrimaryKeySelective(bill);
        return i;
    }

    //根据id删除订单
    @Override
    public Integer deleteBill(Long id) throws Exception {
        return billMapper.deleteByPrimaryKey(id);
    }


}
