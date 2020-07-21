import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.masterslave.StatefulRedisMasterSlaveConnection;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.teamo.learning.redis.lettuce.ClusterLettuce;
import site.teamo.learning.redis.lettuce.SentinelLettuce;
import site.teamo.learning.redis.lettuce.SingleInstanceLettuce;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 爱做梦的锤子
 * @create 2020/7/20
 */
public class LettuceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LettuceTest.class);
    private static final String TEST_KEY = "lettuce";
    private static final String TEST_VALUE = "dream-hammer";

    @Test
    public void SingleInstance(){
        SingleInstanceLettuce singleInstanceLettuce = new SingleInstanceLettuce("192.168.56.90", 6379, "123456");
        StatefulRedisConnection<String, String> connection = singleInstanceLettuce.connect();
        RedisCommands<String, String> commands = connection.sync();
        commands.set(TEST_KEY, TEST_VALUE);
        LOGGER.info("lettuce单机单实例:{}",commands.get(TEST_KEY));
        Assert.assertEquals(TEST_VALUE,commands.get(TEST_KEY));
    }

    @Test
    public void sentinel(){
        List<RedisURI> redisURIList = new ArrayList<>();
        redisURIList.add(RedisURI.builder().withSentinelMasterId("redis-master").withSentinel("192.168.56.91",26379).withPassword("123456").build());
        redisURIList.add(RedisURI.builder().withSentinelMasterId("redis-master").withSentinel("192.168.56.92",26379).withPassword("123456").build());
        redisURIList.add(RedisURI.builder().withSentinelMasterId("redis-master").withSentinel("192.168.56.93",26379).withPassword("123456").build());
        SentinelLettuce sentinelLettuce = new SentinelLettuce(redisURIList);
        StatefulRedisMasterSlaveConnection<String, String> connection = sentinelLettuce.connect();
        RedisCommands<String, String> commands = connection.sync();
        commands.set(TEST_KEY, TEST_VALUE);
        LOGGER.info("lettuce哨兵模式:{}",commands.get(TEST_KEY));
        Assert.assertEquals(TEST_VALUE,commands.get(TEST_KEY));
    }

    @Test
    public void cluster(){
        List<RedisURI> redisURIList = new ArrayList<>();
        redisURIList.add(RedisURI.builder().withHost("192.168.56.81").withPort(6379).withPassword("123456").build());
        redisURIList.add(RedisURI.builder().withHost("192.168.56.82").withPort(6379).withPassword("123456").build());
        redisURIList.add(RedisURI.builder().withHost("192.168.56.83").withPort(6379).withPassword("123456").build());
        redisURIList.add(RedisURI.builder().withHost("192.168.56.84").withPort(6379).withPassword("123456").build());
        redisURIList.add(RedisURI.builder().withHost("192.168.56.85").withPort(6379).withPassword("123456").build());
        redisURIList.add(RedisURI.builder().withHost("192.168.56.86").withPort(6379).withPassword("123456").build());
        ClusterLettuce clusterLettuce = new ClusterLettuce(redisURIList);
        StatefulRedisClusterConnection<String, String> connection = clusterLettuce.connect();
        RedisAdvancedClusterCommands<String, String> commands = connection.sync();
        commands.set(TEST_KEY, TEST_VALUE);
        LOGGER.info("lettuce集群模式:{}",commands.get(TEST_KEY));
        Assert.assertEquals(TEST_VALUE,commands.get(TEST_KEY));
    }

}
