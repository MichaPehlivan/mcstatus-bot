package michapehlivan.mcstatusbot.ipdata;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import discord4j.common.util.Snowflake;

public class IpWriter {
    
    private final String path = "mcstatusbot\\src\\main\\java\\michapehlivan\\mcstatusbot\\ipdata\\IpList.json";
    private final Gson gson = new Gson();
    private JsonObject file;

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

    //adds server to Json file. returns confirmation message for discord
    public String addServer(Snowflake guild, String host, String ip) throws IOException{
        if(!file.has(guild.asString())){
            file.add(guild.asString(), new JsonArray());
        }
        JsonArray guildArray = file.get(guild.asString()).getAsJsonArray();

        ServerObject server = new ServerObject(host, ip);
        JsonObject serverInArray = new JsonObject();

        serverInArray.addProperty("host", server.host);
        serverInArray.addProperty("ip", server.ip);
        guildArray.add(serverInArray);

        FileWriter writer = new FileWriter(path);
        gson.toJson(file, writer);
        writer.close();

        return "server added";
    }
}
