package cn.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

//用户表
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Excel(name = "id")
    private String id;
    @Excel(name = "姓名")
    private String username;
    @Excel(name = "头像",type = 2)
    private String headimg;//头像
    @Excel(name = "手机号")
    private String phone;  //手机号
    @Excel(name = "描述")
    private String brief;  //个性签名
    private String wechat; //微信
    @Excel(name = "注册时间",exportFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdate; //注册时间
    private int status; //状态
    private String sex;
}