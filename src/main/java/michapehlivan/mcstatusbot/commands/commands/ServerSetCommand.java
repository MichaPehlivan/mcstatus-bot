package michapehlivan.mcstatusbot.commands.commands;

import java.io.IOException;

import discord4j.core.event.domain.message.MessageCreateEvent;
import michapehlivan.mcstatusbot.commands.Command;
import michapehlivan.mcstatusbot.serverdata.IpWriter;
import michapehlivan.mcstatusbot.serverdata.ServerObject;
import reactor.core.publisher.Mono;

public class ServerSetCommand implements Command{

    private static IpWriter writer;

    public ServerSetCommand(){
        try {
            writer = new IpWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        String command = event.getMessage().getContent().replace("-set", "");
        String[] data = command.split(", ");
        return event.getMessage()
            .getChannel()
            .block()
            .createMessage(writer.setServer(new ServerObject(event.getGuildId().get().asLong(), data[0], data[1])))
            .then();
    }
    
}
