package cn.baizhi;

import cn.baizhi.dao.AdminDao;
import cn.baizhi.dao.UserDao;
import cn.baizhi.service.CategoryService;
import cn.baizhi.service.UserService;
import cn.baizhi.service.VideoService;
import cn.baizhi.vo.MonthAndCount;
import io.goeasy.GoEasy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class YxLhlApplicationTests {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private UserService us;
    @Autowired
    private CategoryService cd;
    @Autowired
    private VideoService vd;
    @Autowired
    private UserDao ud;

    @Test
    void contextLoads() {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io","BC-48c84eb5810d4f20b0ab85b8b9a10df0");
        goEasy.publish("aa","大家好");
    }

    @Test
    void test1(){
        System.out.println(us.queryByPage(1, 3));
    }

    @Test
    void test2(){
        cd.delete("058ba266-38e9-4b9d-af6c-88ded595b78a");
    }

    @Test
    void test3(){
        System.out.println(vd.queryRange(1, 3));
    }

    @Test
    void test4() throws IOException {
        us.export();
    }

    @Test
    void test5(){
        Map<String,Object> map = new HashMap<>();
        List<MonthAndCount> man = ud.man();
        List<Integer> manI = new ArrayList<>();
        List<MonthAndCount> woman = ud.woman();
        List<Integer> womanI = new ArrayList<>();

        for (int i=1; i<=12; i++){
            manI.add(0);
            womanI.add(0);
        }
        for (MonthAndCount monthAndCount : man) {
            int i1 = monthAndCount.getMonth();
            manI.set(i1-1,monthAndCount.getCount());

        }
        for (MonthAndCount monthAndCount : woman) {
            int i1 = monthAndCount.getMonth();
            womanI.set(i1-1,monthAndCount.getCount());

        }
        /*for (int i=1; i<=12; i++){
            for (MonthAndCount monthAndCount : woman) {
                if (monthAndCount.getMonth() == i){
                    if(monthAndCount.getCount() != null){
                        womanI.set(i-1,monthAndCount.getCount());
                    }else {
                        womanI.set(i-1,0);
                    }
                }
            }
        }*/
        map.put("manI",manI);
        map.put("womanI",womanI);
        System.out.println(manI);
        System.out.println(womanI);
    }
}
