package cn.baizhi.controller;

import cn.baizhi.commont.CommontResult;
import cn.baizhi.service.VideoService;
import cn.baizhi.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/yingx/app")
public class AppController {
    @Autowired
    private VideoService vs;

    @RequestMapping("/queryByReleaseTime")
    public void queryByReleaseTime(){
        List<VideoVo> videoVos = new ArrayList<>();
        try {
            videoVos = vs.queryFindAll();
            CommontResult.success("查询成功",videoVos);
        }catch (Exception e){
            CommontResult.fail("查询失败",videoVos);
        }
    }
}
