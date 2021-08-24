package cn.baizhi.controller;

import cn.baizhi.entity.Category;
import cn.baizhi.entity.Video;
import cn.baizhi.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService vs;

    //分页查
    @RequestMapping("/selectVideo")
    public Map<String, Object> selectVideo(int page){
        System.out.println(page);
        int size = 3;
        return vs.queryRange(page,size);
    }

    //添加
    @RequestMapping("/add")
    public void add(MultipartFile file,String title,String brief,String id) throws IOException {
        Video video = new Video();
        video.setId(UUID.randomUUID().toString());
        video.setTitle(title);
        video.setBrief(brief);
        video.setCreateDate(new Date());
        video.setCategory(new Category(id,null,null,null));
        vs.insert(video,file,null);
    }

    //删除
    @RequestMapping("/delete")
    public void delete(String id){
        vs.delete(id);
    }
}