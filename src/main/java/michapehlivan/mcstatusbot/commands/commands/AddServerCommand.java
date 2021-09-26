package michapehlivan.mcstatusbot.commands.commands;

import java.io.IOException;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import michapehlivan.mcstatusbot.commands.Command;
import michapehlivan.mcstatusbot.ipdata.IpWriter;
import reactor.core.publisher.Mono;

public class AddServerCommand implements Command{

    IpWriter writer;

    public AddServerCommand() throws IOException{
        writer = new IpWriter();
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        String message = event.getMessage().getContent();
        Snowflake guild = event.getGuildId().get();
        return event.getMessage().getChannel()
            .flatMap(channel -> channel.createMessage(addServerFromCommand(message, guild))).then();
    }

    public String addServerFromCommand(String message, Snowflake guild){
        try {
            String commandData = message.replace("-add", "").trim();
            String[] data = commandData.split(",");
            String host = data[0].trim();
            String ip = data[1].trim();
            return writer.addServer(guild, host, ip);
        } catch (Exception e) {
            System.out.println(e);
            return "error occured, please type out the command properly";
        }
    }
    
}
