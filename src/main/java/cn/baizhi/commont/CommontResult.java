package cn.baizhi.commont;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommontResult {
    private String status;
    private String message;
    private Object data;

    //定义一个方法表示成功的状态
    public static Map<String, Object> success(String message, Object data){
        Map<String,Object> map = new HashMap<>();
        map.put("status","100");
        map.put("message",message);
        map.put("data",data);
        return map;
    }

    //定义一个方法表示失败的状态
    public static Map<String, Object> fail(String message, Object data){
        Map<String,Object> map = new HashMap<>();
        map.put("status","104");
        map.put("message",message);
        map.put("data",data);
        return map;
    }
}
