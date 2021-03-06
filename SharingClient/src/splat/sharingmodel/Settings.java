package splat.sharingmodel;

import java.util.Random;

public class Settings {
    private static String ipRemote = "54.227.75.250";
    private static String ipLoc = "192.168.1.85";
    private static boolean isLocalServer = true;
    public static Integer port = 1337;
    public static int id = 0;
    public static boolean debugmode = true;

    public static String getIp() {
        return isLocalServer ? ipLoc : ipRemote;
    }

    public static int getPort() {
        return port;
    }

    public static int getId() {
        return id;
    }

    public static int newId() {
        Random rand = new Random();
        id = rand.nextInt(1000000);
        return id;
    }

    public static boolean debug() {
        return debugmode;
    }
}
