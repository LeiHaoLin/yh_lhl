package cn.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video implements Serializable {
    private String id;
    private String title;   //标题
    private String brief;   //描述
    private String coverPath;//封面连接
    private String videoPath;//视频连接
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate; //发布时间
    private Category category;//所属二级id
    private User user;       //用户id
    private String groupId;  //小组id
}
