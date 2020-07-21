package site.teamo.learning.redis.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.Set;

/**
 * @author 爱做梦的锤子
 * @create 2020/7/17
 */
public class ClusterJedis {

    private Set<HostAndPort> redisNodes;
    private String password;
    private GenericObjectPoolConfig config;

    /**
     * 连接redis cluster
     *
     * @param redisNodes 集群中redis节点信息
     * @param password   redis 密码
     */
    public ClusterJedis(Set<HostAndPort> redisNodes, String password) {
        this.redisNodes = redisNodes;
        this.password = password;
        config = new GenericObjectPoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(5);
        config.setMinIdle(5);
    }

    /**
     * 连接redis cluster
     *
     * @return 一个redis cluster客户端
     */
    public JedisCluster connect() {
        JedisCluster jedisCluster = new JedisCluster(redisNodes, 10000, 10000, 3, password, config);
        return jedisCluster;
    }

}
