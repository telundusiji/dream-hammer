package site.teamo.learning;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import site.teamo.learning.log.PrintLog;

@SpringBootApplication
public class Application implements ApplicationRunner {

    @Autowired
    private PrintLog printLog;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE) // .REACTIVE, .SERVLET
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        printLog.print();
    }
}
