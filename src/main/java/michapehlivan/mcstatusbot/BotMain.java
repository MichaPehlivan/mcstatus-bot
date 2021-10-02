package michapehlivan.mcstatusbot;

import java.io.IOException;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;

import michapehlivan.mcstatusbot.commands.CommandManager;
import michapehlivan.mcstatusbot.util.Console;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
    Main class containing the Discord4J code, as well as the server code
*/
public class BotMain {

    public static void main(String[] args) throws IOException{
        Console console = new Console("Bot Console", 800, 500);
        System.setOut(console.getPrintStream());

        final GatewayDiscordClient gateway = DiscordClient.create("token").login().block();
        gateway.updatePresence(ClientPresence.online(ClientActivity.playing("-status"))).block();

        CommandManager.init();

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
    }
}
