package michapehlivan.mcstatusbot.commands.commands;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import michapehlivan.mcstatusbot.commands.Command;
import michapehlivan.mcstatusbot.mcstatus.Request;
import michapehlivan.mcstatusbot.util.DataCode;
import michapehlivan.mcstatusbot.util.PlayerList;
import reactor.core.publisher.Mono;

/**
 * Class containing the -status command
 * @author Micha Pehlivan
 */
public class StatusCommand implements Command{

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return event.getMessage().getChannel()
            .flatMap(channel -> {
                try {
                    return channel.createMessage(getEmbed());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).then();
    }

    /**
     * Generates an embed containing thes status of the currently registered Minecraft server
     * @author Micha Pehlivan
     * @return Embed containing server status
     * @throws IOException
     */
    public static EmbedCreateSpec getEmbed() throws IOException{
        Random random = new Random();
        Color color = Color.of(random.nextFloat(), random.nextFloat(), random.nextFloat()); //generates random color for embed
        
        try {
            if(Boolean.parseBoolean(Request.request(DataCode.STATE))){ //check if server is online
                EmbedCreateSpec embed = EmbedCreateSpec.builder()
                    .color(color)
                    .title("status of server")
                    .footer("hosted by " + Request.request(DataCode.HOST).trim(), null)
                    .addField("description: ", Request.request(DataCode.DESCRIPTION), false)
                    .addField("version: ", Request.request(DataCode.VERSION), true)
                    .addField("ping: ", Request.request(DataCode.PING) + " ms", true)
                    .addField("players online:", Request.request(DataCode.ONLINE) + '/' + Request.request(DataCode.MAX) + "\n\n"
                        + new PlayerList(Request.request(DataCode.PLAYERS)).getPlayers()
                            .iterator().next().replace(",", "\n"), false)
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
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            return EmbedCreateSpec.builder().title("error while getting server status, please try again").build();
        }
    }
    
}
