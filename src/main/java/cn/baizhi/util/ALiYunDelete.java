package cn.baizhi.util;

import cn.baizhi.config.ALiYunConfig;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.File;

public class ALiYunDelete {
    public static void delete(String objectName){

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ALiYunConfig.ENDPOINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = ALiYunConfig.ACCESS_KEY_ID;
        String accessKeySecret = ALiYunConfig.ACCESS_KEY_SECRET;
        String bucketName = "yingx11";  //存储空间名


        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public static void delete2(File file){
         File[] files = file.listFiles();
         if (files.length == 0){ //目录下的子目录为0
               file.delete();//删除此目录
                    return;
         } else {
            for (int i = 0; i < files.length; i++) {
                 if (files[i].isDirectory()){ //判断是否为目录
                     delete2(files[i]);  //是目录调用递归
                     files[i].delete();//子目录删除成功后删除此目录
                 } else {
                      files[i].delete();//不是目录删除文件
                 }
            }
         }
         //file.delete();  //在删除完最高层目录下的子目录后删除最高层目录
    }
}