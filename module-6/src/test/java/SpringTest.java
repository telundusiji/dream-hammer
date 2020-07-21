import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.teamo.learning.redis.spring.Application;
import site.teamo.learning.redis.spring.RedisSpring;

/**
 * @author 爱做梦的锤子
 * @create 2020/7/20
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class SpringTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringTest.class);

    @Autowired
    private RedisSpring redisSpring;

    private static final String TEST_KEY = "spring";
    private static final String TEST_VALUE = "dream-hammer";

    @Test
    public void test() {
        Object value = redisSpring.redisTemplateOperate(TEST_KEY, TEST_VALUE);
        LOGGER.info("redisTemplate:{}",value);
        Assert.assertEquals(value,TEST_VALUE);
        String s = redisSpring.stringRedisTemplateOperate(TEST_KEY, TEST_VALUE);
        LOGGER.info("stringRedisTemplate:{}",s);
        Assert.assertEquals(TEST_VALUE,s);
    }

}
