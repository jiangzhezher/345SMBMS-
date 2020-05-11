package com.smbms.controller;

import com.github.pagehelper.PageInfo;
import com.smbms.exception.UserException;
import com.smbms.pojo.Role;
import com.smbms.pojo.User;
import com.smbms.pojo.UserExample;
import com.smbms.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jz
 * @date 2020/4/30 - 20:40
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @RequestMapping(value = "/detail/{id}")
//    public String getUser(@PathVariable("id") Long id, Model model) throws Exception{
//        User user = userService.findUserById(id);
//        if(user==null){
//            throw new UserException("用户不存在");
//        }
//        model.addAttribute("user",user);
//        return "userDetail";
//    }

    //跳转修改密码页面
    @RequestMapping("/pwdmodify")
    public String pwd(){
        return "pwdmodify";
    }

//    @RequestMapping("/list")
//    public String findUserList(String queryname, Long queryUserRole, Model model) throws Exception{
//        List<User> userList = null;
//        if(queryname==null&&queryUserRole==0){
//            userList = userService.queryUserList2();
//            model.addAttribute("userList",userList);
//        }else{
//            userList = userService.queryUserList(queryname,queryUserRole);
//            model.addAttribute("userList",userList);
//        }
//        return "userlist";
//    }

    //用户列表查询（分页）
    @RequestMapping("/list")
    public String getUsers(Model model,Integer pageIndex,String queryname,Long queryUserRole) throws Exception{

        if(pageIndex==null){//如果为空 则默认第一页
            pageIndex = 1;
        }
        //分页查询
        PageInfo<User> pageInfo = userService.getUserList(pageIndex,5,queryname,queryUserRole);
        //获取角色列表
        List<Role> roleList = userService.getRoleList();
        //获取用户列表
        List<User> userList = pageInfo.getList();

        //处理角色名称
        for (User user : userList) {
            for (Role role : roleList) {
                if(user.getUserRole()==role.getId()){
                    user.setUserRoleName(role.getRoleName());
                }
            }
        }

        //存入数据
        model.addAttribute("roleList",roleList);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("queryUserRole",queryUserRole);
        model.addAttribute("queryUserName",queryname);
        return "userlist";
    }

    //验证userCode
    @RequestMapping(value = "/usercode",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> userCode(String userCode,HttpSession session) throws Exception{

        Map<String, String> map = new HashMap<String, String>();
        User loginUser = (User)session.getAttribute("userSession");

        if(userCode.equals(loginUser.getUserCode())){
            userCode = "exist";
        }
        map.put("userCode",userCode);
        return map;
    }

    //验证密码
    @RequestMapping(value = "/confirmpwd",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> confirmpwd(String oldpassword,HttpSession session) throws Exception{

        Map<String, String> map = new HashMap<String, String>();
        User loginUser = (User)session.getAttribute("userSession");
        String result = "";
        if(loginUser==null){
            result = "sessionerror";
        }else if(oldpassword.equals(loginUser.getUserPassword())){
            result = "true";
        }else{
            result = "false";
        }
        map.put("result",result);
        return map;
    }

    //修改密码
    @RequestMapping("/updatepwd")
    public String updatepwd(HttpSession session,String newpassword)throws Exception{
        //获取session域中存储的user对象
        User loginUser = (User)session.getAttribute("userSession");
        Long id = loginUser.getId();
        //根据id修改用户密码
        userService.updateUserPasswordById(newpassword,id);
        //清空session
        session.invalidate();
        //重定向
        return "redirect:/login.jsp";
    }

    //跳转添加页面
    @RequestMapping("/addjsp")
    public String addjsp(Model model) throws Exception{
        List<Role> roleList = userService.getRoleList();
        model.addAttribute("roleList",roleList);
        return "useradd";
    }

    //添加用户
    @RequestMapping("/add")
    public String addUser(User user) throws Exception{
        int i = userService.insertUser(user);
        return "forward:/user/list";
    }

    //跳转修改页面
    @RequestMapping("/updatejsp")
    public String updatejsp(Model model,Long uid,HttpServletRequest request) throws Exception{
        User user = userService.findUserById(uid);

        List<Role> roleList = userService.getRoleList();
        model.addAttribute("roleList",roleList);
        model.addAttribute("user",user);
        return "usermodify";
    }

    //修改用户信息
    @RequestMapping("/update")
    public String updateUser(User user,Long uid) throws Exception{
        int i = userService.updateUser(user,uid);
        return "forward:/user/list";
    }

    //根据id查询用户信息
    @RequestMapping("/query")
    public String queryUser(Long uid,Model model) throws Exception{
        User user = userService.findUserById(uid);
        List<Role> roleList = userService.getRoleList();

        for (Role role : roleList) {
            if(user.getUserRole()==role.getId()){
                user.setUserRoleName(role.getRoleName());
            }
        }

        model.addAttribute("user",user);
        return "userview";
    }

    //根据id删除用户
    @RequestMapping("/del")
    @ResponseBody
    public Map<String,String> delUser(Long uid,HttpServletRequest request) throws Exception{
        Map<String,String> map = new HashMap<>();
        int i = userService.delUser(uid);
        String delResult = "";
        String pathlocation = "";
        if(i>0){
            delResult = "true";
            pathlocation = request.getContextPath()+"/user/list";
        }
        map.put("delResult",delResult);
        map.put("pathlocation",pathlocation);
        return map;
    }
}
