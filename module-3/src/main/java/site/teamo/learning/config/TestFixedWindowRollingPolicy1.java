package site.teamo.learning.config;

import ch.qos.logback.core.rolling.RollingPolicyBase;
import ch.qos.logback.core.rolling.RolloverFailure;
import ch.qos.logback.core.rolling.helper.*;

import java.io.File;



public class TestFixedWindowRollingPolicy1 extends RollingPolicyBase {

    RenameUtil util = new RenameUtil();

    @Override
    public void rollover() throws RolloverFailure {

        // Delete the oldest file, to keep Windows happy.

        String fileName = fileNamePatternStr.replace("$execId", LogConfig.getExecId());

        File file = new File(fileName);

//        if (file.exists()) {
//            file.delete();
//        }


        File toRename = new File(fileName);
        // no point in trying to rename an inexistent file

        addInfo("Skipping roll-over for inexistent file " + fileName);

        util.rename(getActiveFileName(), fileName);
    }


    @Override
    public String getActiveFileName() {
        return getParentsRawFileProperty();
    }
}
