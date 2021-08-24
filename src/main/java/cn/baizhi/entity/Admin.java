package cn.baizhi.entity;

import lombok.*;

import java.io.Serializable;

//管理员表
@Setter //生成set方法
@Getter //生成get方法
@ToString //上传toString方法
@AllArgsConstructor //生成有参构造
@NoArgsConstructor //生成无参构造
public class Admin implements Serializable {
    private String id;
    private String username;
    private String password;
    private Integer status; //状态
}
