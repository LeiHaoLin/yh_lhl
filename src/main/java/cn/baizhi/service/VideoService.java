package cn.baizhi.service;

import cn.baizhi.entity.Video;
import cn.baizhi.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface VideoService {
    //分页查
    Map<String,Object> queryRange(int page, int size);
    //添加
    void insert(Video video, MultipartFile file,String videoHeadimg) throws IOException;
    //删除
    void delete(String id);
    //根据视频的上传时间降序排序
    List<VideoVo> queryFindAll();
}
