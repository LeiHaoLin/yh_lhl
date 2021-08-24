package cn.baizhi.service;

import cn.baizhi.entity.Category;

import java.util.List;

public interface CategoryService {
    //查1级标题
    List<Category> queryByLevels(int levels);
    //查2级标题
    List<Category> queryByParendId(String id);
    //添加
    void save(Category category);
    //删除
    void delete(String id);
}
