package michapehlivan.mcstatusbot.serverdata;

public class ServerObject {

    private String host, ip;

    public ServerObject(String host, String ip){
        this.host = host;
        this.ip = ip;
    }

    //getters
    public String getHost(){
        return host;
    }

    public String getIp(){
        return ip;
    }
}
