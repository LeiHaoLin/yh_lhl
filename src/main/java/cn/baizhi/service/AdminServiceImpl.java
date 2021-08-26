package cn.baizhi.service;

import cn.baizhi.dao.AdminDao;
import cn.baizhi.entity.Admin;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao ad;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryByName(String name, String password, HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        Admin admin1 = ad.queryByName(name);
        String sessionId = request.getSession(true).getId();

        map.put("pk",false);
        if(admin1 == null){
            //用户不存在
            map.put("msg","用户名不存在");
        }else {
            if (admin1.getPassword().equals(password)){
                //密码正确
                map.put("pk",true);
                map.put("admin1",admin1);
                //响应 sessionId
                ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
                valueOperations.set(sessionId, JSONObject.toJSONString(admin1),10000,TimeUnit.MINUTES);
                map.put("token",sessionId);
            }else {
                //密码错误
                map.put("msg","密码错误");
            }
        }
        return map;
    }
}