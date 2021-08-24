package cn.baizhi.dao;

import cn.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDao {
    //分页查
    List<Video> queryRange(@Param("start") int start, @Param("end") int end);
    //查总页
    int selectCount();
    //添加
    void insert(Video video);
    //根据id查一个
    Video selectOne(String id);
    //删除
    void delete(String id);
}
