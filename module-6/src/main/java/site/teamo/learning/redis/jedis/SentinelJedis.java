package site.teamo.learning.redis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Set;

/**
 * @author 爱做梦的锤子
 * @create 2020/7/17
 */
public class SentinelJedis {

    private JedisSentinelPool jedisSentinelPool;

    /**
     * 连接哨兵模式的redis
     *
     * @param masterName redis的master名称
     * @param sentinels  哨兵的主机和端口信息
     * @param password   redis的认证密码
     */
    public SentinelJedis(String masterName, Set<String> sentinels, String password) {
        //根据redis的信息创建一个redis哨兵的连接池
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(5);
        config.setMinIdle(5);
        jedisSentinelPool = new JedisSentinelPool(masterName, sentinels, config, password);
    }

    /**
     * 从连接池中取出一个客户端
     *
     * @return 获取一个Jedis客户端
     */
    public Jedis connect() {
        return jedisSentinelPool.getResource();
    }

    /**
     * 销毁连接池
     */
    public void close() {
        jedisSentinelPool.close();
        jedisSentinelPool.destroy();
    }
}
