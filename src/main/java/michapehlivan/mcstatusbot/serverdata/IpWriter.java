package michapehlivan.mcstatusbot.serverdata;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class IpWriter {

    private static final String path = "mcstatusbot\\src\\main\\java\\michapehlivan\\mcstatusbot\\serverdata\\Servers.json";
    private static final Gson gson = new Gson();
    private static JsonObject file = new JsonObject();

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

    public String setServer(ServerObject server){
        String host = server.getHost();
        String ip = server.getIp();

        if(file.has("host")){
            file.remove("host");
        }
        if(file.has("ip")){
            file.remove("ip");
        }
        file.addProperty("host", host);
        file.addProperty("ip", ip);

        FileWriter writer;
        try {
            writer = new FileWriter(path);
            gson.toJson(file, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "error while adding server, please try again";
        }
        
        return "server sucesfully added";
    }
}
