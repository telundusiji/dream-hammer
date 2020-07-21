package site.teamo.learning.redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author 爱做梦的锤子
 * @create 2020/7/17
 */
public class SentinelRedisson {

    private Config config;

    /**
     * 连接哨兵模式redis
     *
     * @param masterName redis的master名称
     * @param sentinels  哨兵的连接信息 redis://sentinelHost:sentinelPort
     * @param password   redis密码
     */
    public SentinelRedisson(String masterName, String[] sentinels, String password) {
        config = new Config();
        config.useSentinelServers()
                .setMasterName(masterName)
                .addSentinelAddress(sentinels)
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
