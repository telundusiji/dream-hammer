package site.teamo.learning.redis.lettuce;


import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

/**
 * @author 爱做梦的锤子
 * @create 2020/7/20
 */
public class SingleInstanceLettuce {

    private RedisURI redisURI;

    /**
     * 使用Lettuce连接单机单实例的redis
     * @param host redis的主机地址
     * @param port redis的端口号
     * @param password redis的密码
     */
    public SingleInstanceLettuce(String host, Integer port, String password) {
        redisURI = RedisURI.builder()
                .withHost(host)
                .withPort(port)
                .withPassword(password)
                .build();
    }

    /**
     * 连接redis获取一个连接
     * @return 一个redis连接
     */
    public StatefulRedisConnection<String, String> connect() {
        RedisClient redisClient = RedisClient.create(redisURI);
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        return connect;
    }
}
