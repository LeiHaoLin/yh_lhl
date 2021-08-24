package cn.baizhi.util;

import cn.baizhi.config.ALiYunConfig;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;

import java.io.File;

public class ALiYunDownload {
    public static void download(String objectName, String localFile) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ALiYunConfig.ENDPOINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = ALiYunConfig.ACCESS_KEY_ID;
        String accessKeySecret = ALiYunConfig.ACCESS_KEY_SECRET;
        String bucketName = "yingx11";  //存储空间名
        //String objectName = ".jpg";  //文件名
        //String localFile = "D:\\IdeaWork/yx_lhl/src/main/webapp/download" + objectName;  //下载本地地址  地址加保存名字

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}

