package michapehlivan.mcstatusbot.commands.commands;

import java.io.IOException;
import java.util.Random;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import michapehlivan.mcstatusbot.commands.Command;
import michapehlivan.mcstatusbot.mcstatus.Request;
import michapehlivan.mcstatusbot.util.DataCode;
import michapehlivan.mcstatusbot.util.PlayerList;
import reactor.core.publisher.Mono;

public class StatusCommand implements Command{

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return event.getMessage().getChannel()
            .flatMap(channel -> {
                try {
                    return channel.createMessage(getEmbed(event.getMessage().getGuildId().get()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).then();
    }

    //generate embed containing Minecraft server data
    public static EmbedCreateSpec getEmbed(Snowflake guild) throws IOException{
        Random random = new Random();
        Color color = Color.of(random.nextFloat(), random.nextFloat(), random.nextFloat());
        Long guildId = guild.asLong();
        
        if(Boolean.parseBoolean(Request.request(DataCode.STATE, guildId))){
            EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .color(color)
                .title("status of " + Request.request(DataCode.NAME, guildId))
                .footer("hosted by " + Request.request(DataCode.HOST, guildId), null)
                .addField("version: ", Request.request(DataCode.VERSION, guildId), true)
                .addField("ping: ", Request.request(DataCode.PING, guildId) + " ms", true)
                .addField("players online:", Request.request(DataCode.ONLINE, guildId) + '/' + Request.request(DataCode.MAX, guildId) + "\n\n"
                    + new PlayerList(Request.request(DataCode.PLAYERS, guildId)).getPlayers()
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
    }
    
}
