package site.teamo.learning.redis.lettuce;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;

import java.util.List;

/**
 * @author 爱做梦的锤子
 * @create 2020/7/20
 */
public class ClusterLettuce {

    private List<RedisURI> redisURIList;

    /**
     * 使用Lettuce连接集群模式redis
     * @param redisURIList 集群中redis节点的信息，建议使用RedisURI.builder来进行构造
     */
    public ClusterLettuce(List<RedisURI> redisURIList) {
        this.redisURIList = redisURIList;
    }

    /**
     * 连接redis获取一个连接
     * @return 一个redis的连接
     */
    public StatefulRedisClusterConnection<String, String> connect() {
        RedisClusterClient redisClusterClient = RedisClusterClient.create(redisURIList);
        StatefulRedisClusterConnection<String, String> connect = redisClusterClient.connect();
        return connect;
    }


}
