package site.teamo.learning.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import site.teamo.learning.config.LogConfig;

import java.util.Random;

@Component
public class PrintLog {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrintLog.class);

    public void print() throws InterruptedException {
        long cur = System.currentTimeMillis();
        String[] test = new String[]{"a","b","c"};
        Random r = new Random();
        String p = "main";
        while (true){

            if((System.currentTimeMillis()-cur) % 5l == 0){
                String t = test[Math.abs(r.nextInt())%3];
                p = t;
                LogConfig.setExecId(t);
                LogConfig.setRoll(true);
            }

            LOGGER.info("当前时间:{}",p);
            Thread.sleep(100);
        }
    }

}
