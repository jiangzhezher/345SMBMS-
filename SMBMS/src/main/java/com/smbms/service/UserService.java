package com.smbms.service;

import com.github.pagehelper.PageInfo;
import com.smbms.pojo.Role;
import com.smbms.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jz
 * @date 2020/4/30 - 20:42
 */
@Service
public interface UserService {
    //根据用户id查询用户信息
    User findUserById(Long id) throws Exception;

    //根据用户code和密码查询用户（登录业务）
    User login(String userCode, String userPassword) throws Exception;

    //获取用户列表(分页查询)
    PageInfo<User> getUserList(int pageNum, int pageSize,String queryname,Long queryUserRole) throws Exception;

    //获取角色列表
    List<Role> getRoleList() throws Exception;

    //根据用户名修改用户密码
    Integer updateUserPasswordById(String newPassword,Long id)throws Exception;

    //添加用户
    Integer insertUser(User user) throws Exception;

    //修改用户信息
    Integer updateUser(User user,Long id) throws Exception;

    //根据id删除用户
    Integer delUser(Long id) throws Exception;
}
