package cn.baizhi.dao;

import cn.baizhi.entity.Admin;

public interface AdminDao {
    Admin queryByName(String name);
}
