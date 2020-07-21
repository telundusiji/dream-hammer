package site.teamo.learning.redis.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 爱做梦的锤子
 * @create 2020/7/20
 */
@Service("redisSpring")
public class RedisSpring {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Object redisTemplateOperate(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        return redisTemplate.opsForValue().get(key);
    }

    public String stringRedisTemplateOperate(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
        return stringRedisTemplate.opsForValue().get(key);
    }

}
