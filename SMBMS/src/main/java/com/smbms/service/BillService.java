package com.smbms.service;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.Bill;
import com.smbms.pojo.Provider;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jz
 * @date 2020/5/7 - 12:50
 */
@Service
public interface BillService {
    //获取订单列表
    PageInfo<Bill> getBillList(Long queryProviderId,String queryProductName,Integer queryIsPayment) throws Exception;

    //获取供应商名称
    List<Provider> getProName() throws Exception;

    //添加订单
    Integer insertBill(Bill bill) throws Exception;

    //根据id查询供应商信息
    Bill findBillById(Long id) throws Exception;

    //根据id修改订单信息
    Integer updateBillDetail(Long id,Bill bill) throws Exception;

    //根据id删除订单
    Integer deleteBill(Long id) throws Exception;
}
