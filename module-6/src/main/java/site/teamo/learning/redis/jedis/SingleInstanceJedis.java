package site.teamo.learning.redis.jedis;

import redis.clients.jedis.Jedis;

/**
 * @author 爱做梦的锤子
 * @create 2020/7/17
 */
public class SingleInstanceJedis {


    private String host;
    private Integer port;
    private String password;

    /**
     * 连接单机单实例的redis
     *
     * @param host     redis主机地址
     * @param port     redis服务端口
     * @param password redis认证密码
     */
    public SingleInstanceJedis(String host, Integer port, String password) {
        this.host = host;
        this.port = port;
        this.password = password;
    }

    /**
     * 连接redis
     *
     * @return 一个Jedis客户端
     */
    public Jedis connect() {
        Jedis jedis = new Jedis(host, port);
        jedis.auth(password);
        return jedis;
    }

}
