package cn.baizhi.service;

import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.config.ALiYunConfig;
import cn.baizhi.dao.VideoDao;
import cn.baizhi.dao.VideoVoDao;
import cn.baizhi.entity.Video;
import cn.baizhi.util.ALiYunAdd;
import cn.baizhi.util.ALiYunDelete;
import cn.baizhi.vo.VideoVo;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoDao vd;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryRange(int page, int size) {
        Map<String,Object> map = new HashMap<>();
        List<Video> videos = vd.queryRange((page-1) * size , size);
        map.put("videos",videos);

        //总页数
        int count = vd.selectCount();
        if(count % size == 0){
            map.put("pages",count/size);
        }else {
            map.put("pages",count/size+1);
        }

        return map;
    }

    @DeleteCache
    @Override
    public void insert(Video video, MultipartFile file,String videoHeadimg) throws IOException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ALiYunConfig.ENDPOINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ALiYunConfig.ACCESS_KEY_ID;
        String accessKeySecret = ALiYunConfig.ACCESS_KEY_SECRET;
        // 填写视频文件所在的Bucket名称。
        String bucketName = "yingx11";
        // 填写视频文件的完整路径。若视频文件不在Bucket根目录，需携带文件访问路径，例如examplefolder/videotest.mp4。
        long time = new Date().getTime();
        String objectName = "video/"+time+file.getOriginalFilename();

        ALiYunAdd.uploadByBytes(file,objectName);
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 使用精确时间模式截取视频50s处的内容，输出为JPG格式的图片，宽度为800，高度为600。
        String style = "video/snapshot,t_1000,f_jpg,w_800,h_600";
        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);


        // 填写网络流地址。
        InputStream inputStream = null;
        try {
            inputStream = new URL(signedUrl.toString()).openStream();
        }catch (Exception e){
            e.printStackTrace();
        }

        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        String[] split = objectName.split("\\.");
        ossClient.putObject("yingx11", split[0]+".jpg", inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
        String videoPath = "http://yingx11.oss-cn-beijing.aliyuncs.com/"+objectName;
        String coverPath = "http://yingx11.oss-cn-beijing.aliyuncs.com/"+split[0]+".jpg";
        video.setVideoPath(videoPath);
        video.setCoverPath(coverPath);
        vd.insert(video);
    }

    @DeleteCache
    @Override
    public void delete(String id) {
        Video video = vd.selectOne(id);
        String coverPath = video.getCoverPath();//封面
        int i = coverPath.lastIndexOf("/")+1;//获取指定最后的字符
        String s = coverPath.substring(i); //截取
        ALiYunDelete.delete("video/"+s);

        String videoPath = video.getVideoPath();//视频
        int i1 = videoPath.lastIndexOf("/")+1;
        String s1 = videoPath.substring(i1);
        ALiYunDelete.delete("video/"+s1);
        vd.delete(id);
    }

    @Autowired
    private VideoVoDao vvd;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<VideoVo> queryFindAll() {
        return vvd.findAll();
    }
}