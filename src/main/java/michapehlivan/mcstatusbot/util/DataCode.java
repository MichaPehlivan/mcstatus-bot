package michapehlivan.mcstatusbot.util;

/**
    Enum for storing data request indices
    @author Micha Pehlivan
*/
public enum DataCode {
    ;
    public static final int STATE = 0; //online state of server
    public static final int HOST = 1; //name of server host
    public static final int ONLINE = 2; //number of online players
    public static final int MAX = 3; //max online players on this server
    public static final int DESCRIPTION = 4; //description of this server
    public static final int VERSION = 5; //Minecraft version of this server
    public static final int PING = 6; //latency of this server in ms
    public static final int PLAYERS = 7; //list of online players, empty if more than 10 are online
}
