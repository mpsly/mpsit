package cn.mpsit.StudentDemo.mapper;

import cn.mpsit.StudentDemo.bean.UserBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    public List<UserBean> login(@Param("username") String username, @Param("password") String password);
}
