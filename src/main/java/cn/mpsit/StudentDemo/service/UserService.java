package cn.mpsit.StudentDemo.service;

import cn.mpsit.StudentDemo.bean.UserBean;

public interface UserService {
    public UserBean logIn(String username,String password);
}
