package site.teamo.learning.redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author 爱做梦的锤子
 * @create 2020/7/17
 */
public class SingleInstanceRedisson {

    private Config config;

    /**
     * 连接单机单实例的redis
     *
     * @param host     redis主机地址
     * @param port     redis 端口
     * @param password redis密码
     */
    public SingleInstanceRedisson(String host, Integer port, String password) {
        config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setPassword(password);

    }

    /**
     * 连接redis
     *
     * @return 一个RedissonClient客户端
     */
    public RedissonClient connect() {
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
