package michapehlivan.mcstatusbot.commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import michapehlivan.mcstatusbot.commands.commands.HelpCommand;
import michapehlivan.mcstatusbot.commands.commands.ServerSetCommand;
import michapehlivan.mcstatusbot.commands.commands.StatusCommand;

/**
 * Class for managing all commands
 * @author Micha Pehlivan
 */
public class CommandManager {
    
    public static Map<String, Command> commands = new HashMap<String, Command>();

    /**
     * Adds commands to commands HashMap
     * @author Micha Pehlivan
     * @throws IOException
     */
    public static void init() throws IOException{
        commands.put("status", new StatusCommand());
        commands.put("set", new ServerSetCommand());
        commands.put("help", new HelpCommand());
    }

}
