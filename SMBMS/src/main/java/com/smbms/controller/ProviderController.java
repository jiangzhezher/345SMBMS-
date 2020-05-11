package com.smbms.controller;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.Provider;
import com.smbms.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jz
 * @date 2020/5/7 - 12:01
 */
@Controller
@RequestMapping("/pro")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    //获取供应商列表(条件查询)
    @RequestMapping("/list")
    public String queryProList(String queryProCode, String queryProName, Model model) throws Exception{
        PageInfo<Provider> pageInfo = providerService.getProList(queryProCode,queryProName);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("queryProCode",queryProCode);
        model.addAttribute("queryProName",queryProName);
        return "providerlist";
    }

    //跳转添加页面
    @RequestMapping("/addjsp")
    public String addjsp() throws Exception{
        return "provideradd";
    }

    //添加供应商
    @RequestMapping("/add")
    public String addProvider(Provider provider) throws Exception{
        provider.setCreationDate(new Date());
        int i = providerService.insertProvider(provider);
        return "forward:/pro/list";
    }

    //根据id查询供应商信息
    @RequestMapping("/query")
    public String queryProvider(Long pid,Model model) throws Exception{
        Provider provider = providerService.findProviderById(pid);
        model.addAttribute("provider",provider);
        return "providerview";
    }

    //跳转修改页面
    @RequestMapping("/updatejsp")
    public String updatejsp(Long pid,Model model) throws Exception{
        Provider provider = providerService.findProviderById(pid);
        model.addAttribute("provider",provider);
        return "providermodify";
    }

    //修改供应商信息
    @RequestMapping("/update")
    public String updateProvider(Long pid, Provider provider) throws Exception{
        int i = providerService.updateProvider(pid,provider);
        return "forward:/pro/list";
    }

    //根据id删除供应商
    @RequestMapping("/del")
    @ResponseBody
    public Map<String,String> delProvider(Long proid, HttpServletRequest request) throws Exception{
        Map<String,String> map = new HashMap<>();
        int i = providerService.deleteProvider(proid);
        String delResult = "";
        String pathlocation = "";
        if(i>0){
            delResult = "true";
            pathlocation = request.getContextPath()+"/pro/list";
        }
        map.put("delResult",delResult);
        map.put("pathlocation",pathlocation);
        return map;
    }
}
