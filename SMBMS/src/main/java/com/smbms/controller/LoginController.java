package com.smbms.controller;

import com.smbms.pojo.User;
import com.smbms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author jz
 * @date 2020/5/4 - 10:19
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录功能
     * @param userCode
     * @param userPassword
     * @param session
     * @return
     */
    @PostMapping("/login")
    public String doLogin(String userCode, String userPassword, HttpSession session, Model model) throws Exception{
        User user = userService.login(userCode,userPassword);
        if(user==null){
            model.addAttribute("error","用户名或密码错误！！！");
            return "forward:login.jsp";
        }
        session.setAttribute("userSession",user);
        return "frame";
    }

    //注销功能
    @RequestMapping("/logout.do")
    public String doLogout(HttpSession session){
        session.invalidate();
        return "redirect:/login.jsp";
    }

}
