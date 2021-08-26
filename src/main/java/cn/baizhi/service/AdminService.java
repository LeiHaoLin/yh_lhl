package cn.baizhi.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AdminService {
    Map<String, Object> queryByName(String name, String password, HttpServletRequest request);
}
