package cn.baizhi.service;

import cn.baizhi.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface UserService {
    //分页查
    Map<String,Object> queryByPage(int page,int size);
    //修改状态
    void update(String id,int status);
    //添加
    void insert(MultipartFile headimg, User user) throws IOException;
    //删除
    void delete(String id, String headimg);
    //导出用户信息
    void export() throws IOException;
    //查看每月注册人数
    Map<String,Object> queryRegistCount();
    //根据id查一个
    User queryOne(String id);
    //修改用户信息
    void updateUser(User user);
}
