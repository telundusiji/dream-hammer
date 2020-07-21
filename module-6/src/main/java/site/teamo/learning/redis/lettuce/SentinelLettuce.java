package site.teamo.learning.redis.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.codec.Utf8StringCodec;
import io.lettuce.core.masterslave.MasterSlave;
import io.lettuce.core.masterslave.StatefulRedisMasterSlaveConnection;

import java.util.List;

/**
 * @author 爱做梦的锤子
 * @create 2020/7/20
 */
public class SentinelLettuce {
    private List<RedisURI> redisURIList;

    /**
     * 连接哨兵模式的redis
     * @param redisURIList 哨兵模式redis下的哨兵的信息，建议使用RedisURI.builder.sentinel填写哨兵信息来进行构造
     */
    public SentinelLettuce(List<RedisURI> redisURIList) {
        this.redisURIList = redisURIList;
    }

    /**
     * 连接redis获取一个连接
     * @return 一个redis的连接
     */
    public StatefulRedisMasterSlaveConnection<String, String> connect() {
        RedisClient redisClient = RedisClient.create();
        StatefulRedisMasterSlaveConnection<String, String> connect = MasterSlave.connect(redisClient, new Utf8StringCodec(), redisURIList);
        return connect;
    }
}
