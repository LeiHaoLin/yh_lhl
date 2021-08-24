package cn.baizhi.controller;

import cn.baizhi.entity.Admin;
import cn.baizhi.service.AdminService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin //解决跨域问题
public class AdminController {
    @Autowired
    private AdminService as;
    private org.slf4j.Logger log = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("/login")
    public Map<String, Object> login(@RequestBody Admin admin){
        Map<String,Object> map = new HashMap<>();
        Admin admin1 = as.queryByName(admin.getUsername());
        //log.debug(admin.toString());

        map.put("pk",false);
        if(admin1 == null){
            //用户不存在
            map.put("msg","用户名不存在");
        }else {
            if (admin1.getPassword().equals(admin.getPassword())){
                //密码正确
                map.put("pk",true);
                map.put("admin1",admin1);
            }else {
                //密码错误
                map.put("msg","密码错误");
            }
        }
        return map;
    }
}
