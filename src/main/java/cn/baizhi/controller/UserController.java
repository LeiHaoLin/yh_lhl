package cn.baizhi.controller;

import cn.baizhi.entity.User;
import cn.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin //解决跨域
public class UserController {
    @Autowired
    private UserService us;

    //分页查
    @RequestMapping("/queryByPage")
    public Map<String, Object> queryByPage(int page){
        int size = 3;
        return us.queryByPage(page,size);
    }

    //修改状态
    @RequestMapping("/update")
    public void update(String id,int status){
        us.update(id,status);
    }

    //添加
    @RequestMapping("/add")
    public void add(MultipartFile photo,String username,String phone,String brief,String sex) throws IOException {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setPhone(phone);
        user.setBrief(brief);
        user.setCreatedate(new Date());
        user.setWechat(null);
        user.setStatus(0);
        user.setSex(sex);
        //http://yingx11.oss-cn-beijing.aliyuncs.com/80d1c210dc1bb66448696cbb607c7d3.jpg
        String setHeadimg = "http://yingx11.oss-cn-beijing.aliyuncs.com/user/"+photo.getOriginalFilename();
        user.setHeadimg(setHeadimg);
        us.insert(photo,user);
    }

    //删除
    @RequestMapping("/delete")
    public void delete(String id,String photo){
        us.delete(id,photo);
    }

    //导出
    @RequestMapping("/export")
    public void export() throws IOException {
        us.export();
    }

    //查所有月份的注册人数
    @RequestMapping("/registCount")
    public Map<String, Object> registCount(){
        return us.queryRegistCount();
    }

    //根据id查一个
    @RequestMapping("/selectOne")
    public User selectOne(String id){
        return us.queryOne(id);
    }

    //修改用户
    @RequestMapping("/updateUser")
    public void updateUser(User user){
        us.updateUser(user);
    }
}