package cn.baizhi.config;

import cn.baizhi.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    //用来加入拦截器相关配置 参数1:InterceptorRegistry 拦截器注册对象
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加那个拦截器
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")//拦截所有控制器请求
                .excludePathPatterns("/admin/login");//排除指定的请求;
    }
}
