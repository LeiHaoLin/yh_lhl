package cn.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//@Aspect
//@Component
public class CacheAspect {
    @Autowired
    private RedisTemplate redisTemplate;
    /*
        execution():方法级别
        within():   类级别
        @annotation:注解方式
     */
    @Around("execution(* cn.baizhi.service.*Impl.query*(..))")
    public Object addCache(ProceedingJoinPoint joinPoint){
        StringBuilder sb = new StringBuilder();

        /*
            redis :key value
            key:   类名全路径 + 方法名 +实参
            value: obj
         */

        //获取类的全路径
        String className = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        sb.append(className).append(methodName);
        //实参值
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg);
        }

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        HashOperations hashOperations = redisTemplate.opsForHash();

        Object obj = null;
        if (hashOperations.hasKey(className,sb.toString())){
            //如果有这个key
            obj = hashOperations.get(className,sb.toString());
        }else {
            //没有这个key
            try {
                obj = joinPoint.proceed();//放行请求
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            hashOperations.put(className,sb.toString(),obj);
        }
        return obj;
    }

    //只要执行了增删改 就应该清除缓存
    @After("@annotation(cn.baizhi.annotation.DeleteCache)")
    public void delCache(JoinPoint joinPoint){
        //类的全限命名
        String name = joinPoint.getTarget().getClass().getName();

        redisTemplate.delete(name);
    }
}