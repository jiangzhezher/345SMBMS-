package com.smbms.controller;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.Bill;
import com.smbms.pojo.Provider;
import com.smbms.service.BillService;
import com.smbms.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jz
 * @date 2020/5/7 - 12:00
 */
@Controller
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillService billService;

    //获取订单列表
    @RequestMapping("/list")
    public String queryBillList(Long queryProviderId, String queryProductName,Integer queryIsPayment ,Model model) throws Exception{
        PageInfo<Bill> pageInfo = billService.getBillList(queryProviderId,queryProductName,queryIsPayment);

        List<Provider> providerList = billService.getProName();

        List<Bill> billList = pageInfo.getList();
        for (Bill bill : billList) {
            for (Provider provider : providerList) {
                if(bill.getProviderId()==provider.getId()){
                    bill.setProviderName(provider.getProName());
                }
            }
        }

        model.addAttribute("queryProviderId",queryProviderId);
        model.addAttribute("queryIsPayment",queryIsPayment);
        model.addAttribute("providerList",providerList);
        model.addAttribute("pageInfo",pageInfo);
        return "billlist";
    }


    //跳转添加页面
    @RequestMapping("/addjsp")
    public String addjsp(Model model) throws Exception{
        List<Provider> providerList = billService.getProName();
        model.addAttribute("providerList",providerList);
        return "billadd";
    }

    //添加订单
    @RequestMapping("/add")
    public String addBill(Bill bill) throws Exception{
        //调用业务逻辑
        int i = billService.insertBill(bill);
        return "forward:/bill/list";
    }

    //根据id查询订单信息
    @RequestMapping("/query")
    public String queryBill(Long bid,Model model) throws Exception{
        Bill bill = billService.findBillById(bid);
        List<Provider> providerList = billService.getProName();

        for (Provider provider : providerList) {
            if(bill.getProviderId().equals(provider.getId())){
                bill.setProviderName(provider.getProName());
            }
        }

        model.addAttribute("bill",bill);
        return "billview";
    }
    //跳转修改页面
    @RequestMapping("/updatejsp")
    public String updatejsp(Model model,Long bid) throws Exception{
        Bill bill = billService.findBillById(bid);
        List<Provider> providerList = billService.getProName();
        model.addAttribute("bill",bill);
        model.addAttribute("providerList",providerList);
        return "billmodify";
    }

    //修改订单信息
    @RequestMapping("/update")
    public String updateBill(Long id,Bill bill) throws Exception{
        int i = billService.updateBillDetail(id,bill);
        return "forward:/bill/list";
    }

    //根据id删除订单
    @RequestMapping("/del")
    @ResponseBody
    public Map<String,String> delBill(HttpServletRequest request,Long billid) throws Exception{
        Map<String,String> map = new HashMap<>();
        int i = billService.deleteBill(billid);
        String delResult = "";
        String pathlocation = "";
        if(i>0){
            delResult = "true";
            pathlocation = request.getContextPath()+"/bill/list";
        }
        map.put("delResult",delResult);
        map.put("pathlocation",pathlocation);
        return map;
    }
}
