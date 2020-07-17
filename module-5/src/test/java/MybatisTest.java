
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.teamo.learning.service.DreamAccountService;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = {"site.teamo.learning.dao"})
public class MybatisTest {

    @Autowired
    private DreamAccountService dreamAccountService;

    @Test
    public void test1() throws Exception {
        dreamAccountService.transferAccounts(10L,"郝大","锤子");
    }
}
