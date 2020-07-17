package site.teamo.learning.config;

import ch.qos.logback.core.rolling.TriggeringPolicyBase;

import java.io.File;

public class TestTrigger<E> extends TriggeringPolicyBase<E> {

    public boolean isTriggeringEvent(File file, E whatever) {
        if(LogConfig.getRoll()==true){
            LogConfig.setRoll(false);
            return true;
        }
        return false;
//        return true;
    }
}

