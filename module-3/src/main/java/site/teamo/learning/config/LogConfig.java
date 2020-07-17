package site.teamo.learning.config;

public class LogConfig {
    private static String execId;
    private static boolean roll;


    public static String getExecId() {
        return execId;
    }

    public static void setExecId(String execId) {
        LogConfig.execId = execId;
    }

    public static boolean getRoll() {
        return roll;
    }

    public static void setRoll(boolean roll) {
        LogConfig.roll = roll;
    }
}
