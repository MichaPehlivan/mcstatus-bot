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
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;

import michapehlivan.mcstatusbot.commands.CommandManager;
import michapehlivan.mcstatusbot.util.Console;
import michapehlivan.mcstatusbot.util.DataCode;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BotMain {
    
    static ServerSocket serversocket;
    static Socket client;
    static BufferedReader input;
    static DataOutputStream output;

    /*
        Main class containing the Discord4J code, as well as the server code
    */
    public static void main(String[] args) throws IOException {
        Console console = new Console("Bot Console", 800, 500);
        System.setOut(console.getPrintStream());

        final GatewayDiscordClient gateway = DiscordClient.create("token").login().block();
        gateway.updatePresence(ClientPresence.online(ClientActivity.playing("-status"))).block();

        serversocket = new ServerSocket(6000);

        Runtime.getRuntime().exec("python mcstatusbot\\src\\main\\java\\michapehlivan\\mcstatusbot\\McStatus.py");
        
        client = serversocket.accept();
        System.out.println("client connected");

        CommandManager.init();

        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new DataOutputStream(client.getOutputStream());

        gateway.on(ReadyEvent.class)
            .subscribe(event -> System.out.println("bot ready"));

        gateway.on(MessageCreateEvent.class)
            .flatMap(event -> Mono.just(event.getMessage().getContent())
                .flatMap(content -> Flux.fromIterable(CommandManager.commands.entrySet())
                    .filter(entry -> content.startsWith('-' + entry.getKey()))
                    .flatMap(entry -> entry.getValue().execute(event))
                    .next()))
            .subscribe();
        
        gateway.onDisconnect().block();
        getData(DataCode.CLOSE);
        serversocket.close();
    }

    //function to request and retrieve data through a socket connection
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
