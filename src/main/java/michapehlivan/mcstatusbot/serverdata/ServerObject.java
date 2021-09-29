package michapehlivan.mcstatusbot.serverdata;

public class ServerObject {

    private long guildId;
    private String host, ip;

    public ServerObject(long guildId, String host, String ip){
        this.guildId = guildId;
        this.host = host;
        this.ip = ip;
    }

    //getters
    public long getGuildId(){
        return guildId;
    }

    public String getHost(){
        return host;
    }

    public String getIp(){
        return ip;
    }
}
