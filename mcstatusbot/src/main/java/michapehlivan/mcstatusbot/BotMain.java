package michapehlivan.mcstatusbot;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;

public class BotMain {
    
    static ServerSocket serversocket;
    static Socket client;
    static BufferedReader input;
    static DataOutputStream output;

    public static void main(String[] args) throws IOException {
        final DiscordClient discordclient = DiscordClient.create("token");
        final GatewayDiscordClient gateway = discordclient.login().block();

        serversocket = new ServerSocket(6000);
        client = serversocket.accept();
        System.out.println("client connected");

        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new DataOutputStream(client.getOutputStream());

        gateway.on(ReadyEvent.class)
            .subscribe(event -> System.out.println("bot ready"));

        gateway.on(MessageCreateEvent.class)
            .map(MessageCreateEvent::getMessage)
            .filter(message -> message.getContent().equalsIgnoreCase(".online"))
            .flatMap(message -> message.getChannel())
            .flatMap(channel -> channel.createMessage(EmbedCreateSpec.builder()
                .color(Color.RED)
                .title("status of " + getData(2))
                .addField("players online:", getData(0) + '/' + getData(1), true)   
                .addField("version: ", getData(3), true)
                .addField("ping: ", getData(4) + " ms", false)
                .build()))
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
}
