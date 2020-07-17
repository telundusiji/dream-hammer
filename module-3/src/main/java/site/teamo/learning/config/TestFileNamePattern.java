package site.teamo.learning.config;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.rolling.helper.FileNamePattern;

public class TestFileNamePattern  extends FileNamePattern {
    public TestFileNamePattern(String patternArg, Context contextArg) {
        super(patternArg, contextArg);
    }



    @Override
    public String convertInt(int i) {
        return getPattern().replace("%i",LogConfig.getExecId());
    }
}
