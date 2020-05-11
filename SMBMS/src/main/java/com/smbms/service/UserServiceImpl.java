package com.smbms.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smbms.dao.RoleMapper;
import com.smbms.dao.UserMapper;
import com.smbms.pojo.Role;
import com.smbms.pojo.RoleExample;
import com.smbms.pojo.User;
import com.smbms.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jz
 * @date 2020/4/30 - 20:42
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired//自动注入
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    //根据id查询用户信息
    @Override
    @Transactional(readOnly = true)//事务管理 只读
    public User findUserById(Long id) throws Exception{
        return userMapper.selectByPrimaryKey(id);
    }

    //登录业务
    @Override
    public User login(String userCode, String userPassword) throws Exception{
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserCodeEqualTo(userCode);
        criteria.andUserPasswordEqualTo(userPassword);
        List<User> userList = userMapper.selectByExample(example);

        if(userList.size()==0){
            return null;
        }
        return userList.get(0);
    }


    //查询用户列表（分页查询）
    @Override
    public PageInfo<User> getUserList(int pageNum, int pageSize,String queryname,Long queryUserRole) throws Exception {
        PageHelper.startPage(pageNum,pageSize);

        List<User> userList = null;
        UserExample example = new UserExample();
        UserExample.Criteria criteria= example.createCriteria();


        if(queryname==null&&queryUserRole==null){
            userList = userMapper.selectByExample(example);
        }else if(queryUserRole==0&&queryname.equals("")){
            userList = userMapper.selectByExample(example);
        }

        if(queryname!=null&&!queryname.equals("")){
            criteria.andUserNameLike("%"+queryname+"%");
            userList = userMapper.selectByExample(example);
        }

        if(queryUserRole!=null&&queryUserRole!=0){
            criteria.andUserRoleEqualTo(queryUserRole);
            userList = userMapper.selectByExample(example);
        }

        //处理年龄问题
        for (User user : userList) {
            user.setAge(new Date().getYear() - user.getBirthday().getYear());
        }

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    //获取角色列表
    @Override
    public List<Role> getRoleList() throws Exception {
        RoleExample example = new RoleExample();
        return roleMapper.selectByExample(example);
    }

    //修改密码
    @Override
    public Integer updateUserPasswordById(String newpassword,Long id) throws Exception {
        User user = new User();
        user.setId(id);
        user.setUserPassword(newpassword);
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        return userMapper.updateByExampleSelective(user,example);
    }

    //添加用户
    @Override
    public Integer insertUser(User user) throws Exception {
        int i = userMapper.insertSelective(user);
        return i;
    }

    //修改用户
    @Override
    public Integer updateUser(User user,Long id) throws Exception {
        //存入id
        user.setId(id);
        //调用sql
        int i = userMapper.updateByPrimaryKeySelective(user);
        return i;
    }

    //根据id删除用户
    @Override
    public Integer delUser(Long id) throws Exception {
        int i = userMapper.deleteByPrimaryKey(id);
        return i;
    }
}
