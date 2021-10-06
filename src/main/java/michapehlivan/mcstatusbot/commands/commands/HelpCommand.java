package michapehlivan.mcstatusbot.commands.commands;

import java.util.Random;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import michapehlivan.mcstatusbot.commands.Command;
import reactor.core.publisher.Mono;

/**
 * Class containing the -help command
 * @author Micha Pehlivan
 */
public class HelpCommand implements Command{

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return event.getMessage()
            .getChannel()
            .block()
            .createMessage(getHelpEmbed())
            .then();
    }

    /**
     * Create embed containing info on all commands
     * @author Micha Pehlivan
     * @return embed containing a list of commands
     */
    public EmbedCreateSpec getHelpEmbed(){
        Random random = new Random();
        Color color = Color.of(random.nextFloat(), random.nextFloat(), random.nextFloat()); //generates random color for embed

        EmbedCreateSpec embed = EmbedCreateSpec.builder()
            .color(color)
            .title("help")
            .description("list of commands")
            .addField("-status", "display the status of the currently registered Minecraft server", false)
            .addField("-set `hostname, serverip`", "set the server to be used by -status", false)
            .addField("-help", "display this message", false)
            .build();

        return embed;
    }
    
}
