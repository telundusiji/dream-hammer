import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import site.teamo.learning.redis.jedis.ClusterJedis;
import site.teamo.learning.redis.jedis.SentinelJedis;
import site.teamo.learning.redis.jedis.SingleInstanceJedis;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 爱做梦的锤子
 * @create 2020/7/17
 */

public class JedisTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisTest.class);
    private static final String TEST_KEY = "jedis";
    private static final String TEST_VALUE = "dream-hammer";

    @Test
    public void singleInstance() {
        SingleInstanceJedis singleInstanceJedis = new SingleInstanceJedis("192.168.56.90", 6379, "123456");
        Jedis jedis = singleInstanceJedis.connect();
        jedis.set(TEST_KEY, TEST_VALUE);
        LOGGER.info("jedis单机单实例:{}", jedis.get(TEST_KEY));
        Assert.assertEquals(TEST_VALUE, jedis.get(TEST_KEY));
    }

    @Test
    public void sentinel() {
        Set<String> sentinels = new HashSet<>();
        sentinels.add("192.168.56.91:26379");
        sentinels.add("192.168.56.92:26379");
        sentinels.add("192.168.56.93:26379");
        SentinelJedis sentinelJedis = new SentinelJedis("redis-master", sentinels, "123456");
        Jedis jedis = sentinelJedis.connect();
        jedis.set(TEST_KEY, TEST_VALUE);
        LOGGER.info("jedis哨兵模式:{}", jedis.get(TEST_KEY));
        Assert.assertEquals(TEST_VALUE, jedis.get(TEST_KEY));
    }

    @Test
    public void cluster() {
        Set<HostAndPort> redisNodes = new HashSet<>();
        redisNodes.add(new HostAndPort("192.168.56.81",6379));
        redisNodes.add(new HostAndPort("192.168.56.82",6379));
        redisNodes.add(new HostAndPort("192.168.56.83",6379));
        redisNodes.add(new HostAndPort("192.168.56.84",6379));
        redisNodes.add(new HostAndPort("192.168.56.85",6379));
        redisNodes.add(new HostAndPort("192.168.56.86",6379));
        ClusterJedis clusterJedis = new ClusterJedis(redisNodes, "123456");
        JedisCluster jedisCluster = clusterJedis.connect();
        jedisCluster.set(TEST_KEY, TEST_VALUE);
        LOGGER.info("jedis集群模式:{}", jedisCluster.get(TEST_KEY));
        Assert.assertEquals(TEST_VALUE, jedisCluster.get(TEST_KEY));
    }
}
