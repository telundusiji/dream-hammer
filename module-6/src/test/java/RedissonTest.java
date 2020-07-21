import org.junit.Assert;
import org.junit.Test;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.teamo.learning.redis.redisson.ClusterRedisson;
import site.teamo.learning.redis.redisson.SentinelRedisson;
import site.teamo.learning.redis.redisson.SingleInstanceRedisson;

/**
 * @author 爱做梦的锤子
 * @create 2020/7/20
 */
public class RedissonTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonTest.class);
    private static final String TEST_KEY = "redisson";
    private static final long TEST_VALUE = 100L;

    @Test
    public void singleInstance() {
        SingleInstanceRedisson singleInstanceRedisson = new SingleInstanceRedisson("192.168.56.90", 6379, "123456");
        RedissonClient redissonClient = singleInstanceRedisson.connect();
        RAtomicLong atomicLong = redissonClient.getAtomicLong(TEST_KEY);
        atomicLong.set(TEST_VALUE);
        LOGGER.info("redisson单机单实例:{}", redissonClient.getAtomicLong(TEST_KEY).get());
        Assert.assertEquals(TEST_VALUE, redissonClient.getAtomicLong(TEST_KEY).get());
    }

    @Test
    public void sentinel() {
        String[] sentinels = new String[]{"redis://192.168.56.91:26379", "redis://192.168.56.92:26379", "redis://192.168.56.93:26379"};
        SentinelRedisson sentinelRedisson = new SentinelRedisson("redis-master", sentinels, "123456");
        RedissonClient redissonClient = sentinelRedisson.connect();
        RAtomicLong atomicLong = redissonClient.getAtomicLong(TEST_KEY);
        atomicLong.set(TEST_VALUE);
        LOGGER.info("redisson哨兵模式:{}", redissonClient.getAtomicLong(TEST_KEY).get());
        Assert.assertEquals(TEST_VALUE, redissonClient.getAtomicLong(TEST_KEY).get());
    }

    @Test
    public void cluster() {
        String[] redisNodes = new String[]{
                "redis://192.168.56.81:6379",
                "redis://192.168.56.82:6379",
                "redis://192.168.56.83:6379",
                "redis://192.168.56.84:6379",
                "redis://192.168.56.85:6379",
                "redis://192.168.56.86:6379"
        };
        ClusterRedisson clusterRedisson = new ClusterRedisson(redisNodes, "123456");
        RedissonClient redissonClient = clusterRedisson.connect();
        RAtomicLong atomicLong = redissonClient.getAtomicLong(TEST_KEY);
        atomicLong.set(TEST_VALUE);
        LOGGER.info("redisson集群模式:{}", redissonClient.getAtomicLong(TEST_KEY).get());
        Assert.assertEquals(TEST_VALUE, redissonClient.getAtomicLong(TEST_KEY).get());
    }
}
