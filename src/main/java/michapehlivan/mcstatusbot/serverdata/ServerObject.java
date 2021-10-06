package michapehlivan.mcstatusbot.serverdata;

/**
 * Class used as a template for objects representing Minecraft
 */
public class ServerObject {

    private String host, ip;

    /**
     * Constructor of ServerObject class
     * @param host the server host
     * @param ip the ip of the server
     * @authro Micha Pehlivan
     */
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
