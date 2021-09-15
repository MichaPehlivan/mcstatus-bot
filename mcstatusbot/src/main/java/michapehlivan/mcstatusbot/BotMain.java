package michapehlivan.mcstatusbot;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import michapehlivan.mcstatusbot.util.DataCode;

public class BotMain {
    
    static ServerSocket serversocket;
    static Socket client;
    static BufferedReader input;
    static DataOutputStream output;

    public static void main(String[] args) throws IOException {
        final DiscordClient discordclient = DiscordClient.create("token");
        final GatewayDiscordClient gateway = discordclient.login().block();

        serversocket = new ServerSocket(6000);
        Runtime.getRuntime().exec("python mcstatusbot\\src\\main\\java\\michapehlivan\\mcstatusbot\\McStatus.py");
        client = serversocket.accept();
        System.out.println("client connected");

        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new DataOutputStream(client.getOutputStream());

        gateway.on(ReadyEvent.class)
            .subscribe(event -> System.out.println("bot ready"));

        gateway.on(MessageCreateEvent.class)
            .map(MessageCreateEvent::getMessage)
            .filter(message -> message.getContent().equalsIgnoreCase("status"))
            .flatMap(message -> message.getChannel())
            .flatMap(channel -> channel.createMessage(getEmbed(channel)))
            .subscribe();
    
        gateway.onDisconnect().block();
    }

    public static String getData(int index){
        try {
            output.writeInt(index);
            String data = input.readLine();
            return data;
        } catch (IOException e) {
            return null;
        }
    }

    public static EmbedCreateSpec getEmbed(MessageChannel channel){
        Random random = new Random();
        Color color = Color.of(random.nextFloat(), random.nextFloat(), random.nextFloat());
        
        if(Boolean.parseBoolean(getData(DataCode.ONLINE))){
            EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .color(color)
                .title("status of " + getData(DataCode.NAME))
                .addField("version: ", getData(DataCode.VERSION), true)
                .addField("ping: ", getData(DataCode.PING) + " ms", true)
                .addField("players online:", getData(DataCode.ONLINE) + '/' + getData(DataCode.MAX) + "\n\n"
                    + getData(DataCode.PLAYERS).substring(2, 9) 
                    + "\n" + getData(DataCode.PLAYERS).substring(13, 20), false)
                .build();
            return embed;
        }
        else{
            EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .color(color)
                .title("status of server")
                .description("server is offline")
                .build();
            return embed;
        }
    }
}
