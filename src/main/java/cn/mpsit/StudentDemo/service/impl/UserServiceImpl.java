package cn.mpsit.StudentDemo.service.impl;

import cn.mpsit.StudentDemo.bean.UserBean;
import cn.mpsit.StudentDemo.mapper.UserMapper;
import cn.mpsit.StudentDemo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserBean logIn(String username, String password) {
        List<UserBean> userList=null;
         userList  =userMapper.login(username,password);
        if (userList.size()!=0){
            System.out.print(userList.size());
            return userList.get(0);
        }else{
            return null;
        }

    }
}
