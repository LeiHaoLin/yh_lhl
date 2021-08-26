package cn.baizhi.controller;

import cn.baizhi.entity.Admin;
import cn.baizhi.service.AdminService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin //解决跨域问题
public class AdminController {
    @Autowired
    private AdminService as;
    private org.slf4j.Logger log = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("/login")
    public Map<String, Object> login(@RequestBody Admin admin,HttpServletRequest request){
        return as.queryByName(admin.getUsername(),admin.getPassword(),request);
    }
}
