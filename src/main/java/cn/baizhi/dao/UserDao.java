package cn.baizhi.dao;

import cn.baizhi.entity.User;
import cn.baizhi.vo.MonthAndCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    //分页查
    List<User> queryRange(@Param("start") int start, @Param("end") int end);
    //查总页
    int selectCount();
    //修改状态
    void update(@Param("id")String id,@Param("status")int status);
    //添加
    void insert(User user);
    //删除
    void delete(String id);
    //查所有
    List<User> queryFindAll();
    //查所有男生
    List<MonthAndCount> man();
    //查所有女生
    List<MonthAndCount> woman();
    //根据id查一个
    User queryOne(String id);
    //修改用户信息
    void updateUser(User user);
}
