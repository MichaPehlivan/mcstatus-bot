package michapehlivan.mcstatusbot.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

/**
 * Interface to be used for all commands
 * @author Micha Pehlivan
 */
public interface Command {
    
    Mono<Void> execute(MessageCreateEvent event);
}
