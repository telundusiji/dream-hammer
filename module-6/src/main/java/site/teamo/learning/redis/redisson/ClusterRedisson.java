package site.teamo.learning.redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author 爱做梦的锤子
 * @create 2020/7/17
 */
public class ClusterRedisson {

    private Config config;

    /**
     * 连接cluster模式的redis
     *
     * @param redisNodes redis集群中节点信息 redis://nodeHost:nodePort
     * @param password   redis密码
     */
    public ClusterRedisson(String[] redisNodes, String password) {
        config = new Config();
        config.useClusterServers()
                .addNodeAddress(redisNodes)
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
