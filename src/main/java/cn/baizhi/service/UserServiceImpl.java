package cn.baizhi.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.UserDao;
import cn.baizhi.entity.User;
import cn.baizhi.util.ALiYunAdd;
import cn.baizhi.util.ALiYunDelete;
import cn.baizhi.util.ALiYunDownload;
import cn.baizhi.vo.MonthAndCount;
import com.alibaba.fastjson.JSONObject;
import io.goeasy.GoEasy;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao ud;

    //分页查
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryByPage(int page, int size) {
        Map<String,Object> map = new HashMap<>();
        List<User> users = ud.queryRange((page - 1) * size, size);
        int count = ud.selectCount();
        map.put("data",users);

        //查总页
        if(count%size==0){
            map.put("pages",count/size);
        }else{
            map.put("pages",count/size+1);
        }

        return map;
    }

    //修改状态
    @DeleteCache
    @Override
    public void update(String id,int status) {
        ud.update(id,status);
    }

    //添加
    @DeleteCache
    @Override
    public void insert(MultipartFile headimg,User user) throws IOException {
        ud.insert(user);
        ALiYunAdd.uploadByBytes(headimg,"user/"+headimg.getOriginalFilename());

        //实时更新用户注册删除
        Map<String, Object> map = queryRegistCount();
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io","BC-48c84eb5810d4f20b0ab85b8b9a10df0");
        goEasy.publish("aa", JSONObject.toJSONString(map));
    }

    //删除
    @DeleteCache
    @Override
    public void delete(String id, String headimg) {
        ud.delete(id);
        int index = headimg.lastIndexOf("/")+1; //获取最后一个/
        String objectName = "user/"+headimg.substring(index);  //文件名  截取
        ALiYunDelete.delete(objectName);

        //实时更新用户注册删除
        Map<String, Object> map = queryRegistCount();
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io","BC-48c84eb5810d4f20b0ab85b8b9a10df0");
        goEasy.publish("aa", JSONObject.toJSONString(map));
    }

    @Override
    public void export() throws IOException {
        String objectName = null;
        //查所有
        List<User> all = ud.queryFindAll();
        for (User user : all) {
            String headimg = user.getHeadimg();

            //http://yingx11.oss-cn-beijing.aliyuncs.com/user/1fb5b4c917f960873a6dc71632d424d74ec2af737d19989be5c84253c84e52ac.jpg
            int index = headimg.lastIndexOf("/")+1;// 获取最后一个/
            objectName = headimg.substring(index);    // 截取文件名
            //先下载阿里云图片
            String localFile = "D:\\IdeaWork/yx_lhl/src/main/webapp/download/" + objectName; //下载本地地址  地址加保存名字
            ALiYunDownload.download("user/"+objectName,localFile);

            user.setHeadimg(localFile);
        }

        //导出用户信息
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息", "用户信息表"), User.class, all);
        workbook.write(new FileOutputStream("d:\\user.xls"));
        System.out.println("---------1--------");

        //删除图片
        ALiYunDelete.delete2(new File("D:\\IdeaWork/yx_lhl/src/main/webapp/download/"));
        System.out.println("---------2--------");
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryRegistCount() {
        Map<String,Object> map = new HashMap<>();
        List<MonthAndCount> man = ud.man();
        List<Integer> manI = new ArrayList<>();
        List<MonthAndCount> woman = ud.woman();
        List<Integer> womanI = new ArrayList<>();

        for (int i=1; i<=12; i++){
            manI.add(0);
            womanI.add(0);
        }
        //查所有男生
        for (MonthAndCount monthAndCount : man) {
            int i1 = monthAndCount.getMonth();
            manI.set(i1-1,monthAndCount.getCount());

        }
        //查所有女生
        for (MonthAndCount monthAndCount : woman) {
            int i1 = monthAndCount.getMonth();
            womanI.set(i1-1,monthAndCount.getCount());

        }
        List<String> data = Arrays.asList("一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月");
        map.put("data",data);
        map.put("manCount",manI);
        map.put("womanCount",womanI);
        return map;

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User queryOne(String id) {
        return ud.queryOne(id);
    }

    @DeleteCache
    @Override
    public void updateUser(User user) {
        ud.updateUser(user);
    }
}