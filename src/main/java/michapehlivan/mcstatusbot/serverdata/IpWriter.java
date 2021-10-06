package michapehlivan.mcstatusbot.serverdata;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import michapehlivan.mcstatusbot.mcstatus.Request;

/**
 * Class for writing host-ip pairs to Servers.json
 * @author Micha Pehlivan
 */
public class IpWriter {

    private static final String path = "mcstatusbot\\src\\main\\java\\michapehlivan\\mcstatusbot\\serverdata\\Servers.json";
    private static final Gson gson = new Gson();
    private static JsonObject file = new JsonObject();

    /**
     * Constructor of IpWriter
     * @author Micha Pehlivan
     * @throws IOException
     */
    public IpWriter() throws IOException{
        JsonParser parser = new JsonParser();
        FileReader reader = new FileReader(path);
        JsonElement jsonElement = parser.parse(reader);
        reader.close();
        file = jsonElement.getAsJsonObject();

        FileWriter writer = new FileWriter(path);
        gson.toJson(file, writer);
        writer.close();
    }

    /**
     * Writes the server to Servers.json
     * @param server the ServerObject to be added to Servers.json
     * @author Micha Pehlivan
     * @return response from server
     */
    public String setServer(ServerObject server){
        String host = server.getHost();
        String ip = server.getIp();

        if(file.has("serverhost")){
            file.remove("serverhost");
        }
        if(file.has("serverip")){
            file.remove("serverip");
        }
        file.addProperty("serverhost", host);
        file.addProperty("serverip", ip);

        FileWriter writer;
        try {
            writer = new FileWriter(path);
            gson.toJson(file, writer);
            writer.close();
            return Request.post(server);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            return "error while adding server, please try again";
        }
    }
}
