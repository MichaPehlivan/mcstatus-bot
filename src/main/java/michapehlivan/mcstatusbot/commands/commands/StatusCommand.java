package michapehlivan.mcstatusbot.commands.commands;

import java.util.Random;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import michapehlivan.mcstatusbot.BotMain;
import michapehlivan.mcstatusbot.commands.Command;
import michapehlivan.mcstatusbot.util.DataCode;
import michapehlivan.mcstatusbot.util.PlayerList;
import reactor.core.publisher.Mono;

public class StatusCommand implements Command{

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return event.getMessage().getChannel()
            .flatMap(channel -> channel.createMessage(getEmbed())).then();
    }

    //generate embed containing Minecraft server data
    public static EmbedCreateSpec getEmbed(){
        Random random = new Random();
        Color color = Color.of(random.nextFloat(), random.nextFloat(), random.nextFloat());
        
        if(Boolean.parseBoolean(BotMain.getData(DataCode.STATE))){
            EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .color(color)
                .title("status of " + BotMain.getData(DataCode.NAME))
                .addField("version: ", BotMain.getData(DataCode.VERSION), true)
                .addField("ping: ", BotMain.getData(DataCode.PING) + " ms", true)
                .addField("players online:", BotMain.getData(DataCode.ONLINE) + '/' + BotMain.getData(DataCode.MAX) + "\n\n"
                    + new PlayerList(BotMain.getData(DataCode.PLAYERS)).getPlayers()
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
